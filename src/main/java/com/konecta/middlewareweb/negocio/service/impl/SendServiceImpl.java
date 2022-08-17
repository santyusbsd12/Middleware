package com.konecta.middlewareweb.negocio.service.impl;

import static com.konecta.middlewareweb.negocio.util.EncriptadoUtil.decrypt;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.konecta.middlewareweb.model.SendConfig;
import com.konecta.middlewareweb.negocio.service.RestApiService;
import com.konecta.middlewareweb.negocio.service.SendService;

@Service
public class SendServiceImpl implements SendService{
	
	@Autowired
	RestApiService restApiService;
	

	@Override
	public void sendMessage(SendConfig config, LinkedMultiValueMap<String, Object>  params) {
		String url = config.getUrl();
		
		String us = config.getUs();
		String ps = decrypt(config.getPass());
		
		String auth = us + ":" + ps;
		
		
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = config.getTokenPathern().replace("{{1}}", new String(encodedAuth));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);	         
		headers.set( "Authorization", authHeader );


		
		restApiService.execute(url, params, HttpMethod.POST, String.class, headers);
	}

}
