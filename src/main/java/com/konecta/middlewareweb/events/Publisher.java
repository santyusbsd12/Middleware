package com.konecta.middlewareweb.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.konecta.middlewareweb.events.dto.MessageReceivedEvent;
import com.konecta.middlewareweb.events.dto.SendClosingMessageEvent;

@Component
public class Publisher {
	
	private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	
	public void publishMessageRecived(final MessageReceivedEvent messageReceivedEvent) {
		LOG.info("Evento Message Recived Recibido");
		eventPublisher.publishEvent(messageReceivedEvent);
		LOG.info("Evento Message Recived Publicado");
	}
	
	public void publisSendClossingMessage(final SendClosingMessageEvent sendClosingMessageEvent) {
		LOG.info("Evento Send Closing Message Recibido");
		eventPublisher.publishEvent(sendClosingMessageEvent);
		LOG.info("Evento Send Closing Message Publicado");
	}
	

}
