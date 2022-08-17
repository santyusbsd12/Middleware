package com.konecta.middlewareweb.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "SESSION_CLIENT")
@EntityListeners(AuditingEntityListener.class)
public class SessionClient extends AbstractEntity {
	
	private static final long serialVersionUID = 5535505497051899193L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_client_id_gen")
	@SequenceGenerator(name = "session_client_id_gen", sequenceName = "SESSION_CLIENT_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "SESSION_ID")
	private String sessionId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	private Date lastMessageAt;
	
	
	@Type(type = "true_false")
	private boolean conversationEnded;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public Date getLastMessageAt() {
		return lastMessageAt;
	}


	public void setLastMessageAt(Date lastMessageAt) {
		this.lastMessageAt = lastMessageAt;
	}


	public boolean isConversationEnded() {
		return conversationEnded;
	}


	public void setConversationEnded(boolean conversationEnded) {
		this.conversationEnded = conversationEnded;
	}


}
