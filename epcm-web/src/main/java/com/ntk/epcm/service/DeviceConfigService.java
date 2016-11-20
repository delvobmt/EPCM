package com.ntk.epcm.service;

import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IDeviceConfigDAO;
import com.ntk.epcm.model.DeviceConfig;

@Service
public class DeviceConfigService extends Observable {

	@Inject
	IDeviceConfigDAO dao;

	public int insert(DeviceConfig deviceConfig) {
		int id = dao.insert(deviceConfig);
		if (id != -1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	public boolean save(DeviceConfig deviceConfig) {
		boolean success = dao.save(deviceConfig);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(DeviceConfig deviceConfig) {
		boolean success = dao.remove(deviceConfig);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public DeviceConfig findById(int id) {
		return dao.findById(id);
	}

	public DeviceConfig findByDeviceId(int device_id) {
		return dao.findByDeviceId(device_id);
	}

}
