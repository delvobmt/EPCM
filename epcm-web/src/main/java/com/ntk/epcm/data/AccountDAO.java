package com.ntk.epcm.data;

import java.sql.Date;
import java.util.Calendar;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

	@SuppressWarnings({ "deprecation", "finally" })
	@Override
	public Account findAccountByEmail(String email) {
		Session session = factory.openSession();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("email", email)).setMaxResults(1);
		Object account = null;
		try {
			account = criteria.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("eror while find account by email", e);
		} finally {
			session.close();
			return account == null ? null : (Account) account;
		}
	}

	@SuppressWarnings({ "deprecation", "finally" })
	@Override
	public Account findAccountByUsername(String username) {
		Session session = factory.openSession();
		Account account = null;
		try {
			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(Account.class);
			criteria.add(Restrictions.eq("username", username)).setMaxResults(1);
			account = (Account) criteria.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("error while find account by username", e);
		} finally {
			session.close();
			return account;
		}
	}

	@SuppressWarnings({ "deprecation", "finally" })
	@Override
	public boolean checkExistenceEmail(String email) {
		Session session = factory.openSession();
		long count = 0;
		try {
			Criteria criteria = session.createCriteria(Account.class);
			criteria.add(Restrictions.eq("email", email)).setProjection(Projections.rowCount());
			count = (Long) criteria.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("Error while check email existence", e);
		} finally {
			session.close();
			return count != 0;
		}
	}

	@SuppressWarnings({ "deprecation", "finally" })
	@Override
	public boolean checkExistenceUsername(String username) {
		Session session = factory.openSession();
		
		long count = 0;
		try {
			Criteria criteria = session.createCriteria(Account.class);
			criteria.add(Restrictions.eq("username", username)).setProjection(Projections.rowCount());
			count  = (Long) criteria.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("Error while check username existence", e);
		}finally {
			session.close();
			return count != 0;
		}
	}
}