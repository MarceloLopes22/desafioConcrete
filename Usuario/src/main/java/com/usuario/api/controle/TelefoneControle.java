package com.usuario.api.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.api.servico.TelefoneServico;

@RestController
@RequestMapping("/api/telefone")
@CrossOrigin(origins = "*")
public class TelefoneControle {

	@Autowired
	private TelefoneServico telefoneServico;
	
	
	
	
}
