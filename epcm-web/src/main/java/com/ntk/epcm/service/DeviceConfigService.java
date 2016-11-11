package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IDeviceConfigDAO;
import com.ntk.epcm.model.DeviceConfig;

@Service
public class DeviceConfigService implements IDeviceConfigService{

	@Inject
	IDeviceConfigDAO dao;
	
	boolean needUpdate;
	
	@Override
	public int insert(DeviceConfig deviceConfig) {
		int id = dao.insert(deviceConfig);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(DeviceConfig deviceConfig) {
		boolean success = dao.save(deviceConfig);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public boolean remove(DeviceConfig deviceConfig) {
		boolean success = dao.remove(deviceConfig);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public DeviceConfig findById(int id) {
		return dao.findById(id);
	}

	@Override
	public DeviceConfig findByDeviceId(int device_id) {
		return dao.findByDeviceId(device_id);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

}
