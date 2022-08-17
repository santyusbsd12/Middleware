package com.konecta.middlewareweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.konecta.middlewareweb.model.enums.MessageOrigin;


@Entity
@Table(name = "Message")
@EntityListeners(AuditingEntityListener.class)
public class Message extends AbstractEntity {
	
	private static final long serialVersionUID = 6634002959263405877L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_gen")
	@SequenceGenerator(name = "message_id_gen", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "CHAT_ID")
	private Long chatId;
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	@Column(name = "TEXT")
	private String text;
	@ManyToOne(fetch = FetchType.LAZY)
	private Bot bot;
	
	@Enumerated(EnumType.STRING)
	private MessageOrigin messageorigin;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "action")
	private String action;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public MessageOrigin getMessageorigin() {
		return messageorigin;
	}
	public void setMessageorigin(MessageOrigin messageorigin) {
		this.messageorigin = messageorigin;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
