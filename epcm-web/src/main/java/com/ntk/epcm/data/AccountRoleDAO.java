package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.AccountRoleConstant;
import com.ntk.epcm.model.AccountRole;

@Component
public class AccountRoleDAO implements IAccountRoleDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRoleDAO.class);
	
	@Inject
	SessionFactory factory;

	@Override
	public boolean insert(AccountRole accountRole) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.save(accountRole);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while insert new role", e);
			session.getTransaction().rollback();
			error=true;
		}finally {
			session.close();
		}
		return !error;
	}

	@Override
	public boolean save(AccountRole accountRole) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(accountRole);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while save role", e);
			session.getTransaction().rollback();
			error=true;
		}finally {
			session.close();
		}
		return !error;
	}

	@Override
	public boolean remove(AccountRole accountRole) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.remove(accountRole);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while remove role", e);
			session.getTransaction().rollback();
			error=true;
		}finally{
			session.close();
		}
		return !error;
	}

	@Override
	public List<AccountRole> findByAccount(int account_id) {
		List<AccountRole> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s where %s = :account_id", 
					AccountRoleConstant.TABLE, AccountRoleConstant.ACCOUNT_ID), AccountRole.class)
			.setParameter("account_id", account_id)
			.getResultList();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while findByAccount", e);
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<AccountRole> findByRole(String role) {
		List<AccountRole> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s where %s = :role", 
					AccountRoleConstant.TABLE, AccountRoleConstant.ROLE_KEY), AccountRole.class)
			.setParameter("role", role)
			.getResultList();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while findByRole", e);
		}finally{
			session.close();
		}
		return list;
	}

}
