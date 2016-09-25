package com.ntk.epcm.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ntk.epcm.model.Account;

public class AccountDAO implements IAccountDAO {
	@Autowired
	SessionFactory factory;
	
	public AccountDAO(SessionFactory factory) {
		super();
		this.factory = factory;
	}

	@Override
	public int insert(String username, String password) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		int id = -1;
		try {
			Account account = new Account(username, password);
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
		//TODO edit account infomation
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
		List<Account> list = session.createCriteria(Account.class)
				.add(Restrictions.eq("email", email)).setMaxResults(1).list();
		if(!list.isEmpty()){
			return list.get(0);
		}
		session.close();
		return null;
	}

	
}