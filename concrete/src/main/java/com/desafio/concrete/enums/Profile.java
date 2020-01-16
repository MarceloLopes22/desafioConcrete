package com.desafio.concrete.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Profile {
	ROLE_USUARIO,
	ROLE_ADMIN;
	
	@JsonCreator
	public static Profile getNameByValue(final int value) {
        for (final Profile p: Profile.values()) {
            if (p.ordinal() == value) {
                return p;
            }
        }
        return null;
	}
}
