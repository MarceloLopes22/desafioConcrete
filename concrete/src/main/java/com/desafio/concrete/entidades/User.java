package com.desafio.concrete.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.desafio.concrete.enums.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, precision = 1)
	private Integer id;
	
	@Column(name = "name", length = 100, nullable = true)
	private String name;
	
	@Column(name = "email", length = 100, nullable = true)
	private String email;
	
	@Column(name = "password", length = 100, nullable = true)
	private String password;
	
	@Column(name = "created", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate created;
	
	@Column(name = "modified", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate modified;
	
	@Column(name = "last_login", nullable = true)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate last_login;
	
	@Column(name = "token", length = 255, nullable = true)
	private String token;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Phone> phones;
	
	@Column(name = "profile_id", nullable = true)
	private Profile profile;
	
	

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public LocalDate getModified() {
		return modified;
	}

	public void setModified(LocalDate modified) {
		this.modified = modified;
	}

	public LocalDate getLast_login() {
		return last_login;
	}

	public void setLast_login(LocalDate last_login) {
		this.last_login = last_login;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
