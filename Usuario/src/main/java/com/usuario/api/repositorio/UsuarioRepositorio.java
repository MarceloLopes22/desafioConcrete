package com.usuario.api.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.api.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Usuario findByEmailAndSenha(String email, String senha);
	
	Usuario findByEmail(String email);
}
