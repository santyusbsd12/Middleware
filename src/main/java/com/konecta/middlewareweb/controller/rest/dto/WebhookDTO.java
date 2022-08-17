package com.konecta.middlewareweb.controller.rest.dto;

public class WebhookDTO {
	
	private Long chatId;
	private String user;
	private Long departmentId;
	private String message;
	private String bot;
	private String url;
	
	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBot() {
		return bot;
	}
	public void setBot(String bot) {
		this.bot = bot;
	}
	

}
