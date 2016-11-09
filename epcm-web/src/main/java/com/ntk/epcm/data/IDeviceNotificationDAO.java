package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.model.DeviceNotification;

public interface IDeviceNotificationDAO {
	int insert(DeviceNotification notification);

	boolean save(DeviceNotification notification);

	boolean remove(DeviceNotification notification);

	DeviceNotification find(int id);

	List<DeviceNotification> findAll();

	List<DeviceNotification> findBySeverity(Severity severity);

	List<DeviceNotification> findByDevice(int device_id);
}
