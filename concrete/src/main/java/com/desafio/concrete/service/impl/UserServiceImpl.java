package com.desafio.concrete.service.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.desafio.concrete.controller.response.Response;
import com.desafio.concrete.entidades.LoginDto;
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.repository.UserRepository;
import com.desafio.concrete.service.UserService;

@Service
public class UserServiceImpl  implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmailAndPassword(String email, String password) {
		return this.userRepository.findUserByEmailAndPassword(email, password);
	}

	@Override
	public Response<User> createOrUpdate(User user) {
		Response<User> response = new Response<>();
		response = validar(user, response);
		if (response.getErros().isEmpty()) {
			preencherCamposLGPD(user);
			gerarUuid(user);
			setUserToPhones(user);
			User usuarioSalvo = this.userRepository.save(user);
			response.setDado(usuarioSalvo);
			response.setStatus(HttpStatus.CREATED);
		}
		return response;
	}

	private void setUserToPhones(User usuarioSalvo) {
		List<Phone> phones = usuarioSalvo.getPhones();
		if (phones != null && !phones.isEmpty()) {
			phones.forEach(phone -> phone.setUser(usuarioSalvo));
		}
	}

	private void preencherCamposLGPD(User user) {
		user.setCreated(LocalDate.now());
		if (user.getId() != null && user.getId() > 0) {
			user.setModified(LocalDate.now());
		}
		user.setLast_login(LocalDate.now());
	}

	private Response<User> validar(User user, Response<User> response) {
		
		if (user != null) {
			if (StringUtils.isEmpty(user.getName())) {
				response.getErros().put("nomeErro", "O nome do usuario deve ser preenchido.");
				response.setStatus(HttpStatus.BAD_REQUEST);
			}
			if (StringUtils.isEmpty(user.getEmail())) {
				response.getErros().put("emailErro", "O email do usuario deve ser preenchido.");
				response.setStatus(HttpStatus.BAD_REQUEST);
			} else {
				User userFind = userRepository.findUserByEmail(user.getEmail());
				if (userFind != null) {
					response.getErros().put("emailErro", "E-mail já existente.");
					response.setStatus(HttpStatus.BAD_REQUEST);
				}
			}
			if (StringUtils.isEmpty(user.getPassword())) {
				response.getErros().put("senhaErro", "A senha do usuario deve ser preenchida.");
				response.setStatus(HttpStatus.BAD_REQUEST);
			}
		}
		
		return response;
	}

	private void gerarUuid(User user) {
	    String a = UUID.randomUUID().toString().substring(0,16);
	    SecretKeySpec key = new SecretKeySpec(a.getBytes(), "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);	 	 
			String email = user.getEmail();
			String password = user.getPassword();
			byte[] messageEncriptada = cipher.doFinal(email.concat(password).getBytes());	 	 
			String token = Base64.getEncoder().encodeToString(messageEncriptada);
			user.setToken(token);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response<User> login(LoginDto loginDto) {
		Response<User> response = new Response<>();
		
		if (StringUtils.isEmpty(loginDto.getEmail()) || StringUtils.isEmpty(loginDto.getPassword())) {
			response.getErros().put("camposErrors", "Necessário informar email e senha.");
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		
		User userReturned = this.userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		response = validarDadosLogin(loginDto, userReturned, response);
		return response;
	}

	private Response<User> validarDadosLogin(LoginDto loginDto, User userConsulted, Response<User> response) {
		String email = loginDto.getEmail();
		String password = loginDto.getPassword();
		
		if (!email.isEmpty() &&  !password.isEmpty() && userConsulted != null) {
			// Caso o e-mail e a senha correspondam a um usuário existente, retornar igual ao endpoint de Criação.
			if (email.equalsIgnoreCase(userConsulted.getEmail()) && password.equalsIgnoreCase(userConsulted.getPassword())) {
				response.setDado(userConsulted);
				response.setStatus(HttpStatus.OK);
				return response;
			}
		} else {
			if (userConsulted == null) {
				// Caso o e-mail não exista, retornar erro com status apropriado mais a mensagem "Usuário e/ou senha inválidos"
				User userReturned = this.userRepository.findUserByEmail(email);
				String message = "Usuário e/ou senha inválidos.";
				if (userReturned == null) {
					response.getErros().put("emailErro", message);
					response.setStatus(HttpStatus.UNAUTHORIZED);
				} else {
					// Caso o e-mail exista mas a senha não bata, retornar o status apropriado 401 mais a mensagem "Usuário e/ou senha inválidos"
					if (!password.equalsIgnoreCase(userReturned.getPassword())) {
						response.getErros().put("passwordErro", message);
						response.setStatus(HttpStatus.UNAUTHORIZED);
					}
				}
			}
		}
		return response;
	}

	@Override
	public Response<User> findByEmail(String email) {
		Response<User> response = new Response<User>();
		 User findByEmail = userRepository.findUserByEmail(email);
		 response.setDado(findByEmail);
		 response.setStatus(HttpStatus.OK);
		 return response;
	}
}
