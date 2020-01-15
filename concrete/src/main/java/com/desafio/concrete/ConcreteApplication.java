package com.desafio.concrete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.desafio.concrete.service.UserService;

@SpringBootApplication(scanBasePackages = "com.desafio.concrete.*")
public class ConcreteApplication {

	@Autowired
	private UserService usuarioServico;
	
	public static void main(String[] args) {
		SpringApplication.run(ConcreteApplication.class, args);
	}

	/*@Bean
	CommandLineRunner inicializar() {
		return args -> {
			initUsuario();
		};
	}
	
	private void initUsuario() {
		Usuario mFilho = criarUsuarioAdmin();
		Usuario marcelo = criarUsuarioMarcelo();
		
		Usuario findMFilho = usuarioServico.findByEmail(mFilho.getEmail());
		if (findMFilho == null) {
			usuarioServico.createOrUpdate(mFilho);
		}
		
		Usuario findMarcelo = usuarioServico.findByEmail(marcelo.getEmail());
		if (findMarcelo == null) {
			usuarioServico.createOrUpdate(marcelo);
		}
	}

	private Usuario criarUsuarioMarcelo() {
		Usuario marcelo = new Usuario();
		marcelo.setNome("Marcelo Lopes");
		marcelo.setEmail("marcelomlopes2@gmail.com");
		marcelo.setSenha("123456");
		marcelo.setPerfil(Perfil.ROLE_USUARIO);
		return marcelo;
	}

	private Usuario criarUsuarioAdmin() {
		Usuario mFilho = new Usuario();
		mFilho.setNome("Marcelo Filho");
		mFilho.setEmail("mfilho@gmail.com");
		mFilho.setSenha("1234");
		mFilho.setPerfil(Perfil.ROLE_USUARIO);
		return mFilho;
	}*/
}
