package com.konecta.middlewareweb.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Bot")
@EntityListeners(AuditingEntityListener.class)
public class Bot extends AbstractEntity{
	
	private static final long serialVersionUID = -7219906575933251057L;
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bot_id_gen")
	@SequenceGenerator(name = "bot_id_gen", sequenceName = "BOT_SEQ", allocationSize = 1)
	private Long id;
	@Column(name = "BOT_ID")
	private String botId;
	@Column(name = "LANGUAGE_CODE")
	private String languageCode;
	@Column(name = "NAME")
	private String name;
	@ManyToOne
	@JoinColumn(name = "SERVICE_ACCOUNT_ID", nullable = false)
	private ServiceAccount serviceAccount;
	
	@OneToOne(mappedBy= "bot")
	private CustomMessage customMessage;

	@OneToOne(mappedBy = "bot")
	private SendConfig sendConfig;
	
	@OneToMany(mappedBy= "bot")
	private Set<ClosingMessage> closingMessages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBotId() {
		return botId;
	}

	public void setBotId(String botId) {
		this.botId = botId;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceAccount getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(ServiceAccount serviceAccount) {
		this.serviceAccount = serviceAccount;
	}


	public CustomMessage getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(CustomMessage customMessage) {
		this.customMessage = customMessage;
	}

	public SendConfig getSendConfig() {
		return sendConfig;
	}

	public void setSendConfig(SendConfig sendConfig) {
		this.sendConfig = sendConfig;
	}

	public Set<ClosingMessage> getClosingMessages() {
		return closingMessages;
	}

	public void setClosingMessages(Set<ClosingMessage> closingMessages) {
		this.closingMessages = closingMessages;
	}
	



}
