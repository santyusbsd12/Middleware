package com.konecta.middlewareweb.events.dto;

import com.konecta.middlewareweb.dialogflow.dto.ResponseDTO;
import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.model.SendConfig;

public class PersistBotResponseEvent {
	
	private Bot bot;
	private String userId;
	private ResponseDTO responseDTO;
	private SendConfig sendConfig;
	
	public PersistBotResponseEvent(Bot bot, String userId, ResponseDTO responseDTO, SendConfig sendConfig) {
		this.bot = bot;
		this.userId = userId;
		this.responseDTO = responseDTO;
		this.sendConfig = sendConfig;
	}
	
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ResponseDTO getResponseDTO() {
		return responseDTO;
	}
	public void setResponseDTO(ResponseDTO responseDTO) {
		this.responseDTO = responseDTO;
	}

	public SendConfig getSendConfig() {
		return sendConfig;
	}

	public void setSendConfig(SendConfig sendConfig) {
		this.sendConfig = sendConfig;
	}
	
	

}
