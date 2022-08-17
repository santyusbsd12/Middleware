package com.konecta.middlewareweb.events.dto;

import com.konecta.middlewareweb.model.SendConfig;

public class SendClosingMessageEvent {
	
	private String text;
	private String chatId;
	private Long delay;
	private SendConfig config;
	public SendClosingMessageEvent(String text, String chatId, Long delay,SendConfig config) {
		super();
		this.text = text;
		this.chatId = chatId;
		this.delay = delay;
		this.config = config;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public Long getDelay() {
		return delay;
	}
	public void setDelay(Long delay) {
		this.delay = delay;
	}
	public SendConfig getConfig() {
		return config;
	}
	public void setConfig(SendConfig config) {
		this.config = config;
	}
	

}
