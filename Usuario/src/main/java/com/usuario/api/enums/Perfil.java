package com.usuario.api.enums;

public enum Perfil {
	ROLE_USUARIO,
	ROLE_ADMIN;
	
	
	public static Perfil getPerfil(String valor) {
		return ROLE_USUARIO;
	}
}
