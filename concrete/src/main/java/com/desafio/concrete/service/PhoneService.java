package com.desafio.concrete.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.desafio.concrete.entidades.Phone;

public interface PhoneService {

	List<Phone> createOrUpdate(List<Phone> phones);
	
	Phone findByIdAvenida(Integer idPhone);
	
	void delete(Integer idPhone);
	
	Page<Phone> findAll(int page, int count);
}
