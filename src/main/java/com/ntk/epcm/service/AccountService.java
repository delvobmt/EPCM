package com.ntk.epcm.service;

import com.ntk.epcm.data.IAccountDAO;

/**
 * @author TienKhoa
 *
 */
public class AccountService implements IAccountService{
	
	IAccountDAO accountDAO;
	
	public AccountService(IAccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	/* (non-Javadoc)
	 * @see com.ntk.service.model.IUserBO#insert(java.lang.String, java.lang.String)
	 */
	@Override
	public int insert(String username, String password) {
		return accountDAO.insert(username, password);
	}

	/* (non-Javadoc)
	 * @see com.ntk.service.model.IUserBO#save(int, java.lang.String, java.lang.String)
	 */
	@Override
	public int save(int id, String username, String password) {
		return accountDAO.save(id, username, password);
	}

	/* (non-Javadoc)
	 * @see com.ntk.service.model.IUserBO#remove(int)
	 */
	@Override
	public int remove(int id) {
		return accountDAO.remove(id);
	}
}