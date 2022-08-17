package com.konecta.middlewareweb.negocio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konecta.middlewareweb.model.Message;
import com.konecta.middlewareweb.negocio.service.MessageService;
import com.konecta.middlewareweb.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Message save(Message m) {
		return messageRepository.save(m);
	}
}
