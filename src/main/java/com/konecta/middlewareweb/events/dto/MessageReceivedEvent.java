package com.konecta.middlewareweb.events.dto;

import com.konecta.middlewareweb.controller.rest.dto.WebhookDTO;

public class MessageReceivedEvent {
	
	
	private WebhookDTO webHookDto;
	
	
	public MessageReceivedEvent(WebhookDTO webhookDTO) {
		this.webHookDto = webhookDTO;
	}

	public WebhookDTO getWebHookDto() {
		return webHookDto;
	}

	public void setWebHookDto(WebhookDTO webHookDto) {
		this.webHookDto = webHookDto;
	}
	
	
	
}
