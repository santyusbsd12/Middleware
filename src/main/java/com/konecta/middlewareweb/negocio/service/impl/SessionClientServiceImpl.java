package com.konecta.middlewareweb.negocio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konecta.middlewareweb.model.SessionClient;
import com.konecta.middlewareweb.negocio.service.SessionClientService;
import com.konecta.middlewareweb.repositories.SessionClientRepository;

@Service
public class SessionClientServiceImpl implements SessionClientService {
	
	
	@Autowired
	private  SessionClientRepository sessionClientRepository;

	@Override
	public SessionClient obtainSessionClient(String sessionId) {
		SessionClient session= sessionClientRepository.findBySessionId(sessionId);
		
		if(session != null) {
			return session;
		}else {
			SessionClient sc = new SessionClient();
			sc.setSessionId(sessionId);
			return sessionClientRepository.save(sc);
		}
		
	}

	@Override
	public SessionClient save(SessionClient sessionClient) {
		return sessionClientRepository.save(sessionClient);
	}
	
	

}
