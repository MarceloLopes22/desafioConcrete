package com.desafio.concrete.controller.response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;


public class Response<T> {

	private T dado;
	
	private HttpStatus status;
	
	private Map<String,String> erros;

	public T getDado() {
		return dado;
	}

	public void setDado(T dado) {
		this.dado = dado;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Map<String, String> getErros() {
		if (erros == null) {
			this.erros = new HashMap<String,String>();
		}
		return erros;
	}

	public void setErros(Map<String, String> erros) {
		this.erros = erros;
	}
}
