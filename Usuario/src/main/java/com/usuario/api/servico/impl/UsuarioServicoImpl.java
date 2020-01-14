package com.usuario.api.servico.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.usuario.api.controle.response.Response;
import com.usuario.api.entidades.Usuario;
import com.usuario.api.repositorio.UsuarioRepositorio;
import com.usuario.api.servico.UsuarioServico;

@Service
public class UsuarioServicoImpl  implements UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	public Usuario findByEmailAndSenha(String email, String senha) {
		return this.repositorio.findByEmailAndSenha(email, senha);
	}

	@Override
	public Usuario findByEmail(String email) {
		return this.repositorio.findByEmail(email);
	}

	@Override
	public Response<Usuario> createOrUpdate(Usuario usuario) {
		Response<Usuario> response = new Response<>();
		response = validar(usuario, response);
		if (response.getErros().isEmpty()) {
			preencherCamposLGPD(usuario);
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			gerarUuid(usuario);
			Usuario usuarioSalvo = this.repositorio.saveAndFlush(usuario);
			response.setDado(usuarioSalvo);
			response.setStatus(ResponseEntity.status(HttpStatus.CREATED));
		}
		return response;
	}

	private void preencherCamposLGPD(Usuario usuario) {
		usuario.setData_criacao(LocalDate.now());
		if (usuario.getId() != null && usuario.getId() > 0) {
			usuario.setData_alteracao(LocalDate.now());
		}
		usuario.setData_ultimo_login(LocalDate.now());
	}

	private Response<Usuario> validar(Usuario usuario, Response<Usuario> response) {
		
		if (usuario != null) {
			if (StringUtils.isEmpty(usuario.getNome())) {
				response.getErros().put("nomeErro", "O nome do usuario deve ser preenchido.");
				response.setStatus(ResponseEntity.status(HttpStatus.BAD_REQUEST));
			}
			if (StringUtils.isEmpty(usuario.getEmail())) {
				response.getErros().put("emailErro", "O email do usuario deve ser preenchido.");
				response.setStatus(ResponseEntity.status(HttpStatus.BAD_REQUEST));
			} else {
				Usuario usuarioEncontrado = repositorio.findByEmail(usuario.getEmail());
				if (usuarioEncontrado != null) {
					response.getErros().put("emailErro", "E-mail já existente.");
					response.setStatus(ResponseEntity.status(HttpStatus.BAD_REQUEST));
				}
			}
			if (StringUtils.isEmpty(usuario.getSenha())) {
				response.getErros().put("senhaErro", "A senha do usuario deve ser preenchida.");
				response.setStatus(ResponseEntity.status(HttpStatus.BAD_REQUEST));
			}
		}
		
		return response;
	}

	private void gerarUuid(Usuario usuario) {
	    String a = UUID.randomUUID().toString().substring(0,16);
	    SecretKeySpec key = new SecretKeySpec(a.getBytes(), "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);	 	 
			String email = usuario.getEmail();
			String senha = usuario.getSenha();
			byte[] mensagemEncriptada = cipher.doFinal(email.concat(senha).getBytes());	 	 
			String token = Base64.getEncoder().encodeToString(mensagemEncriptada);
			usuario.setToken(token);
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
