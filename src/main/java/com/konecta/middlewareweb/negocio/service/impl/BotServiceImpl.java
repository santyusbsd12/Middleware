package com.konecta.middlewareweb.negocio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.negocio.service.BotService;
import com.konecta.middlewareweb.repositories.BotRepository;



@Service
public class BotServiceImpl implements BotService{
	
	@Autowired
	private BotRepository botRepository;

	@Override
	public Bot findByBotId(String botId) {
		return botRepository.findByBotId(botId);
	}
	
	

}
