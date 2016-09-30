package com.ntk.epcm.data;

import com.ntk.epcm.constant.RespondCode;
import com.ntk.epcm.model.Account;

public interface IAccountDAO {
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
	 * 
	 * @param id
	 * @return Account
	 */
	Account findAccountById(int id);

	/**
	 * find account with specified email
	 * 
	 * @param email
	 * @return Account
	 */
	Account findAccountByEmail(String email);

	/**
	 * check existence of username
	 * 
	 * @param username
	 * @return true if username is existed
	 */
	Account findAccountByUsername(String username);

	/**
	 * check existence of email
	 * 
	 * @param email
	 * @return true when email is used
	 */
	boolean checkExistenceEmail(String email);

	/**
	 * check existence of Username
	 * @param username
	 * @return true when username is existed
	 */
	boolean checkExistenceUsername(String username);

	RespondCode doLogin(String username, String password);
}