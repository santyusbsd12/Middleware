package com.konecta.middlewareweb.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.middlewareweb.controller.rest.dto.WebhookDTO;
import com.konecta.middlewareweb.events.Publisher;
import com.konecta.middlewareweb.events.dto.MessageReceivedEvent;
import com.konecta.middlewareweb.negocio.util.EncriptadoUtil;

@RestController
public class WebhookController {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(WebhookController.class);
	
	@Autowired
	private Publisher publisher;

	
	
	@PostMapping("/webhook")
	public ResponseEntity<String> webhook(@RequestBody WebhookDTO dto) {
		LOG.info("Mensaje entrante desde origen : {}" , dto.getUrl());
		LOG.info("Mensaje de chat : {}, bot : {}", dto.getChatId(), dto.getBot());
		publisher.publishMessageRecived(new MessageReceivedEvent(dto));
		LOG.info("Mensaje recibido message : {} ", dto.getMessage());
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	
	@GetMapping("/encript")
	public String encript(@RequestParam String cadena) {
		return EncriptadoUtil.encrypt(cadena);
	}

}
