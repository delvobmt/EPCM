package com.ntk.epcm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IAccountRoleDAO;
import com.ntk.epcm.model.AccountRole;

@Service
public class AccountRoleService implements IAccountRoleService {

	@Inject
	IAccountRoleDAO dao;
	
	boolean needUpdate = false;
	
	@Override
	public boolean insert(AccountRole accountRole) {
		boolean success = dao.insert(accountRole);
		needUpdate = needUpdate?needUpdate:success ;
		return success;
	}

	@Override
	public boolean save(AccountRole accountRole) {
		boolean success = dao.save(accountRole);
		needUpdate = needUpdate?needUpdate:success ;
		return success;
	}

	@Override
	public boolean remove(AccountRole accountRole) {
		boolean success = dao.remove(accountRole);
		needUpdate = needUpdate?needUpdate:success ;
		return success;
	}

	@Override
	public List<AccountRole> findByAccount(int account_id) {
		return dao.findByAccount(account_id);
	}

	@Override
	public List<AccountRole> findByRole(String role) {
		return dao.findByRole(role);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

}
