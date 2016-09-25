package com.ntk.epcm.view.account;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Service;

import com.ntk.epcm.model.Account;
import com.ntk.epcm.service.AccountService;

@ManagedBean
@SessionScoped
@Service
public class RegistrationView implements Serializable {

	private static final long serialVersionUID = -5746869699117671009L;

	@Inject
	AccountService accountService;
	
	@Size(max=50, min=4, message="Username must be long between 4 and 50 charactors")
	private String username;
	
	@Min(10)
	@Max(20)
	private String name;
	
	@Pattern(regexp="\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\\b")
	private String email;
	
	@Size(min=6, message="Username must be longer than 6 charactors")
	private String password;

	public String register(){
		Account account = accountService.findAccountByEmail(email);
		if(account!=null){
			// account is existed
			return "";
		} else {
			accountService.insert(username, password);
			return "account/detail";
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
