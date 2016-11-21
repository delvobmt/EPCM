package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.model.Customer;
import com.ntk.epcm.model.CustomerDevice;
import com.ntk.epcm.model.Device;

public interface ICustomerDeviceDAO {
	int insert(CustomerDevice customerDevice);
	boolean save(CustomerDevice customerDevice);
	boolean remove(List<CustomerDevice> customerDevice);
	
	CustomerDevice findById(int id);
	CustomerDevice findByDevice(Device device);
	CustomerDevice findByCustomer(Customer customer);
	CustomerDevice findByConsumeGroup(ConsumeGroup consumeGroup);
	List<CustomerDevice> findAll();
	
}
