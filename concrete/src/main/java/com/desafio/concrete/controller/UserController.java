package com.desafio.concrete.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.service.UserService;

@RestController
@RequestMapping("/api/usuario/")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userServico;
	
	@PostMapping(value = "criar/")
	public ResponseEntity<Response<User>> criar(HttpServletRequest request, @RequestBody User user, BindingResult result) {
		Response<User> response = userServico.create(user);
		HttpStatus httpStatus = response.getStatus();
		return new ResponseEntity<Response<User>>(response, httpStatus);
	}
	
	@RequestMapping(value = "login/", method = RequestMethod.GET)
	public ResponseEntity<Response<User>> login(@RequestBody User user) {
		Response<User> response = userServico.login(user);
		HttpStatus httpStatus = response.getStatus();
		return new ResponseEntity<Response<User>>(response, httpStatus);
	}
	
	@RequestMapping(value = "listar/", method = RequestMethod.GET)
	public ResponseEntity<Response<List<User>>> login() {
		Response<List<User>> response = userServico.users();
		HttpStatus httpStatus = response.getStatus();
		return new ResponseEntity<Response<List<User>>>(response, httpStatus);
	}
	
}
