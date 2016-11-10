package com.ntk.epcm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.data.IDeviceNotificationDAO;
import com.ntk.epcm.model.DeviceNotification;

@Service
public class DeviceNotificationService implements IDeviceNotificationService {

	@Inject
	IDeviceNotificationDAO dao;
	
	@Override
	public int insert(DeviceNotification deviceNotification) {
		return dao.insert(deviceNotification);
	}

	@Override
	public boolean save(DeviceNotification deviceNotification) {
		return dao.save(deviceNotification);
	}

	@Override
	public boolean remove(DeviceNotification deviceNotification) {
		return remove(deviceNotification);
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
