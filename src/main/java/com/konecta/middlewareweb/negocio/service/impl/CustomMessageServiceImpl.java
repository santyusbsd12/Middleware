package com.konecta.middlewareweb.negocio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konecta.middlewareweb.model.CustomMessage;
import com.konecta.middlewareweb.negocio.service.CustomMessageService;
import com.konecta.middlewareweb.repositories.CustomMessageRepository;


@Service
public class CustomMessageServiceImpl implements CustomMessageService{
	
	
	
	@Autowired
	private CustomMessageRepository customMessageRepository;
	
	
	public CustomMessage findByBotId(String botId) {
		return customMessageRepository.findCustomMessageByBotId(botId);
	}
	
	

}
