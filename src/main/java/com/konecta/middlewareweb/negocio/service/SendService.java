package com.konecta.middlewareweb.negocio.service;

import org.springframework.util.LinkedMultiValueMap;

import com.konecta.middlewareweb.model.SendConfig;

public interface SendService {
	
	void sendMessage(SendConfig config, LinkedMultiValueMap<String, Object>  params);

}
