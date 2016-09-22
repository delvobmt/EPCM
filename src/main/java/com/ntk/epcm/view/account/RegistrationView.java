package com.ntk.epcm.view.account;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.ntk.epcm.service.AccountService;
import javax.validation.constraints.Size;

@ManagedBean
public class RegistrationView {

	@Autowired
	AccountService accountService;
	
	@Size
	private String username;
	private String name;
	private String email;

}
