package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IAccountRoleDAO;
import com.ntk.epcm.model.AccountRole;

@Service
public class AccountRoleService extends Observable {

	@Inject
	IAccountRoleDAO dao;
	
	boolean needUpdate = false;
	
	public boolean insert(AccountRole accountRole) {
		boolean success = dao.insert(accountRole);
		if(success){
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean save(AccountRole accountRole) {
		boolean success = dao.save(accountRole);
		if(success){
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(AccountRole accountRole) {
		boolean success = dao.remove(accountRole);
		if(success){
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public List<AccountRole> findByAccount(int account_id) {
		return dao.findByAccount(account_id);
	}

	public List<AccountRole> findByRole(String role) {
		return dao.findByRole(role);
	}

	public boolean needUpdate() {
		return needUpdate;
	}

}
