package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DeviceConstant;
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
		} catch (Exception e) {
			LOGGER.error("error while insert({})", device, e);
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
		} catch (Exception e) {
			LOGGER.error("error while save({})", device, e);
			session.getTransaction().rollback();
			error = true;
		} finally {
			session.close();
			return !error;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public boolean remove(List<Device> devices) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			devices.forEach(session::remove);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("error while remove({})", devices, e);
			session.getTransaction().rollback();
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
		} catch (Exception e) {
			LOGGER.error("error while findDeviceById({})", device_id, e);
		} finally {
			session.close();
			return device;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Device findDeviceByMacAddress(String macAddress) {
		Session session = factory.openSession();
		Device device = null;
		try {
			device = (Device) session.createQuery(String.format("from %s where %s=:macAddress", 
					DeviceConstant.TABLE, DeviceConstant.MAC_ADDRESS_KEY),Device.class)
					.setParameter("macAddress", macAddress)
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			LOGGER.error("error while findDeviceByMacAddress({})", macAddress, e);
		} finally {
			session.close();
			return device;
		}
	}

	@Override
	public boolean checkExistenceIpAddress(String ipAddress) {
		Session session = factory.openSession();
		try {
			long count = (long) session.createQuery(String.format("select count(*) from %s where %s=:ipAddress", 
					DeviceConstant.TABLE, DeviceConstant.IP_ADRESS_KEY))
					.setParameter("ipAddress", ipAddress)
					.getSingleResult();
			return count != 0;
		} catch (Exception e) {
			LOGGER.error("error while checkIpAddressAvailable({})", ipAddress, e);
		}
		return false;
	}

	@SuppressWarnings("finally")
	@Override
	public List<Device> findAll() {
		Session session = factory.openSession();
		List<Device> list = Collections.emptyList();
		try {
			Query<Device> query = session.createQuery("from " + DeviceConstant.TABLE, Device.class);
			list = query.getResultList();
		} catch (Exception e) {
			LOGGER.error("error while findAll()", e);
		} finally {
			session.close();
			return list;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Device findByIpAddress(String ipAddress) {
		Session session = factory.openSession();
		Device device = null;
		try {
			device = session.createQuery(String.format("from %s where %s=:ipAddress", 
					DeviceConstant.TABLE, DeviceConstant.IP_ADRESS_KEY),Device.class)
					.setParameter("ipAddress", ipAddress)
					.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			LOGGER.error("error while findDeviceByMacAddress({})", ipAddress, e);
		} finally {
			session.close();
			return device;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public long countStatus(boolean isOn) {
		Session session = factory.openSession();
		long count = -1;
		try {
			count = session.createQuery(String.format("select count(*) from %s where %s=:status", 
					DeviceConstant.TABLE, DeviceConstant.STATUS_KEY),Long.class)
					.setParameter("status", isOn)
					.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("error while countOffDevices({})", e);
		} finally {
			session.close();
			return count;
		}
	}

}
