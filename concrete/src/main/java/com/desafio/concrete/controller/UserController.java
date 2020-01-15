package com.desafio.concrete.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.service.UserService;

@RestController
@RequestMapping("/api/usuario/")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService usuarioServico;

	@PostMapping(value = "criar/")
	//@PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
	public ResponseEntity<Response<User>> criar(HttpServletRequest request, @RequestBody User user, BindingResult result) {
		Response<User> response = usuarioServico.createOrUpdate(user);
		HttpStatus httpStatus = response.getStatus();
		return new ResponseEntity<Response<User>>(response, httpStatus);
	}

}
