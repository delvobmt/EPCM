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
	
	private boolean needUpdate = false;
	
	@Override
	public int insert(DeviceNotification deviceNotification) {
		int id = dao.insert(deviceNotification);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(DeviceNotification deviceNotification) {
		boolean success = dao.save(deviceNotification);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public boolean remove(DeviceNotification deviceNotification) {
		boolean sucess = remove(deviceNotification);
		needUpdate = needUpdate?needUpdate:sucess;
		return sucess;
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

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}
	
}
