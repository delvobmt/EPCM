package com.ntk.epcm.data;

import com.ntk.epcm.model.DeviceConfig;

public interface IDeviceConfigDAO {
	int insert(DeviceConfig deviceConfig);
	boolean save(DeviceConfig deviceConfig);
	boolean remove(DeviceConfig deviceConfig);
	DeviceConfig findById(int id);
	DeviceConfig findByDeviceId(int device_id);
}
