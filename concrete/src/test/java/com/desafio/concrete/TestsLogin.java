package com.desafio.concrete;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.LoginDto;
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.service.UserService;
import com.desafio.concrete.utils.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsLogin {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private Util util;
    
	@Before(value = "")
	public void setup() {
		mock(UserService.class);
		mock(Response.class);
	}
    
	@Test
	public void testLoginSucesso() {
		LoginDto loginDto = createLoginDto();
		Response<User> login = userService.login(loginDto);
		HttpStatus status = login.getStatus();
		assertEquals(loginDto.getEmail(), login.getDado().getEmail());
		assertEquals(loginDto.getPassword(), login.getDado().getPassword());
		assertEquals(HttpStatus.OK.value(), status.value());
	}
	
	@Test
	public void testLoginEmailInvalido() {
		LoginDto loginDto = createLoginDto();
		loginDto.setEmail(null);
		Response<User> response = userService.login(loginDto);
		HttpStatus status = response.getStatus();
		String emailMessage = response.getErros().values().iterator().next();
		assertEquals("Necessário informar email e senha.", emailMessage);
		assertEquals(HttpStatus.BAD_REQUEST.value(), status.value());
	}
	
	@Test
	public void testLoginPasswordInvalido() {
		LoginDto loginDto = createLoginDto();
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
		LoginDto loginDto = createLoginDto();
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
		LoginDto loginDto = createLoginDto();
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
		LoginDto loginDto = createLoginDto();
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
	
	private LoginDto createLoginDto() {
		LoginDto dto = new LoginDto("joao@silva.org","hunter2");
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
