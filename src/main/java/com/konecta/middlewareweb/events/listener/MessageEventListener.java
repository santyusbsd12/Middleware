package com.konecta.middlewareweb.events.listener;

import static com.konecta.middlewareweb.negocio.util.Utils.toMapFormData;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

import com.konecta.middlewareweb.controller.rest.dto.WebhookDTO;
import com.konecta.middlewareweb.dialogflow.dto.ResponseDTO;
import com.konecta.middlewareweb.events.Publisher;
import com.konecta.middlewareweb.events.dto.MessageReceivedEvent;
import com.konecta.middlewareweb.events.dto.PersistBotResponseEvent;
import com.konecta.middlewareweb.events.dto.SendClosingMessageEvent;
import com.konecta.middlewareweb.events.dto.SendMessageEvent;
import com.konecta.middlewareweb.events.dto.SendMessageReceivedToBotEvent;
import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.model.ClosingMessage;
import com.konecta.middlewareweb.model.CustomMessage;
import com.konecta.middlewareweb.model.Message;
import com.konecta.middlewareweb.model.SendConfig;
import com.konecta.middlewareweb.model.enums.MessageOrigin;
import com.konecta.middlewareweb.negocio.exception.MiddlewareWebException;
import com.konecta.middlewareweb.negocio.service.BotService;
import com.konecta.middlewareweb.negocio.service.MessageService;
import com.konecta.middlewareweb.negocio.service.SendService;
import com.konecta.middlewareweb.negocio.service.TalkToBotService;

@Component
public class MessageEventListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageEventListener.class);
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SendService sendService;
	
	@Autowired
	private TalkToBotService talkToBotService;
	
	@Autowired
	private BotService botService;

	@Autowired
	private Publisher publisher;
	
	@EventListener
	public SendMessageReceivedToBotEvent handleMessageReceivedEvent(MessageReceivedEvent event) throws MiddlewareWebException {
		
		WebhookDTO dto = event.getWebHookDto();

		Bot bot = botService.findByBotId(dto.getBot());
		
		if(bot == null) {
			throw new MiddlewareWebException("Bot no encontrado : " + dto.getBot());
		}
		CustomMessage customMessage = bot.getCustomMessage();
		SendConfig sendConfig = bot.getSendConfig();
		Message message = new Message();
		message.setChatId(dto.getChatId());
		message.setDepartmentId(dto.getDepartmentId());
		message.setText(dto.getMessage());
		message.setMessageorigin(MessageOrigin.USER);
		message.setUrl(dto.getUrl());
		message.setBot(bot);
		
		messageService.save(message);
		LOG.info("Mensage almacenado chatId: {}, bot:{}", dto.getChatId(), dto.getBot());
	
		return new SendMessageReceivedToBotEvent(bot, dto.getChatId().toString(), dto.getMessage(),customMessage,sendConfig);
		
	}
	
	@EventListener
	public PersistBotResponseEvent handleSendMessageReceivedToBot(SendMessageReceivedToBotEvent event) {
		LOG.info("Evento Send Message Recived to Bot - chatId: {}, bot:{} " , event.getChatId(), event.getBot().getBotId());
	
		ResponseDTO response = talkToBotService.sendMessageToBot(event.getBot(), event.getChatId(), event.getText(),event.getCustomMessage());
		
		return new PersistBotResponseEvent(event.getBot(), event.getChatId(), response,event.getSendConfig());
		
	}
	
	@EventListener
	public SendMessageEvent handlePersistBotResponseEvent(PersistBotResponseEvent event) {
		LOG.info("Evento Persist Bot Response - chatId: {}, bot:{}",event.getUserId(), event.getBot().getBotId());
		ResponseDTO botResponseDTO = event.getResponseDTO();
		
		Message message = new Message();
		message.setBot(event.getBot());
		message.setChatId(Long.parseLong(event.getUserId()));
		message.setMessageorigin(MessageOrigin.BOT);
		message.setText(botResponseDTO.getText());
		message.setAction(botResponseDTO.getAction());
		
		messageService.save(message);
		return new SendMessageEvent(event.getUserId(),botResponseDTO, event.getSendConfig(), event.getBot().getClosingMessages());
	}
	
	@EventListener
	public void handleSendMessageEvent(SendMessageEvent event) throws MiddlewareWebException {
		
		if(event.getSendConfig() == null) {
			throw new MiddlewareWebException("No existe configuracion para envio de mensaje");
		}
	
		LinkedMultiValueMap<String, Object> data = toMapFormData(
												"chat_id", event.getChatId(), 
												"msg", event.getResponse().getText(),
												"sender", "bot", 
												"meta_msg", event.getResponse().getCustomMessage());
		
		sendService.sendMessage(event.getSendConfig(), data);
		
		
		ClosingMessage closingMessage = null;
		if(event.getClossingMessages() != null && !event.getClossingMessages().isEmpty()) {

			closingMessage = event.getClossingMessages().stream()
					.filter(clos ->  event.getResponse().getAction().equals(clos.getAction())).findFirst().orElse(null);
			
		}
		
		if(event.getResponse().isEnded() && closingMessage != null && event.getResponse().getCustomMessage() == null) {
			publisher.publisSendClossingMessage(new SendClosingMessageEvent(closingMessage.getMessage(), event.getChatId(),closingMessage.getDelay(),event.getSendConfig()));
		}
		
	}
	
	
	@EventListener
	public  void handleSendClosingMessage(SendClosingMessageEvent event) throws MiddlewareWebException, InterruptedException {
		if(event.getText() == null || event.getChatId() == null) {
			throw new MiddlewareWebException("No existe configuracion para envio de mensaje");
		}
		
		Long delay = event.getDelay();
		if(event.getDelay() != null && event.getDelay() > 0) {
			TimeUnit.SECONDS.sleep(delay);
		}
		
		LinkedMultiValueMap<String, Object> data = toMapFormData(
				"chat_id", event.getChatId(), 
				"msg", event.getText(),
				"sender", "bot");

		sendService.sendMessage(event.getConfig(), data);
	}

}
