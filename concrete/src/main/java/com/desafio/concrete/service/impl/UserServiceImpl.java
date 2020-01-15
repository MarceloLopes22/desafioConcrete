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
import com.desafio.concrete.entidades.Phone;
import com.desafio.concrete.entidades.User;
import com.desafio.concrete.repository.UserRepository;
import com.desafio.concrete.service.PhoneService;
import com.desafio.concrete.service.UserService;

@Service
public class UserServiceImpl  implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneService phoneService;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return this.userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public Response<User> createOrUpdate(User user) {
		Response<User> response = new Response<>();
		response = validar(user, response);
		if (response.getErros().isEmpty()) {
			preencherCamposLGPD(user);
			//usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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
		phones.forEach(phone -> phone.setUser(usuarioSalvo));
		//List<Phone> list = phoneService.createOrUpdate(phones);
		//usuarioSalvo.setPhones(list);
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
				User userFind = userRepository.findByEmail(user.getEmail());
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
}
