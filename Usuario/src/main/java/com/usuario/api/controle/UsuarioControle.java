package com.usuario.api.controle;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.api.controle.response.Response;
import com.usuario.api.entidades.Usuario;
import com.usuario.api.servico.UsuarioServico;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioControle {

	@Autowired
	private UsuarioServico usuarioServico;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * @GetMapping(value = "{email}/{senha}")
	 * 
	 * @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')") public
	 * ResponseEntity<Response<Usuario>> findByEmailAndSenha(@PathVariable("email")
	 * String email,
	 * 
	 * @PathVariable("senha") String senha) { Response<Usuario> response = new
	 * Response<Usuario>();
	 * 
	 * Usuario usuario = usuarioServico.findByEmailAndSenha(email, senha);
	 * 
	 * String token = passwordEncoder.encode(senha); usuario.setSenha(token);
	 * 
	 * if (usuario == null) { // response.getErros().add("Usuario não encontrado.");
	 * ResponseEntity.badRequest().body(response); } response.setDado(usuario);
	 * return ResponseEntity.ok().body(response); }
	 * 
	 * @GetMapping
	 * 
	 * @PreAuthorize("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')") public
	 * ResponseEntity<Response<Usuario>> findByEmail(@PathVariable("email") String
	 * email) { Response<Usuario> response = new Response<Usuario>();
	 * 
	 * Usuario usuario = usuarioServico.findByEmail(email); if (usuario == null) {
	 * // response.getErros().add("Usuario não encontrado.");
	 * ResponseEntity.badRequest().body(response); } response.setDado(usuario);
	 * return ResponseEntity.ok().body(response); }
	 */

	@PostMapping
	public ResponseEntity<Response<Usuario>> criar(HttpServletRequest request, @RequestBody Usuario usuario, BindingResult result) {
		Response<Usuario> response = usuarioServico.createOrUpdate(usuario);
		return new ResponseEntity<Response<Usuario>>(response, (HttpStatus) response.getStatus());
	}

}
