package com.konecta.middlewareweb.negocio.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface RestApiService {

	<T, V> ResponseEntity<T> execute(String url, V data, HttpMethod httpMethod, Class<T> responseType, HttpHeaders headers);
}
