package com.ntk.epcm.service;

import com.ntk.epcm.data.IDeviceDAO;
import com.ntk.epcm.model.Device;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class DeviceService implements IDeviceService{
	@Inject
	IDeviceDAO deviceDao;
	
	boolean needUpdate;

	@Override
	public int insert(Device device) {
		int device_id = deviceDao.insert(device);
		needUpdate = device_id != -1;
		return device_id;
	}

	@Override
	public void save(Device device) {
		//set flag when save success
		needUpdate = deviceDao.save(device);
	}

	@Override
	public void remove(Device device) {
		//set flag when remove success
		needUpdate = deviceDao.remove(device);
	}

	@Override
	public Device findDeviceById(int device_id) {
		return deviceDao.findDeviceById(device_id);
	}

	@Override
	public Device findDeviceByMacAddress(String macAddress) {
		return deviceDao.findDeviceByMacAddress(macAddress);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

	@Override
	public List<Device> findAll() {
		return deviceDao.findAll();
	}

	
}
