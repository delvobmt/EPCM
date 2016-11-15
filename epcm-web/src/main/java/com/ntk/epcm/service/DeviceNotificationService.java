package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.data.IDeviceNotificationDAO;
import com.ntk.epcm.model.DeviceNotification;

@Service
public class DeviceNotificationService extends Observable implements IDeviceNotificationService {

	@Inject
	IDeviceNotificationDAO dao;
	
	@Override
	public int insert(DeviceNotification deviceNotification) {
		int id = dao.insert(deviceNotification);
		if(id!=-1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	@Override
	public boolean save(DeviceNotification deviceNotification) {
		boolean success = dao.save(deviceNotification);
		if(success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	@Override
	public boolean remove(DeviceNotification deviceNotification) {
		boolean success = remove(deviceNotification);
		if(success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	@Override
	public DeviceNotification findById(int id) {
		return dao.findById(id);
	}

	@Override
	public List<DeviceNotification> findByDeviceId(int device_id) {
		return dao.findByDevice(device_id);
	}

	@Override
	public List<DeviceNotification> findBySeverity(Severity severity) {
		return dao.findBySeverity(severity);
	}

	@Override
	public List<DeviceNotification> findByAll() {
		return dao.findAll();
	}
}
