package com.konecta.middlewareweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.middlewareweb.model.SessionClient;

public interface SessionClientRepository  extends JpaRepository<SessionClient, Long>{
	
	SessionClient findBySessionId(String sessionId);

}
