package com.ntk.epcm.manage.bean;

import java.io.Serializable;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ntk.epcm.service.IAccountService;

@Scope("request")
@Component
public class AccountBean implements Serializable {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 5746869699117671009L;

	@Inject
	IAccountService accountService;

	@Inject
	BCryptPasswordEncoder encoder;

	private String username;

	private String name;

	private String email;

	private String password;

	public String register() {
		LOGGER.debug("insert account with {}", username);
		int insert = accountService.insert(username, encoder.encode(password), name, email);
		return insert!=-1?"success":"fail";
	}

	public String name() {
		return null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
}
