package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.CustomerConstant;
import com.ntk.epcm.model.Customer;

@Component
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			LOGGER.error("ERROR while save Customer",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public boolean remove(List<Customer> customer) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			customer.forEach(session::remove);
			session.getTransaction().commit();
		} catch (Exception e) {
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
		} catch (Exception e) {
			LOGGER.error("ERROR while find by Id Customer",e);
		}
		session.close();
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		List<Customer> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s", CustomerConstant.TABLE),Customer.class).getResultList();
		} catch (Exception e) {
			LOGGER.error("ERROR while findAll Customer",e);
		}
		session.close();
		return list;
	}

}
