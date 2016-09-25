package com.ntk.epcm.view.account;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.ntk.epcm.model.Account;
import com.ntk.epcm.service.AccountService;

@ManagedBean
@SessionScoped
public class RegistrationView {

	@Autowired
	AccountService accountService;
	
	@Size(max=50, min=2, message="username must be long between 2 and 50 charactors")
	private String username;
	private String name;
	private String email;
	private String password;

	public String register(){
		Account account = accountService.findAccountByEmail(email);
		if(account!=null){
			// account is existed
			return "";
		} else {
			accountService.insert(username, password);
			return "account/registration";
		}
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
