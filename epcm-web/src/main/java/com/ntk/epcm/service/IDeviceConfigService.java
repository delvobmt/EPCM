package com.ntk.epcm.service;

import com.ntk.epcm.model.DeviceConfig;

public interface IDeviceConfigService {
	int insert(DeviceConfig deviceConfig);
	boolean save(DeviceConfig deviceConfig);
	boolean remove(DeviceConfig deviceConfig);
	
	DeviceConfig findById(int id);
	DeviceConfig findByDeviceId(int device_id);
	
	boolean needUpdate();
}
