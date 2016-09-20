package com.ntk.epcm.view.account;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.ntk.epcm.service.AccountService;
@ManagedBean
public class RegistrationView {
	
	@Autowired
	AccountService accountService;
	
	public String getTitle(){
		return "Registration";
	}
}
