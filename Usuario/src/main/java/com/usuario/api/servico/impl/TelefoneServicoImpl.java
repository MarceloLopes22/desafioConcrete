package com.usuario.api.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.usuario.api.entidades.Telefone;
import com.usuario.api.repositorio.TelefoneRepositorio;
import com.usuario.api.servico.TelefoneServico;

@Service
public class TelefoneServicoImpl  implements TelefoneServico {
	
	@Autowired
	private TelefoneRepositorio repositorio;

	@Override
	public Telefone createOrUpdate(Telefone avenida) {
		return this.repositorio.save(avenida);
	}

	@Override
	public Telefone findByIdAvenida(Integer idAvenida) {
		return this.repositorio.findById(idAvenida).get();
	}

	@Override
	public void delete(Integer idAvenida) {
		this.repositorio.deleteById(idAvenida);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page<Telefone> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		Page<Telefone> lista = this.repositorio.findAll(pages);
		return lista;
	}
}
