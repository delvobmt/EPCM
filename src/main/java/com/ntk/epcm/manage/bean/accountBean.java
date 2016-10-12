package com.ntk.epcm.manage.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.RespondCode;
import com.ntk.epcm.service.IAccountService;

@ManagedBean
@Scope("request")
@Component
public class accountBean implements Serializable {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = -5746869699117671009L;

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
		accountService.insert(username, encoder.encode(password), name, email);
		return "user/register_success.xhtml";
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
