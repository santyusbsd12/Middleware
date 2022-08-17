package com.konecta.middlewareweb.negocio.exception;

public class MiddlewareWebException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public MiddlewareWebException(String message) {
		super(message);
	}
	
	public MiddlewareWebException(String message, Throwable cause) {
		super(message,cause);
	}

}
