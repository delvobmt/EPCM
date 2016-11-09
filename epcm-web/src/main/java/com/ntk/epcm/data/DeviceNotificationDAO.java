package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DeviceNotificationConstant;
import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.model.DeviceNotification;

@Component
public class DeviceNotificationDAO implements IDeviceNotificationDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceNotificationDAO.class);
	@Inject
	SessionFactory factory;

	@SuppressWarnings("finally")
	@Override
	public int insert(DeviceNotification notification) {
		Session session = factory.openSession();
		session.getTransaction().begin();
		int id = -1;
		try {
			session.save(notification);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			LOGGER.error("error while insert new Device Notification", e);
			session.getTransaction().rollback();
		} finally {
			session.close();
			return id;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean save(DeviceNotification notification) {
		boolean error = false;
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			DeviceNotification real =  session.load(DeviceNotification.class, notification.getDeviceNotification_id());
			if(real!=null){
				session.save(notification);
				session.getTransaction().commit();
			}else{
				error = true;
			}
		} catch (HibernateException e) {
			LOGGER.error("error while save device notification", e);
			session.getTransaction().rollback();
			error = true;
		} finally {
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean remove(DeviceNotification notification) {
		boolean error = false;
		Session session = factory.openSession();
		session.getTransaction().begin();
		try {
			session.remove(notification);
		} catch (HibernateException e) {
			LOGGER.error("error while delete device notification",e);
			error = true;
		}finally {
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public DeviceNotification find(int id) {
		DeviceNotification notification = null;
		Session session = factory.openSession();
		try {
			notification = session.load(DeviceNotification.class, id);
		} catch (HibernateException e) {
			LOGGER.error("error while find Device", e);
		}finally{
			session.close();
			return notification;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public List<DeviceNotification> findAll() {
		List<DeviceNotification> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			Query<DeviceNotification> query = session.createQuery("from "+DeviceNotificationConstant.TABLE, DeviceNotification.class);
			list = query.getResultList();
			
		} catch (HibernateException e) {
			LOGGER.error("error while findAll DeviceNotification",e);
		}finally{
			session.close();
			return list;
		}
	}

	@Override
	public List<DeviceNotification> findBySeverity(Severity severity) {
		List<DeviceNotification> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			Query<DeviceNotification> query = session.createQuery(String.format("from %s where %s=:severity", 
					DeviceNotificationConstant.TABLE, DeviceNotificationConstant.SEVERITY_KEY), DeviceNotification.class)
					.setParameter("severity", severity);
			list = query.getResultList();
		} catch (HibernateException e) {
			LOGGER.error("ERROR while findBySevertity",e);
		}
		return list;
	}

	@Override
	public List<DeviceNotification> findByDevice(int device_id) {
		// TODO Auto-generated method stub
		return null;
	}

}