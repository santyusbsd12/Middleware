package com.konecta.middlewareweb.negocio.service;

import com.konecta.middlewareweb.model.CustomMessage;

public interface CustomMessageService {
	
	 CustomMessage findByBotId(String botId);

}
