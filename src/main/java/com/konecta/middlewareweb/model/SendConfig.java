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
@Table(name = "send_config")
@EntityListeners(AuditingEntityListener.class)
public class SendConfig extends AbstractEntity{
	private static final long serialVersionUID = -2095924746680027486L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "send_config_id_gen")
	@SequenceGenerator(name = "send_config_id_gen", sequenceName = "SEND_CONFIG_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "URL")
	private String url;
	@Column(name = "US")
	private String us;
	@Column(name = "PASS")
	private String pass;
	@Column(name = "TOKEN_PATHERN")
	private String tokenPathern;
	
	@OneToOne
	@JoinColumn(name = "id_bot", referencedColumnName = "id")
	private Bot bot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUs() {
		return us;
	}

	public void setUs(String us) {
		this.us = us;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTokenPathern() {
		return tokenPathern;
	}

	public void setTokenPathern(String tokenPathern) {
		this.tokenPathern = tokenPathern;
	}

	public Bot getBot() {
		return bot;
	}

	public void setBot(Bot bot) {
		this.bot = bot;
	}



}
