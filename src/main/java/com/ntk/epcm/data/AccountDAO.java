package com.ntk.epcm.data;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ntk.epcm.constant.RespondCode;
import com.ntk.epcm.model.Account;
import com.ntk.epcm.model.vo.AccountLoginVO;

public class AccountDAO implements IAccountDAO {
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
			id = (int) session.save(account);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public int save(int id, String username, String password) {
		// TODO edit account infomation
		return 0;
	}

	@Override
	public int remove(int id) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		session.remove(new Account(id));
		session.getTransaction().commit();
		session.close();
		return 0;
	}

	@Override
	public Account findAccountById(int id) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Account account = session.get(Account.class, id);
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
		Object account = criteria.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return account == null ? null : (Account) account;
	}

	@Override
	public Account findAccountByUsername(String username) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("username", username)).setMaxResults(1);
		Object account = criteria.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return account == null ? null : (Account) account;
	}

	@Override
	public boolean checkExistenceEmail(String email) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("email", email)).setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return count != 0;
	}

	@Override
	public boolean checkExistenceUsername(String username) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.eq("username", username)).setProjection(Projections.rowCount());
		Long count = (Long) criteria.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return count != 0;
	}

	@Override
	public RespondCode doLogin(String username, String password) {
		RespondCode code = RespondCode.ERROR;
		Session session = factory.openSession();
		session.getTransaction().begin();
		Criteria criteria = session.createCriteria(Account.class);
		criteria.add(Restrictions.and(
				Restrictions.or(Restrictions.eq("username", username),
						Restrictions.eq("email", username)),
				Restrictions.eq("password", password)))
				.setProjection(Projections.projectionList()
						.add(Projections.rowCount())
						.add(Projections.property("status")));
		
		try {
			AccountLoginVO result = (AccountLoginVO) criteria.uniqueResult();
			code = result.getCount()==0?RespondCode.FAIL:
				result.getStatus().equals("inactive")?RespondCode.INACTIVE:
					RespondCode.SUCCES;
		} catch (HibernateException e) {
			code = RespondCode.ERROR;
		}
		session.getTransaction().commit();
		session.close();
		
		return code;
	}

}