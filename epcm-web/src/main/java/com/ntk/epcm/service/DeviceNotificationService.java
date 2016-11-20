package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.data.IDeviceNotificationDAO;
import com.ntk.epcm.model.DeviceNotification;

@Service
public class DeviceNotificationService extends Observable {

	@Inject
	IDeviceNotificationDAO dao;

	public int insert(DeviceNotification deviceNotification) {
		int id = dao.insert(deviceNotification);
		if (id != -1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	public boolean save(DeviceNotification deviceNotification) {
		boolean success = dao.save(deviceNotification);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(List<DeviceNotification> deviceNotification) {
		boolean success = dao.remove(deviceNotification);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public DeviceNotification findById(int id) {
		return dao.findById(id);
	}

	public List<DeviceNotification> findByDeviceId(int device_id) {
		return dao.findByDevice(device_id);
	}

	public List<DeviceNotification> findBySeverity(Severity severity) {
		return dao.findBySeverity(severity);
	}

	public List<DeviceNotification> findByAll() {
		return dao.findAll();
	}

	public long countSevertiy(String severity) {
		return dao.countSeverity(severity);
	}
}
