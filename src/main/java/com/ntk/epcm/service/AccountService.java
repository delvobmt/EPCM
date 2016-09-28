package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IAccountDAO;
import com.ntk.epcm.model.Account;

@Service
public class AccountService implements IAccountService{

	@Inject
	IAccountDAO accountDAO;

	@Override
	public int insert(String username, String password, String name, String email) {
		return accountDAO.insert(username, password, name, email);
	}

	@Override
	public int save(int id, String username, String password) {
		return accountDAO.save(id, username, password);
	}

	@Override
	public int remove(int id) {
		return accountDAO.remove(id);
	}

	@Override
	public Account findAccountById(int id) {
		return accountDAO.findAccountById(id);
	}

	@Override
	public Account findAccountByEmail(String email) {
		return accountDAO.findAccountByEmail(email);
	}

	@Override
	public Account findAccountByUsername(String username) {
		return accountDAO.findAccountByUsername(username);
	}

	@Override
	public boolean checkExistenceUsername(String username) {
		return accountDAO.checkExistenceUsername(username);
	}

	@Override
	public boolean checkExistenceEmail(String email) {
		return accountDAO.checkExistenceEmail(email);
	}

}