package com.ntk.epcm.data;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DeviceConfigConstant;
import com.ntk.epcm.model.DeviceConfig;

@Component
public class DeviceConfigDAO implements IDeviceConfigDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceConfigDAO.class);
	
	@Inject
	SessionFactory factory;
	
	@SuppressWarnings("finally")
	@Override
	public int insert(DeviceConfig deviceConfig) {
		int id = -1;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			id = (int) session.save(deviceConfig);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while insert DeviceConfig",e);
			session.getTransaction().rollback();
		}finally{
			session.close();
			return id;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean save(DeviceConfig deviceConfig) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(deviceConfig);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while save DeviceConfig",e);
			session.getTransaction().rollback();
			error = true;
		}finally{
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean remove(DeviceConfig deviceConfig) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.remove(deviceConfig);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while remove DeviceConfig",e);
			session.getTransaction().rollback();
			error = true;
		}finally{
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public DeviceConfig findById(int id) {
		DeviceConfig deviceConfig = null;
		Session session = factory.openSession();
		try {
			deviceConfig = session.load(DeviceConfig.class, id);
		} catch (HibernateException e) {
			LOGGER.error("ERROR while remove DeviceConfig",e);
			session.getTransaction().rollback();
		}finally{
			session.close();
			return deviceConfig;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public DeviceConfig findByDeviceId(int device_id) {
		DeviceConfig deviceConfig = null;
		Session session = factory.openSession();
		try {
			session.createQuery(String.format("from %s where %s=:device_id", DeviceConfigConstant.TABLE, DeviceConfigConstant.DEVICE_ID_KEY));
		} catch (HibernateException e) {
			LOGGER.error("ERROR while remove DeviceConfig",e);
			session.getTransaction().rollback();
		}finally{
			session.close();
			return deviceConfig;
		}
	}

}
