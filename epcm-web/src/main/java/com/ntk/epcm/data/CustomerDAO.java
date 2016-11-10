package com.ntk.epcm.data;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ntk.epcm.model.Customer;

public class CustomerDAO implements ICustomerDAO{

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDAO.class);
	
	@Inject
	SessionFactory factory;
	
	@Override
	public int insert(Customer customer) {
		int id = -1;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			id = (int) session.save(customer);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while insert new Customer",e);
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public boolean save(Customer customer) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(customer);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while save Customer",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public boolean remove(Customer customer) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.remove(customer);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while remove Customer",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public Customer findById(int id) {
		Customer customer = null;
		Session session = factory.openSession();
		try {
			customer = session.load(Customer.class, id);
		} catch (HibernateException e) {
			LOGGER.error("ERROR while find by Id Customer",e);
		}
		session.close();
		return customer;
	}

}
