package com.desafio.concrete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desafio.concrete.service.UserService;
import com.desafio.concrete.utils.Util;

@SpringBootApplication(scanBasePackages = "com.desafio.concrete.*")
public class ConcreteApplication {

	@Autowired
	private UserService userServico;
	
	@Autowired
	private Util util;
	
	public static void main(String[] args) {
		SpringApplication.run(ConcreteApplication.class, args);
	}
}
