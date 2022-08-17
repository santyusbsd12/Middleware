package com.konecta.middlewareweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SERVICE_ACCOUNT")
@EntityListeners(AuditingEntityListener.class)
public class ServiceAccount extends AbstractEntity{

	private static final long serialVersionUID = -605388563531250582L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_account_id_gen")
	@SequenceGenerator(name = "service_account_id_gen", sequenceName = "SERVICE_ACCOUNT_SEQ", allocationSize = 1)
	private Long id;
	@Column(name = "CLIENT_EMAIL")
	private String clientEmail;
	@Column(name = "CLIENT_ID")
	private String clientId;
	@Column(name = "PRIVATE_KEY")
	private String privateKey;
	@Column(name = "PRIVATE_KEY_ID")
	private String privateKeyId;
	@Column(name = "PROJECT_ID")
	private String projectId;
	@Column(name = "json")
	private String json;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPrivateKeyId() {
		return privateKeyId;
	}
	public void setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	

}
