
package com.konecta.middlewareweb.dialogflow;

import java.io.IOException;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.konecta.middlewareweb.dialogflow.dto.DetectIntentDTO;
import com.konecta.middlewareweb.model.Bot;

public interface DialogFlowClient {
	public DetectIntentResponse detectIntent(DetectIntentDTO dto, Bot bot) throws IOException;
}
