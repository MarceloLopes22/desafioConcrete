package com.usuario.api.servico;

import org.springframework.data.domain.Page;

import com.usuario.api.entidades.Telefone;

public interface TelefoneServico {

	Telefone createOrUpdate(Telefone avenida);
	
	Telefone findByIdAvenida(Integer idAvenida);
	
	void delete(Integer idAvenida);
	
	Page<Telefone> findAll(int page, int count);
}
