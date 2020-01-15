package com.desafio.concrete;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcreteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuppressWarnings("rawtypes")
public class ConcreteApplicationTests {
	
	@LocalServerPort
    private int port;
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    @Test
    public void testCriarUser() {
    	User user = createUser();
    	String url = "http://localhost:" + port + "/api/usuario/";
    	ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
    	assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
    }
    
	@Test
    public void testCriarUserNameErrorStatusCode() {
    	User user = createUser();
    	user.setName(null);
    	String url = "http://localhost:" + port + "/api/usuario/";
    	ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
    	assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }
	
	@Test
	public void testCriarUserNameErrorMessage() {
		User user = createUser();
		user.setName(null);
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		Map erros = responseEntity.getBody().getErros();
		String message = String.class.cast(erros.values().iterator().next());
		assertEquals("O nome do usuario deve ser preenchido.", message);
	}
	
	@Test
	public void testCriarUserEmailErrorStatusCode() {
		User user = createUser();
		user.setEmail(null);
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void testCriarUserEmailErrorMessage() {
		User user = createUser();
		user.setEmail(null);
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		Map erros = responseEntity.getBody().getErros();
		String message = String.class.cast(erros.values().iterator().next());
		assertEquals("O email do usuario deve ser preenchido.", message);
	}
	
	@Test
	public void testCriarUserPasswordErrorStatusCode() {
		User user = createUser();
		user.setPassword(null);
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void testCriarUserPasswordErrorMessage() {
		User user = createUser();
		user.setPassword(null);
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		Map erros = responseEntity.getBody().getErros();
		String message = String.class.cast(erros.values().iterator().next());
		assertEquals("A senha do usuario deve ser preenchida.", message);
	}
	
	/*@Test
	public void testCriarUserEmailExistError() {
		User user = createUser();
		String url = "http://localhost:" + port + "/api/usuario/";
		ResponseEntity<Response> responseEntity = restTemplate.postForEntity(url.concat("/criar/"), user, Response.class);
		assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
	}*/

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

	/*
	 * @Test public void contextLoads() { }
	 */

}
