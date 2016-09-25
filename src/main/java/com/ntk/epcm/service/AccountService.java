package com.ntk.epcm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IAccountDAO;
import com.ntk.epcm.model.Account;

@Service
public class AccountService implements IAccountService{

	@Autowired
	IAccountDAO accountDAO;
	
	public AccountService(IAccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	public int insert(String username, String password) {
		return accountDAO.insert(username, password);
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
	
	
}