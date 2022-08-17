package com.konecta.middlewareweb.events.dto;

import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.model.CustomMessage;
import com.konecta.middlewareweb.model.SendConfig;

public class SendMessageReceivedToBotEvent {
	
	private Bot bot;
	private String chatId;
	private String text;
	private CustomMessage customMessage;
	private SendConfig sendConfig;
	
	public SendMessageReceivedToBotEvent(Bot bot, String chatId, String text,CustomMessage customMessage, SendConfig sendConfig) {
		this.bot = bot;
		this.chatId = chatId;
		this.text = text;
		this.customMessage = customMessage;
		this.sendConfig = sendConfig;
	}
	
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public CustomMessage getCustomMessage() {
		return customMessage;
	}
	public void setCustomMessage(CustomMessage customMessage) {
		this.customMessage = customMessage;
	}
	public SendConfig getSendConfig() {
		return sendConfig;
	}
	public void setSendConfig(SendConfig sendConfig) {
		this.sendConfig = sendConfig;
	}


}
