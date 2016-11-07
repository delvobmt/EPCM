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
		needUpdate = !needUpdate?device_id != -1:needUpdate;
		return device_id;
	}

	@Override
	public void save(Device device) {
		//set flag when save success
		boolean result = deviceDao.save(device);
		needUpdate = !needUpdate?result:needUpdate;
	}

	@Override
	public void remove(List<Device> devices) {
		//set flag when remove success
		boolean result = deviceDao.remove(devices);
		needUpdate = !needUpdate?result:needUpdate;
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
	public boolean checkExistenceIpAddress(String ipAddress) {
		return deviceDao.checkExistenceIpAddress(ipAddress);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

	@Override
	public List<Device> findAll() {
		needUpdate = false;
		return deviceDao.findAll();
	}
}
