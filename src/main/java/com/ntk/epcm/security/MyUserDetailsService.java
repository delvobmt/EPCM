package com.ntk.epcm.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import com.ntk.epcm.data.IAccountDAO;
import com.ntk.epcm.model.Account;
import com.ntk.epcm.model.AccountRole;

public class MyUserDetailsService implements UserDetailsService {
	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Inject
	IAccountDAO accountDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)) {
			LOGGER.error("{} is empty while load account", username);
			throw new UsernameNotFoundException(null);
		}
		Account account = accountDAO.findAccountByUsername(username);
		if (account == null) {
			account = accountDAO.findAccountByEmail(username);
		}
		if (account != null) {
			List<GrantedAuthority> authorities = buildAuthorities(account.getRoles());
			return buildUserForAuthentication(account, authorities);
		} else {
			LOGGER.error("{} is not found while build user for authentication", username);
			throw new UsernameNotFoundException(username);
		}

	}

	private UserDetails buildUserForAuthentication(Account account, List<GrantedAuthority> authorities) {
		return new User(account.getUsername(), account.getPassword(), account.getStatus().equals("active"), true, true,
				true, authorities);
	}

	private List<GrantedAuthority> buildAuthorities(Set<AccountRole> roles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (AccountRole userRole : roles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		return new ArrayList<GrantedAuthority>(setAuths);
	}

}
