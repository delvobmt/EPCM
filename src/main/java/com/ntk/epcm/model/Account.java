package com.ntk.epcm.model;

public class Account {
	private int id;
	private String username;
	private String password;

	/**
	 * The constructor
	 */
	
	public Account(int id) {
		this.id = id;
	}
	
	public Account() {
	}

	/**
	 * The constructor
	 * 
	 * @param id
	 * @param username
	 * @param password
	 */
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("[id=%s, username=%s]", id, username);
	}
}