package com.konecta.middlewareweb.negocio.service.impl;

import static com.konecta.middlewareweb.negocio.util.Utils.isMoreThanMinutesAgo;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.v2.Intent.Message;
import com.google.cloud.dialogflow.v2.Context;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.konecta.middlewareweb.dialogflow.DialogFlowClient;
import com.konecta.middlewareweb.dialogflow.dto.DetectIntentDTO;
import com.konecta.middlewareweb.dialogflow.dto.ResponseDTO;
import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.model.CustomMessage;
import com.konecta.middlewareweb.model.SessionClient;
import com.konecta.middlewareweb.negocio.service.RestApiService;
import com.konecta.middlewareweb.negocio.service.SessionClientService;
import com.konecta.middlewareweb.negocio.service.TalkToBotService;


@Service
public class TalkToBotServiceImpl implements TalkToBotService{
	
	private static final Logger LOG = LoggerFactory.getLogger(TalkToBotServiceImpl.class);
	
	private static final String WELCOME_EVENT = "Bienvenida";
	
	@Autowired
	private SessionClientService sessionClientService;
	
	@Autowired
	private DialogFlowClient dialogFlowClient;
	
	@Autowired
	private ScriptEngine engineJs;
	
	@Autowired
	private Invocable invocable;
	
	@Autowired
	private RestApiService restApiService;


	@Override
	public ResponseDTO sendMessageToBot(Bot bot, String userId, String text,CustomMessage customMessage) {
		
		ResponseDTO botResponseDTO = new ResponseDTO();
		
		String sessionId = bot.getBotId() + "_" + userId;
		SessionClient sessionClient = sessionClientService.obtainSessionClient(sessionId);
		
		DetectIntentDTO detectInput = new DetectIntentDTO();
		detectInput.setMessage(text);
		detectInput.setSessionId(sessionClient.getSessionId());
		
		Instant startRequestDialogFlow = Instant.now();
		QueryResult queryResult = null;
		LOG.info("Inicio Peticion a DialogFlow, botId: {} , Session:{}", bot.getBotId(),sessionId);
		
		try {
			queryResult = sendMessageToDialogFlow(detectInput, bot, sessionClient);
		}catch(Exception ex) {
			LOG.error("Ocurrio un error al comunicarse con el bot: {}",bot.getBotId());
			LOG.error("Problema de comunicacion con el bot", ex);
			botResponseDTO.setText("El bot no recibió tu mensaje lamentablemente, intenta de nuevo más tarde.");
			return botResponseDTO;
		}
		
		LOG.info("Fin Peticion a DialogFlow, botId: {} , Session:{}", bot.getBotId(),sessionId);
		LOG.info("Duracion DialogFlow, botId: {} , Session:{} , tiempo:{} ms", bot.getBotId(),sessionId,Duration.between(startRequestDialogFlow, Instant.now()).toMillis());
		
		sessionClient.setLastMessageAt(new Date());
		sessionClient.setConversationEnded(queryResult.getIntent().getEndInteraction());
		
		sessionClientService.save(sessionClient);
		
		List<String> botMessages = new ArrayList<>();
		
		for(Message mensaje: queryResult.getFulfillmentMessagesList()) {
			botMessages.add(mensaje.getText().getText(0));
		}
		
		String finalAnswer = botMessages.stream().map(msg -> 
				msg.replace("\\n", "\n"))
				.collect(Collectors.joining("\n"));
		
		long inicioGral  = System.currentTimeMillis();
		try {
			long inicioEval  = System.currentTimeMillis();
			engineJs.put("api", restApiService);
			engineJs.put("LOG", LOG);
			engineJs.eval(customMessage.getScript());
			long finEval  = System.currentTimeMillis();
			long totalEval = finEval - inicioEval;
			
			LOG.info("Tiempo de compilacion {} milisegundos", totalEval);
			
			@SuppressWarnings("unchecked")
			Map<String, Object> responseJS = (Map<String, Object>) invocable.invokeFunction("proccessMessage", queryResult.getAction(),customMessage.getCustomDesign(),text);
			
			if(responseJS != null) {
				String message = (String) responseJS.get("msg");
				String metaMessage = (String) responseJS.get("meta_msg");
				
				finalAnswer = (message != null ? finalAnswer +"\n" + message : finalAnswer) ;
				botResponseDTO.setCustomMessage(metaMessage);
			}
			
		} catch (ScriptException | NoSuchMethodException e) {
			LOG.error("{}", e);
		}
		
		long finGral  = System.currentTimeMillis();
		long totalEval = finGral - inicioGral;
		LOG.info("Tiempo total dedicado a GraalVM {} milisegundos", totalEval);
		botResponseDTO.setText(finalAnswer);
		botResponseDTO.setAction(queryResult.getIntent().getDisplayName());
		botResponseDTO.setEnded(queryResult.getIntent().getEndInteraction());
		return botResponseDTO;
	}
	
	
	
	protected QueryResult sendMessageToDialogFlow(DetectIntentDTO detectIntentDTO, Bot bot, SessionClient sessionClient) throws IOException {
		
		if (sessionClient.isConversationEnded() || isMoreThanMinutesAgo(sessionClient.getLastMessageAt(),20))
			detectIntentDTO.setEvent(WELCOME_EVENT);
		
		QueryResult query = dialogFlowClient.detectIntent(detectIntentDTO, bot).getQueryResult();
		
		if (thereAreNoContexts(query.getOutputContextsList())
				&& query.getIntent().getDisplayName().contains("fallback")) {

			detectIntentDTO.setEvent(WELCOME_EVENT);
			query = dialogFlowClient.detectIntent(detectIntentDTO, bot).getQueryResult();
		}
		
		return query;
	}
	
	protected boolean thereAreNoContexts(List<Context> contexts) {
		
		if (contexts.isEmpty())
			return true;
		else if (contexts.size() == 1 && contexts.get(0).getName().contains("__system_counters__")) {
			return true;
		}
		
		return false;
	}

}
