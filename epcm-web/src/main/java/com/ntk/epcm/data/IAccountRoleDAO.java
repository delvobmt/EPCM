package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.model.AccountRole;

public interface IAccountRoleDAO {
	boolean insert(AccountRole accountRole);
	boolean save(AccountRole accountRole);
	boolean remove(AccountRole accountRole);
	
	List<AccountRole> findByAccount(int account_id);
	List<AccountRole> findByRole(String role);
}
