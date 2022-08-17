package com.konecta.middlewareweb.negocio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.konecta.middlewareweb.negocio.service.RestApiService;


@Service
public class RestApiServiceImpl implements RestApiService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public <T, V> ResponseEntity<T> execute(String url, V data, HttpMethod httpMethod, Class<T> responseType, HttpHeaders headers) {

		if(null == headers)
			headers = new HttpHeaders();
		
		HttpEntity<V> httpEntity = new HttpEntity<>(data, headers);
		return restTemplate.exchange(url, httpMethod, httpEntity, responseType);
	}
	

}
