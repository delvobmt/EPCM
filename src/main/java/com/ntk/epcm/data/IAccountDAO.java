package com.ntk.epcm.data;

import com.ntk.epcm.constant.RespondCode;
import com.ntk.epcm.model.Account;

public interface IAccountDAO {
	int insert(String username, String password, String name, String email);

	void save(int id, String username, String password, String name, String email);

	void remove(int id);

	Account findAccountById(int id);

	Account findAccountByEmail(String email);

	Account findAccountByUsername(String username);

	boolean checkExistenceEmail(String email);

	boolean checkExistenceUsername(String username);

	RespondCode doLogin(String username, String password);
}