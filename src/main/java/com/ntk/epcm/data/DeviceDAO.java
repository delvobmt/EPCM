package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DataConstant;
import com.ntk.epcm.model.Device;

@Component
public class DeviceDAO implements IDeviceDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceDAO.class);
	@Inject
	SessionFactory factory;

	@SuppressWarnings("finally")
	@Override
	public int insert(Device device) {
		Session session = factory.openSession();
		int device_id = -1;
		try {
			session.getTransaction().begin();
			device_id = (int) session.save(device);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while register device", e);
			session.getTransaction().rollback();
		} finally {
			session.close();
			return device_id;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean save(Device device) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(device);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while save modified device", e);
			error = true;
		} finally {
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean remove(Device device) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.remove(device);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while remove device", e);
			error = true;
		} finally {
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Device findDeviceById(int device_id) {
		Session session = factory.openSession();
		Device device = null;
		try {
			device = session.load(Device.class, device_id);
		} catch (HibernateException e) {
			LOGGER.error("error while findDeviceById {}", device_id, e);
		} finally {
			session.close();
			return device;
		}
	}

	@SuppressWarnings({ "finally", "deprecation" })
	@Override
	public Device findDeviceByMacAddress(String macAddress) {
		Session session = factory.openSession();
		Device device = null;
		try {
			Criteria criteria = session.createCriteria(Device.class);
			criteria.add(Restrictions.eq("macAddress", macAddress)).setMaxResults(1);
			device = (Device) criteria.uniqueResult();
		} catch (HibernateException e) {
			LOGGER.error("error while findDeviceByMacAddress {}", macAddress, e);
		} finally {
			session.close();
			return device;
		}
	}

	@SuppressWarnings({ "unchecked", "finally" })
	@Override
	public List<Device> findAll() {
		Session session = factory.openSession();
		List<Device> list = Collections.emptyList();
		try {
			Query<Device> query = session.createQuery("from "+ DataConstant.TABLE_DEVICE);
			list = query.getResultList();
		} catch (HibernateException e) {
			LOGGER.error("error while findAll",e);
		}finally {
			session.close();
			return list;
		}
	}

}
