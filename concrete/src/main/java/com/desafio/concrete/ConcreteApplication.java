package com.desafio.concrete;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.enums.Profile;
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
	
	@Bean
	CommandLineRunner inicializar() {
		return args -> {
			initUsuario();
		};
	}
	
	private void initUsuario() {
		User mFilho = criarUserAdmin();
		User marcelo = criarUserMarcelo();
		
		Response<User> findByEmail = userServico.findByEmail(mFilho.getEmail());
		User findMFilho = findByEmail.getDado();
		if (findMFilho == null) {
			userServico.create(mFilho);
		}
		
		Response<User> byEmail = userServico.findByEmail(marcelo.getEmail());
		User findMarcelo = byEmail.getDado();
		if (findMarcelo == null) {
			userServico.create(marcelo);
		}
	}

	private User criarUserMarcelo() {
		User marcelo = new User();
		marcelo.setName("Marcelo Lopes");
		marcelo.setEmail("marcelomlopes2@gmail.com");
		marcelo.setPassword("123456");
		marcelo.setProfile(Profile.ROLE_ADMIN);
		util.gerarUuid(marcelo);
		util.encriptyPassword(marcelo);
		List<Phone> createPhones = createPhones();
		marcelo.setPhones(createPhones);
		
		return marcelo;
	}

	private List<Phone> createPhones() {
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setDdd("81");
		phone.setNumber("995253601");
		phones.add(phone);
		return phones;
	}

	private User criarUserAdmin() {
		User mFilho = new User();
		mFilho.setName("Marcelo Filho");
		mFilho.setEmail("mfilho@gmail.com");
		mFilho.setPassword("1234");
		mFilho.setProfile(Profile.ROLE_USUARIO);
		util.gerarUuid(mFilho);
		util.encriptyPassword(mFilho);
		List<Phone> createPhones = createPhones();
		mFilho.setPhones(createPhones);
		return mFilho;
	}
}
