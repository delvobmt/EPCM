package com.ntk.epcm.service;

import com.ntk.epcm.data.IDeviceDAO;
import com.ntk.epcm.model.Device;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class DeviceService implements IDeviceService{
	@Inject
	IDeviceDAO dao;
	
	boolean needUpdate;

	@Override
	public int insert(Device device) {
		int device_id = dao.insert(device);
		needUpdate = !needUpdate?device_id != -1:needUpdate;
		return device_id;
	}

	@Override
	public void save(Device device) {
		device.setLastUpdate(new Date(System.currentTimeMillis()));
		//set flag when save success
		boolean result = dao.save(device);
		needUpdate = !needUpdate?result:needUpdate;
	}

	@Override
	public void remove(List<Device> devices) {
		//set flag when remove success
		boolean result = dao.remove(devices);
		needUpdate = !needUpdate?result:needUpdate;
	}

	@Override
	public Device findDeviceById(int device_id) {
		return dao.findDeviceById(device_id);
	}

	@Override
	public Device findDeviceByMacAddress(String macAddress) {
		return dao.findDeviceByMacAddress(macAddress);
	}
	
	@Override
	public boolean checkExistenceIpAddress(String ipAddress) {
		return dao.checkExistenceIpAddress(ipAddress);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

	@Override
	public List<Device> findAll() {
		needUpdate = false;
		return dao.findAll();
	}

	@Override
	public Device findByIpAddress(String ipAddress) {
		return dao.findByIpAddress(ipAddress);
	}
}
