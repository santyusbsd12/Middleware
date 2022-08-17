package com.konecta.middlewareweb.negocio.service;

import java.io.IOException;

import com.google.cloud.dialogflow.v2.SessionsClient;
import com.konecta.middlewareweb.model.ServiceAccount;

public interface SessionService {
	
	SessionsClient getSessionClient(ServiceAccount serviceAccount) throws IOException;

}
