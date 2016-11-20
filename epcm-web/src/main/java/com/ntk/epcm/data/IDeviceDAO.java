package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.model.Device;

public interface IDeviceDAO {

	/**
	 * @param device
	 * @return id of new device
	 */
	int insert(Device device);

	/**
	 * @param device
	 * @return true when save success, otherwise
	 */
	boolean save(Device device);

	/**
	 * @param device
	 * @return true when save success, otherwise
	 */
	boolean remove(List<Device> devices);

	Device findDeviceById(int device_id);

	Device findDeviceByMacAddress(String macAddress);

	Device findByIpAddress(String ipAddress);

	boolean checkExistenceIpAddress(String ipAddress);

	List<Device> findAll();

	long countStatus(boolean isOn);

	List<Device> findFreeDevices();

}
