package com.ntk.epcm.service;

import java.util.List;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.model.DeviceNotification;

public interface IDeviceNotificationService {
	int insert(DeviceNotification deviceNotification);
	boolean save(DeviceNotification deviceNotification);
	boolean remove(DeviceNotification deviceNotification);
	
	DeviceNotification findById(int id);
	List<DeviceNotification> findByDeviceId(int device_id);
	List<DeviceNotification> findBySeverity(Severity severity);
	List<DeviceNotification> findByAll();
	
	boolean needUpdate();
	
}
