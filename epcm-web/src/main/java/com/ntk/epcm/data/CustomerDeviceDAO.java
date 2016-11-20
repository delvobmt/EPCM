package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.CustomerDeviceConstant;
import com.ntk.epcm.model.CustomerDevice;

@Component
public class CustomerDeviceDAO implements ICustomerDeviceDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDeviceDAO.class);
	
	@Inject
	SessionFactory factory;
	
	@Override
	public int insert(CustomerDevice customerDevice) {
		int id = -1;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			id = (int) session.save(customerDevice);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while insert new CustomerDevice",e);
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public boolean save(CustomerDevice customerDevice) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(customerDevice);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while save CustomerDevice",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public boolean remove(List<CustomerDevice> customerDevice) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			customerDevice.forEach(session::remove);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while remove CustomerDevice",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}
	
	
	@Override
	public CustomerDevice findByDeviceId(int device_id) {
		CustomerDevice customerDevice = null;
		Session session = factory.openSession();
		try {
			customerDevice = session.createQuery(String.format("from %s where %s=:device_id",
					CustomerDeviceConstant.TABLE, CustomerDeviceConstant.DEVICE_KEY),CustomerDevice.class)
					.setParameter("device_id", device_id)
					.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("ERROR while find by Device Id",e);
			session.getTransaction().rollback();
		}
		session.close();
		return customerDevice;
	}

	@Override
	public CustomerDevice findByCustomerId(int customer_id) {
		CustomerDevice customerDevice = null;
		Session session = factory.openSession();
		try {
			customerDevice = session.createQuery(String.format("from %s where %s=:customer_id",
					CustomerDeviceConstant.TABLE, CustomerDeviceConstant.CUSTOMER_KEY),CustomerDevice.class)
					.setParameter("customer_id", customer_id)
					.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("ERROR while find by customer Id",e);
			session.getTransaction().rollback();
		}
		session.close();
		return customerDevice;
	}

	@Override
	public CustomerDevice findByConsumeGroupId(int consumeGroup_id) {
		CustomerDevice customerDevice = null;
		Session session = factory.openSession();
		try {
			customerDevice = session.createQuery(String.format("from %s where %s=:consumeGroup_id",
					CustomerDeviceConstant.TABLE, CustomerDeviceConstant.CONSUME_GROUP_KEY),CustomerDevice.class)
					.setParameter("consumeGroup_id", consumeGroup_id)
					.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("ERROR while find by consume group Id",e);
			session.getTransaction().rollback();
		}
		session.close();
		return customerDevice;
	}

	@Override
	public CustomerDevice findById(int id) {
		CustomerDevice customerDevice = null;
		Session session = factory.openSession();
		try {
			customerDevice = session.load(CustomerDevice.class, id);
		} catch (Exception e) {
			LOGGER.error("ERROR while find by id",e);
			session.getTransaction().rollback();
		}
		session.close();
		return customerDevice;
	}

	@Override
	public List<CustomerDevice> findAll() {
		List<CustomerDevice> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s", CustomerDeviceConstant.TABLE),CustomerDevice.class).getResultList();
		} catch (Exception e) {
			LOGGER.error("ERROR while find all customer device",e);
			session.getTransaction().rollback();
		}
		session.close();
		return list;
	}

	
}
