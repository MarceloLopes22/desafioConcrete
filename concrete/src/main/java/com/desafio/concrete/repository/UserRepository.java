package com.desafio.concrete.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.concrete.entidades.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmailAndPassword(String email, String password);
	
	User findUserByEmail(String email);
	
	User findUserByToken(String token);
}
