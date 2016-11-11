package com.ntk.epcm.service;

import java.util.List;

import com.ntk.epcm.model.AccountRole;

public interface IAccountRoleService {
	boolean insert(AccountRole accountRole);
	boolean save(AccountRole accountRole);
	boolean remove(AccountRole accountRole);
	
	List<AccountRole> findByAccount(int account_id);
	List<AccountRole> findByRole(String role);
	
	boolean needUpdate();
}
