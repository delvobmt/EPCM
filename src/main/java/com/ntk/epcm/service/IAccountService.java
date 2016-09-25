package com.ntk.epcm.service;

import com.ntk.epcm.model.Account;

public interface IAccountService {

	/**
	 * create new user
	 * 
	 * @param username
	 * @param password
	 * @return id of user
	 */
	int insert(String username, String password);

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
}