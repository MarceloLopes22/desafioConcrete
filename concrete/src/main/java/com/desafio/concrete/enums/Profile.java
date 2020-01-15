package com.desafio.concrete.enums;

public enum Profile {
	ROLE_USUARIO,
	ROLE_ADMIN;
	
	
	public static Profile getPerfil(String value) {
		return ROLE_USUARIO;
	}
}
