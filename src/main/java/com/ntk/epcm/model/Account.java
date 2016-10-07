package com.ntk.epcm.model;

import java.util.Collections;
import java.util.Set;

public class Account {
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String status;
	private Set<AccountRole> roles = Collections.emptySet();
	
	public Account(int id) {
		this.id = id;
	}
	
	public Account() {
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("[id=%s, username=%s]", id, username);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<AccountRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AccountRole> roles) {
		this.roles = roles;
	}
}