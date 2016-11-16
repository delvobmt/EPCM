package com.ntk.epcm.model;

import java.util.Date;
import java.util.Collections;
import java.util.Set;

public class Account {
	private int id;
	private String username;
	private String password;
	private String email;
	private String name;
	private String status;
	private Date createAt;
	private Date lastActiveAt;
	private Set<AccountRole> roles = Collections.emptySet();
	
	public Account(int id) {
		this.id = id;
	}
	
	public Account() {
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<AccountRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AccountRole> roles) {
		this.roles = roles;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getLastActiveAt() {
		return lastActiveAt;
	}

	public void setLastActiveAt(Date lastActiveAt) {
		this.lastActiveAt = lastActiveAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", name=" + name + ", status=" + status + ", createAt=" + createAt + ", lastActiveAt=" + lastActiveAt
				+ ", roles=" + roles + "]";
	}
	
}