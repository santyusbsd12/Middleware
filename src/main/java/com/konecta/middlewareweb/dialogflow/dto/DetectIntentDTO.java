package com.konecta.middlewareweb.dialogflow.dto;

public class DetectIntentDTO {
	
	private String message;
	private String sessionId;
	private String event;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}

}
