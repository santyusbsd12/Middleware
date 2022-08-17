package com.konecta.middlewareweb.dialogflow.dto;

public class ResponseDTO {
	
	private String text;
	private String customMessage;
	private String action;
	private boolean ended;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	
}
