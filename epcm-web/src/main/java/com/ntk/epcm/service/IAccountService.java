package com.ntk.epcm.service;

import com.ntk.epcm.model.Account;

public interface IAccountService {

	int insert(String username, String password, String name, String email);

	void save(int id, String username, String password, String name, String email);

	Account findAccountById(int id);

	Account findAccountByEmail(String email);

	Account findAccountByUsername(String username);
	
	boolean checkExistenceUsername(String username);
	
	boolean checkExistenceEmail(String email);
}