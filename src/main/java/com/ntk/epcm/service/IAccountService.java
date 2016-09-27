package com.ntk.epcm.service;

import com.ntk.epcm.model.Account;

public interface IAccountService {

	/**
	 * create new user
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param email
	 * @return id of user
	 */
	int insert(String username, String password, String name, String email);

	/**
	 * save new information of user
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @return count of user saved
	 */
	int save(int id, String username, String password);

	/**
	 * remove user by id
	 * 
	 * @param id
	 * @return count of user removed
	 */
	int remove(int id);
	
	/**
	 * find Account with specified id
	 * @param id
	 * @return Account 
	 */
	Account findAccountById(int id);

	/**
	 * find account with specified email
	 * @param email
	 * @return Account
	 */
	Account findAccountByEmail(String email);

	/**
	 * check existence of username
	 * @param username
	 * @return true if username is existed
	 */
	Account findAccountByUsername(String username);
}