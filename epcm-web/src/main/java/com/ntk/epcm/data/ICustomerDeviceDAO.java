package com.ntk.epcm.data;

import com.ntk.epcm.model.CustomerDevice;

public interface ICustomerDeviceDAO {
	int insert(CustomerDevice customerDevice);
	boolean save(CustomerDevice customerDevice);
	boolean remove(CustomerDevice customerDevice);
	
	CustomerDevice findById(int id);
	CustomerDevice findByDeviceId(int device_id);
	CustomerDevice findByCustomerId(int customer_id);
	CustomerDevice findByConsumeGroupId(int consumeGroup_id);
	
}
