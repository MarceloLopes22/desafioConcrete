package com.desafio.concrete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.concrete.entidades.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
	
}
