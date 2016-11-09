package com.ntk.epcm.data;

import java.sql.Date;
import java.util.Calendar;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.constant.AccountConstant;
import com.ntk.epcm.model.Account;
import com.ntk.epcm.model.AccountRole;

@Component
public class AccountDAO implements IAccountDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDAO.class);

	@Inject
	SessionFactory factory;

	@SuppressWarnings("finally")
	@Override
	public int insert(String username, String password, String name, String email) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		int id = -1;
		try {
			Account account = new Account(username, password);
			account.setEmail(email);
			account.setName(name);
			account.setStatus("active");
			id = (int) session.save(account);

			// add role user as default for this user
			AccountRole role = new AccountRole();
			role.setAccount_id(id);
			role.setRole("user");
			Calendar date = Calendar.getInstance();
			date.add(Calendar.MONTH, 3);
			role.setExpireAt(new Date(date.getTimeInMillis()));

			session.save(role);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while insert new account", e);
			session.getTransaction().rollback();
		} finally {
			session.close();
			return id;
		}
	}

	@Override
	public void save(int id, String username, String password, String name, String email) {
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			Account account = session.load(Account.class, id);
			if (!StringUtils.isEmpty(password)) {
				account.setPassword(password);
			}
			account.setEmail(email);
			account.setName(name);
			session.update(account);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			LOGGER.error("error while insert new account", e);
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Account findAccountById(int id) {
		Session session = factory.openSession();
		Account account = null;
		try {

			session.getTransaction().begin();
			account = session.load(Account.class, id);
			session.close();
		} catch (HibernateException e) {
			LOGGER.error("error while findAccountbyId {}", id, e);
		} finally {
			session.close();
			return account;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Account findAccountByEmail(String email) {
		Account account = null;
		Session session = factory.openSession();
		try {
		account = session.createQuery(String.format("from %s where %s=:email",
				AccountConstant.TABLE, AccountConstant.EMAIL_KEY)
				, Account.class).setParameter("email", email)
			.setMaxResults(1).getSingleResult();
		} catch (HibernateException e) {
			LOGGER.error("eror while find account by email", e);
		} finally {
			session.close();
			return account;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Account findAccountByUsername(String username) {
		Account account = null;
		Session session = factory.openSession();
		try {
			account =session.createQuery(String.format("from %s where %s=:username",
					AccountConstant.TABLE, AccountConstant.USERNAME_KEY)
					, Account.class).setParameter("username", username)
				.setMaxResults(1).getSingleResult();
		} catch (HibernateException e) {
			LOGGER.error("error while find account by username", e);
		} finally {
			session.close();
			return account;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean checkExistenceEmail(String email) {
		Session session = factory.openSession();
		long count = 0;
		try {
			count = session.createQuery(String.format("select count(*) from %s where %s=:email",
					AccountConstant.TABLE, AccountConstant.EMAIL_KEY)
					, Long.class).setParameter("email", email)
				.setMaxResults(1).getSingleResult();
		} catch (HibernateException e) {
			LOGGER.error("Error while check email existence", e);
		} finally {
			session.close();
			return count != 0;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean checkExistenceUsername(String username) {
		Session session = factory.openSession();
		
		long count = 0;
		try {
			count = session.createQuery(String.format("select count(*) from %s where %s=:username",
					AccountConstant.TABLE, AccountConstant.USERNAME_KEY)
					, Long.class).setParameter("username", username)
				.setMaxResults(1).getSingleResult();
		} catch (HibernateException e) {
			LOGGER.error("Error while check username existence", e);
		}finally {
			session.close();
			return count != 0;
		}
	}
}