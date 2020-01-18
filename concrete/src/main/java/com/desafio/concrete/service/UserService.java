package com.desafio.concrete.service;

import java.util.List;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.LoginDto;
import com.desafio.concrete.entidades.User;

public interface UserService {

	User findByEmailAndPassword(String email, String password);
	
	Response<User> findByEmail(String email);
	
	Response<User> create(User user);
	
	void update(User user);

	Response<User> login(LoginDto loginDto);
	
	User findUserByToken(String token);
	
	void delete(User user);
	
	Response<List<User>> users();
}
