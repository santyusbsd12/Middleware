package com.konecta.middlewareweb.negocio.service;

import com.konecta.middlewareweb.dialogflow.dto.ResponseDTO;
import com.konecta.middlewareweb.model.Bot;
import com.konecta.middlewareweb.model.CustomMessage;

public interface TalkToBotService {

	ResponseDTO sendMessageToBot(Bot bot, String userId, String text,CustomMessage customMessage);
}
