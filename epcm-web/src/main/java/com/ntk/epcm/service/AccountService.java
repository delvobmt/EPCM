package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IAccountDAO;
import com.ntk.epcm.model.Account;

@Service
public class AccountService{

	@Inject
	IAccountDAO accountDAO;
	
	public int insert(String username, String password, String name, String email) {
		return accountDAO.insert(username, password, name, email);
	}

	public void save(int id, String username, String password,String name, String email) {
		accountDAO.save(id, username, password, name, email);
	}

	public Account findAccountById(int id) {
		return accountDAO.findAccountById(id);
	}

	public Account findAccountByEmail(String email) {
		return accountDAO.findAccountByEmail(email);
	}

	public Account findAccountByUsername(String username) {
		return accountDAO.findAccountByUsername(username);
	}

	public boolean checkExistenceUsername(String username) {
		return accountDAO.checkExistenceUsername(username);
	}

	public boolean checkExistenceEmail(String email) {
		return accountDAO.checkExistenceEmail(email);
	}
}