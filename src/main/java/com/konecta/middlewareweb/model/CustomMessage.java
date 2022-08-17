package com.konecta.middlewareweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "CUSTOM_MESSAGE")
@EntityListeners(AuditingEntityListener.class)
public class CustomMessage extends AbstractEntity{
	private static final long serialVersionUID = -5359630010459951419L;
	
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_message_id_gen")
	@SequenceGenerator(name = "custom_message_id_gen", sequenceName = "CUSTOM_MESSAGE_SEQ", allocationSize = 1)
	private Long id;

	@Column(name = "SCRIPT")
	private String script;
	
	@Column(name = "CUSTOM_DESIGN")
	private String customDesign;

	
	@OneToOne
	@JoinColumn(name = "ID_BOT", referencedColumnName = "id")
	private Bot bot;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getCustomDesign() {
		return customDesign;
	}
	public void setCustomDesign(String customDesign) {
		this.customDesign = customDesign;
	}
	
}
