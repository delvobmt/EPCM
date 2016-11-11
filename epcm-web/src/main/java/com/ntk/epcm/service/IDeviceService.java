package com.ntk.epcm.service;

import java.util.List;

import com.ntk.epcm.model.Device;

public interface IDeviceService {

	int insert(Device device);

	void save(Device device);

	void remove(List<Device> devices);

	boolean needUpdate();

	Device findDeviceById(int device_id);

	Device findDeviceByMacAddress(String macAddress);

	Device findByIpAddress(String ipAddress);

	boolean checkExistenceIpAddress(String ipAddress);

	List<Device> findAll();
}
