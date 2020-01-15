package com.desafio.concrete.service;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.User;

public interface UserService {

	User findByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);

	Response<User> createOrUpdate(User user);
}
