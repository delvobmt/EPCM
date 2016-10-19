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

import com.ntk.epcm.constant.RespondCode;
import com.ntk.epcm.model.Account;
import com.ntk.epcm.model.AccountRole;

@Component
public class AccountDAO implements IAccountDAO {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Inject
	SessionFactory factory;

	public AccountDAO(SessionFactory factory) {
		super();
		this.factory = factory;
	}

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
			
			//add role user as default for this user
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
		}
		session.close();
		return id;
	}

	@Override
	public void save(int id, String username, String password, String name, String email) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
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
		}
		session.close();
	}

	@Override
	public void remove(int id) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			Account account = session.load(Account.class, id);
			session.remove(account);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while remove account", e);
			session.getTransaction().rollback();
		}
		session.close();
	}

	@Override
	public Account findAccountById(int id) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Account account = session.load(Account.class, id);
		session.close();
		return account;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Account findAccountByEmail(String email) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("email", email)).setMaxResults(1);
		Object account = null;
		try {
			account = criteria.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("eror while find account by email",e);
		}
		session.close();
		return account == null ? null : (Account) account;
	}

	@Override
	public Account findAccountByUsername(String username) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("username", username)).setMaxResults(1);
		Object account = null;
		try {
			account = criteria.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while find account by username",e);
		}
		session.close();
		return account == null ? null : (Account) account;
	}

	@Override
	public boolean checkExistenceEmail(String email) {
		try {
			Session session = factory.openSession();
			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(Account.class);
			criteria.add(Restrictions.eq("email", email)).setProjection(Projections.rowCount());
			Long count = (Long) criteria.uniqueResult();
			session.getTransaction().commit();
			session.close();
			return count != 0;
		} catch (HibernateException e) {
			LOGGER.error("Error while check email existence", e);
			return true;
		}
	}

	@Override
	public boolean checkExistenceUsername(String username) {
		try {
			Session session = factory.openSession();
			session.getTransaction().begin();
			Criteria criteria = session.createCriteria(Account.class);
			criteria.add(Restrictions.eq("username", username)).setProjection(Projections.rowCount());
			Long count = (Long) criteria.uniqueResult();
			session.getTransaction().commit();
			session.close();
			return count != 0;
		} catch (HibernateException e) {
			LOGGER.error("Error while check username existence", e);
			return true;
		}
	}

	@Override
	public RespondCode doLogin(String username, String password) {
		RespondCode code = RespondCode.ERROR;
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.and(
				Restrictions.or(Restrictions.eq("username", username), Restrictions.eq("email", username)),
				Restrictions.eq("password", password))).setProjection(
						Projections.projectionList().add(Projections.rowCount()).add(Projections.property("status")));

		try {		
			Object[] result = (Object[]) criteria.uniqueResult();
			LOGGER.debug("{}",result);
			code = (long)result[0] == 0L ? RespondCode.FAIL
					: result[1]==null||result[1].equals("inactive") ? RespondCode.INACTIVE : RespondCode.SUCCES;
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while login", e);
			code = RespondCode.ERROR;
		}
		session.close();

		return code;
	}

}