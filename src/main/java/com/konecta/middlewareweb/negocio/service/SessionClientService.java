package com.konecta.middlewareweb.negocio.service;

import com.konecta.middlewareweb.model.SessionClient;

public interface SessionClientService {
	SessionClient obtainSessionClient(String sessionId);
	SessionClient save(SessionClient sessionClient);
}
