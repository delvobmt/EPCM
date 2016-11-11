package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.ICustomerDeviceDAO;
import com.ntk.epcm.model.CustomerDevice;

@Service
public class CustomerDeviceService implements ICustomerDeviceService {

	@Inject
	ICustomerDeviceDAO dao;
	
	private boolean needUpdate;
	
	@Override
	public int insert(CustomerDevice customerDevice) {
		int id = dao.insert(customerDevice);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(CustomerDevice customerDevice) {
		boolean success = dao.save(customerDevice);
		needUpdate = needUpdate ? needUpdate : success;
		return success;
	}

	@Override
	public boolean remove(CustomerDevice customerDevice) {
		boolean success = dao.remove(customerDevice);
		needUpdate = needUpdate ? needUpdate : success;
		return success;
	}

	@Override
	public CustomerDevice findById(int id) {
		return dao.findById(id);
	}

	@Override
	public CustomerDevice findByDeviceId(int device_id) {
		return dao.findByDeviceId(device_id);
	}

	@Override
	public CustomerDevice findByCustomerId(int customer_id) {
		return dao.findByCustomerId(customer_id);
	}

	@Override
	public CustomerDevice findByConsumeGroupId(int consumeGroup_id) {
		return dao.findByConsumeGroupId(consumeGroup_id);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

}
