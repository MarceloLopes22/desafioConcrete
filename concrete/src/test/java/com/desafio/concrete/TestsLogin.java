package com.desafio.concrete;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsLogin {
    
    @Autowired
    private UserService userService;
    
    @Before(value = "")
	public void setup() {
		mock(UserService.class);
		mock(Response.class);
	}
    
	@Test
	public void testLoginSucesso() {
		userService.create(createUser());
		User user = createUserLogin();
		Response<User> login = userService.login(user);
		HttpStatus status = login.getStatus();
		assertEquals(user.getEmail(), login.getDado().getEmail());
		assertEquals(user.getPassword(), login.getDado().getPassword());
		assertEquals(HttpStatus.OK.value(), status.value());
	}
	
	@Test
	public void testLoginEmailInvalido() {
		User loginDto = createUserLogin();
		loginDto.setEmail(null);
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String emailMessage = response.getErros().values().iterator().next();
		assertEquals("Necessário informar email e senha.", emailMessage);
		assertEquals(HttpStatus.BAD_REQUEST.value(), status.value());
	}
	
	@Test
	public void testLoginPasswordInvalido() {
		User loginDto = createUserLogin();
		loginDto.setPassword(null);
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String emailMessage = response.getErros().values().iterator().next();
		assertEquals("Necessário informar email e senha.", emailMessage);
		assertEquals(HttpStatus.BAD_REQUEST.value(), status.value());
	}
	
	// Caso o e-mail não exista, retornar erro com status apropriado mais a mensagem "Usuário e/ou senha inválidos"
	@Test
	public void testLoginEmailNotExist() {
		User loginDto = createUserLogin();
		loginDto.setEmail(loginDto.getEmail().concat("r"));
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String emailMessage = response.getErros().values().iterator().next();
		assertEquals("Usuário e/ou senha inválidos.", emailMessage);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), status.value());
	}
	// Caso o e-mail exista mas a senha não bata, retornar o status apropriado 401 mais a mensagem "Usuário e/ou senha inválidos"
	@Test
	public void testLoginPasswordNotExist() {
		User loginDto = createUserLogin();
		loginDto.setPassword(loginDto.getPassword().concat("r"));
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String passwordMessage = response.getErros().values().iterator().next();
		assertEquals("Usuário e/ou senha inválidos.", passwordMessage);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), status.value());
	}
	
	//Caso o token não exista, retornar erro com status apropriado com a mensagem "Não autorizado".
	@Test
	public void testLoginTokenNotExist() {
		User loginDto = createUserLogin();
		Response<User> findByEmail = userService.findByEmail(loginDto.getEmail());
		modificarToken1(findByEmail);
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String passwordMessage = response.getErros().values().iterator().next();
		assertEquals("Não autorizado.", passwordMessage);
		assertEquals(HttpStatus.UNAUTHORIZED.value(), status.value());
		userService.delete(findByEmail.getDado());
		userService.create(createUser());
	}

	private void modificarToken1(Response<User> findByEmail) {
		if (findByEmail != null) {
			User user = findByEmail.getDado();
			user.setToken(null);
			userService.update(user);
		}
	}
	
	private User createUserLogin() {
		User dto = new User("joao@silva.org","hunter2");
		return dto;
	}
	
	private User createUser() {
		User user = new User();
		user.setName("João da Silva");
		user.setEmail("joao@silva.org");
		user.setPassword("hunter2");
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setDdd("21");
		phone.setNumber("987654321");
		phones.add(phone);
		user.setPhones(phones);
		return user;
	}

}
