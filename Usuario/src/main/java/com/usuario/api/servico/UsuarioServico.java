package com.usuario.api.servico;

import com.usuario.api.controle.response.Response;
import com.usuario.api.entidades.Usuario;

public interface UsuarioServico {

	Usuario findByEmailAndSenha(String email, String senha);
	
	Usuario findByEmail(String email);

	Response<Usuario> createOrUpdate(Usuario usuario);
}
