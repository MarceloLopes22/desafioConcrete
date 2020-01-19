package com.desafio.concrete.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
import com.desafio.concrete.utils.Util;

@Service
public class UserServiceImpl  implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Util util;
	
	@Override
	public User findByEmailAndPassword(String email, String password) {
		return this.userRepository.findUserByEmailAndPassword(email, password);
	}

	@Override
	public Response<User> create(User user) {
		Response<User> response = new Response<>();
		response = validar(user, response);
		if (response.getErros().isEmpty()) {
			preencherCamposLGPD(user);
			util.gerarUuid(user);
			util.encriptyPassword(user);
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
		user.setModified(LocalDate.now());
		user.setLast_login(LocalDateTime.now());
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

	@Override
	public Response<User> login(LoginDto loginDto) {
		Response<User> response = new Response<>();
		
		if (StringUtils.isEmpty(loginDto.getEmail()) || StringUtils.isEmpty(loginDto.getPassword())) {
			response.getErros().put("camposErrors", "Necessário informar email e senha.");
			response.setStatus(HttpStatus.BAD_REQUEST);
			return response;
		}
		util.encriptyPassword(loginDto);
		User userReturned = this.userRepository.findUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		response = validarDadosLogin(loginDto, userReturned, response);
		validarDadosPerfilUsuario(loginDto, userReturned, response);
		return response;
	}
	
	private Response<User> validarDadosLogin(LoginDto loginDto, User userConsulted, Response<User> response) {
		String email = loginDto.getEmail();
		String password = loginDto.getPassword();
		
		if (!email.isEmpty() && !password.isEmpty() && userConsulted != null) {
			// Caso o e-mail e a senha correspondam a um usuário existente, retornar igual ao endpoint de Criação.
			if (email.equalsIgnoreCase(userConsulted.getEmail()) && password.equalsIgnoreCase(userConsulted.getPassword())) {
				
				//Seta a data de modificação para identificar o tempo do ultimo login efetuado.
				userConsulted.setLast_login(LocalDateTime.now());
				this.userRepository.save(userConsulted);
				
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

	private void validarDadosPerfilUsuario(LoginDto loginDto, User userReturned, Response<User> response) {
		if (response.getErros().isEmpty()) {
			User user = response.getDado();
			String token = user.getToken();
			
			//Caso o token não exista, retornar erro com status apropriado com a mensagem "Não autorizado".
			if (StringUtils.isEmpty(token)) {
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.getErros().put("loginErro", "Não autorizado.");
			}
			
			//Caso não seja o mesmo token, retornar erro com status apropriado e mensagem "Não autorizado"
			User userByToken = findUserByToken(token);
			if (userByToken != null && !StringUtils.isEmpty(token) && !userByToken.getToken().equalsIgnoreCase(token)) {
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.getErros().put("loginErro", "Não autorizado.");
			}
			
			//Caso o token exista, buscar o usuário pelo id passado no path e comparar se o token no modelo é igual ao token passado no header.
			if (userByToken != null && !StringUtils.isEmpty(token) && !StringUtils.isEmpty(userByToken.getToken()) && userByToken.getToken().equalsIgnoreCase(token)) {
				//Caso seja o mesmo token, verificar se o último login foi a MENOS que 30 minutos atrás. Caso não seja a MENOS que 30 minutos atrás, 
				//retornar erro com status apropriado com mensagem "Sessão inválida".
				long difference = ChronoUnit.MINUTES.between(userByToken.getLast_login(), LocalDateTime.now());
				if(difference > 30 * 60 * 1000) {
					response.setStatus(HttpStatus.FORBIDDEN);
					response.getErros().put("loginErro", "Sessão inválida.");
				}
			}
		}
	}

	@Override
	public Response<User> findByEmail(String email) {
		Response<User> response = new Response<User>();
		 User findByEmail = userRepository.findUserByEmail(email);
		 response.setDado(findByEmail);
		 response.setStatus(HttpStatus.OK);
		 return response;
	}

	@Override
	public User findUserByToken(String token) {
		return userRepository.findUserByToken(token);
	}
	
	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public void update(User user) {
		userRepository.saveAndFlush(user);
	}
	
	@Override
	public Response<List<User>> users() {
		Response<List<User>> response = new Response<List<User>>();
		List<User> list = userRepository.findAll();
		response.setDado(list);
		response.setStatus(HttpStatus.OK);
		return response;
	}
}
