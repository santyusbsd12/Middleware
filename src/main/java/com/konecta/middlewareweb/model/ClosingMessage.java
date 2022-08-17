package com.konecta.middlewareweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="closing_message")
@EntityListeners(AuditingEntityListener.class)
public class ClosingMessage extends AbstractEntity {

	private static final long serialVersionUID = -4249260640432275222L;
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "closing_message_id_gen")
	@SequenceGenerator(name = "closing_message_id_gen", sequenceName = "CLOSING_MESSAGE_SEQ", allocationSize = 1)
	private Long id;
	@Column(name = "MESSAGE",length = 1000)
	private String message;
	@Column(name = "ACTION")
	private String action;
	@Column(name = "DELAY")
	private long delay;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BOT")
	private Bot bot;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}

}
