package com.usuario.api.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.usuario.api.enums.Perfil;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "nome", length = 100, nullable = true)
	private String nome;
	
	@Column(name = "email", length = 100, nullable = true)
	private String email;
	
	@Column(name = "senha", length = 100, nullable = true)
	private String senha;
	
	@Column(name = "data_criacao", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data_criacao;
	
	@Column(name = "data_alteracao", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data_alteracao;
	
	@Column(name = "data_ultimo_login", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate data_ultimo_login;
	
	@Column(name = "token", length = 255, nullable = true)
	private String token;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id")
	private List<Telefone> telefones;
	
	@Column(name = "perfil_id", nullable = true)
	private Perfil perfil;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}

	public LocalDate getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(LocalDate data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public LocalDate getData_ultimo_login() {
		return data_ultimo_login;
	}

	public void setData_ultimo_login(LocalDate data_ultimo_login) {
		this.data_ultimo_login = data_ultimo_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}
