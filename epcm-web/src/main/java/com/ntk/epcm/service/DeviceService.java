package com.ntk.epcm.service;

import com.ntk.epcm.data.IDeviceDAO;
import com.ntk.epcm.model.Device;

import java.sql.Date;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class DeviceService extends Observable implements IDeviceService{
	@Inject
	IDeviceDAO dao;
	
	@Override
	public int insert(Device device) {
		int device_id = dao.insert(device);
		if(device_id != -1) {
			setChanged();
			notifyObservers();
		};
		return device_id;
	}

	@Override
	public void save(Device device) {
		device.setLastUpdate(new Date(System.currentTimeMillis()));
		//set flag when save success
		boolean result = dao.save(device);
		if(result) {
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void remove(List<Device> devices) {
		//set flag when remove success
		boolean result = dao.remove(devices);
		if(result) {
			setChanged();
			notifyObservers();
		}
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
	public List<Device> findAll() {
		return dao.findAll();
	}

	@Override
	public Device findByIpAddress(String ipAddress) {
		return dao.findByIpAddress(ipAddress);
	}
}
