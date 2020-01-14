package com.usuario.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.usuario.api.entidades.Telefone;

@Component
public interface TelefoneRepositorio extends JpaRepository<Telefone, Integer> {
	
}
