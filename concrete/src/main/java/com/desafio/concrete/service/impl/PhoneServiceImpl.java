package com.desafio.concrete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.repository.PhoneRepository;
import com.desafio.concrete.service.PhoneService;

@Service
public class PhoneServiceImpl  implements PhoneService {
	
	@Autowired
	private PhoneRepository repository;

	@Override
	public List<Phone> createOrUpdate(List<Phone> phones) {
		return this.repository.saveAll(phones);
	}

	@Override
	public Phone findByIdAvenida(Integer idPhone) {
		return this.repository.findById(idPhone).get();
	}

	@Override
	public void delete(Integer idPhone) {
		this.repository.deleteById(idPhone);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page<Phone> findAll(int page, int count) {
		Pageable pages = new PageRequest(page, count);
		Page<Phone> lista = this.repository.findAll(pages);
		return lista;
	}
}
