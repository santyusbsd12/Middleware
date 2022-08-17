package com.konecta.middlewareweb.dialogflow.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.EventInput;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.konecta.middlewareweb.dialogflow.DialogFlowClient;
import com.konecta.middlewareweb.dialogflow.dto.DetectIntentDTO;
import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.negocio.service.SessionService;

@Service
public class DialogFlowClientImpl implements DialogFlowClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(DialogFlowClientImpl.class);
	
	
	@Value("${enviroment.dialogflowclient:Dev}")
	private String enviroment;
	
	
	@Autowired
	private SessionService sessionService;
	
	
	@Override
	public DetectIntentResponse detectIntent(DetectIntentDTO dto,Bot bot) throws IOException {
		String projectId = bot.getBotId();
		String sessionId = dto.getSessionId();
		
		SessionName session = SessionName.ofProjectEnvironmentUserSessionName(projectId, enviroment, "-", sessionId);
		QueryInput queryInput = null;
		
		long inicioInput = System.currentTimeMillis();
		
		if(StringUtils.isBlank(dto.getEvent())) {
			TextInput textInput = TextInput.newBuilder().setText(dto.getMessage())
					.setLanguageCode(bot.getLanguageCode()).build();
			queryInput = QueryInput.newBuilder().setText(textInput).build();
		}else {
			EventInput eventInput = EventInput.newBuilder().setName(dto.getEvent())
					.setLanguageCode(bot.getLanguageCode()).build();
			queryInput = QueryInput.newBuilder().setEvent(eventInput).build();
		}
		
		long finInput = System.currentTimeMillis();
		long totalInput = finInput - inicioInput;
		LOG.info("Tiempo generando Input DialogFlow {} milisegundos", totalInput);
		
		long inicioSC = System.currentTimeMillis();
		
		SessionsClient sessionsClient = sessionService.getSessionClient(bot.getServiceAccount());
		
		long finSC = System.currentTimeMillis();
		
		long totalSC = finSC - inicioSC;
		LOG.info("Tiempo generando SesionClient DialogFlow {} milisegundos", totalSC);
		
		long inicioResponse = System.currentTimeMillis();
		
		DetectIntentResponse response = sessionsClient.detectIntent(session,queryInput);
		
		long finResponse = System.currentTimeMillis();
		
		long totalResponse = finResponse - inicioResponse;
		
		LOG.info("Tiempo generando Respuesta DialogFlow {} milisegundos", totalResponse);
		
		sessionsClient.shutdown();
		try {
			if (!sessionsClient.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
				sessionsClient.shutdownNow();
				if (!sessionsClient.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
					LOG.warn("La conexión con DialogFlow no fué terminada.");
				}
			}
		} catch (InterruptedException e) {
			sessionsClient.shutdownNow();
			Thread.currentThread().interrupt();

		}

		return response;
		
	}
	
	
}
