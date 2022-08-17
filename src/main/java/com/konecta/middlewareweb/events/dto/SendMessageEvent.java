package com.konecta.middlewareweb.events.dto;

import java.util.Set;

import com.konecta.middlewareweb.dialogflow.dto.ResponseDTO;
import com.konecta.middlewareweb.model.ClosingMessage;
import com.konecta.middlewareweb.model.SendConfig;

public class SendMessageEvent {
	
	private String chatId;
	private ResponseDTO response;
	private SendConfig sendConfig;
	private Set<ClosingMessage> clossingMessages;
	
	
	public SendMessageEvent(String chatId,ResponseDTO response ,SendConfig sendConfig, Set<ClosingMessage> clossingMessages) {
		this.chatId = chatId;
		this.response = response;
		this.sendConfig = sendConfig;
		this.clossingMessages = clossingMessages;
	}
	
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public SendConfig getSendConfig() {
		return sendConfig;
	}

	public void setSendConfig(SendConfig sendConfig) {
		this.sendConfig = sendConfig;
	}

	public Set<ClosingMessage> getClossingMessages() {
		return clossingMessages;
	}

	public void setClossingMessages(Set<ClosingMessage> clossingMessages) {
		this.clossingMessages = clossingMessages;
	}

	public ResponseDTO getResponse() {
		return response;
	}

	public void setResponse(ResponseDTO response) {
		this.response = response;
	}
	
	

}
