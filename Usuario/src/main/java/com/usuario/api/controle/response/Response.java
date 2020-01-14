package com.usuario.api.controle.response;

import java.util.HashMap;
import java.util.Map;


public class Response<T> {

	private T dado;
	
	private Object status;
	
	private Map<String,String> erros;

	public T getDado() {
		return dado;
	}

	public void setDado(T dado) {
		this.dado = dado;
	}

	public Object getStatus() {
		return status;
	}

	public void setStatus(Object status) {
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
