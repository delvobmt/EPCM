package com.ntk.epcm.service;

import java.sql.Date;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.ntk.epcm.data.IDeviceDAO;
import com.ntk.epcm.model.Device;

@Component
public class DeviceService extends Observable {
	@Inject
	IDeviceDAO dao;

	public int insert(Device device) {
		int device_id = dao.insert(device);
		if (device_id != -1) {
			setChanged();
			notifyObservers();
		}
		;
		return device_id;
	}

	public boolean save(Device device) {
		device.setLastUpdate(new Date(System.currentTimeMillis()));
		boolean success = dao.save(device);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(List<Device> devices) {
		// set flag when remove success
		boolean success = dao.remove(devices);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public Device findDeviceById(int device_id) {
		return dao.findDeviceById(device_id);
	}

	public Device findDeviceByMacAddress(String macAddress) {
		return dao.findDeviceByMacAddress(macAddress);
	}

	public boolean checkExistenceIpAddress(String ipAddress) {
		return dao.checkExistenceIpAddress(ipAddress);
	}

	public List<Device> findAll() {
		return dao.findAll();
	}

	public Device findByIpAddress(String ipAddress) {
		return dao.findByIpAddress(ipAddress);
	}

	public long countStatus(boolean isOn) {
		return dao.countStatus(isOn);
	}

	public List<Device> findFreeDevices() {
		return dao.findFreeDevices();
	}
}
