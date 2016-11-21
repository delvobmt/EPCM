package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.ICustomerDeviceDAO;
import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.model.Customer;
import com.ntk.epcm.model.CustomerDevice;
import com.ntk.epcm.model.Device;

@Service
public class CustomerDeviceService extends Observable {

	@Inject
	ICustomerDeviceDAO dao;

	public int insert(CustomerDevice customerDevice) {
		int id = dao.insert(customerDevice);
		if (id != -1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	public boolean save(CustomerDevice customerDevice) {
		boolean success = dao.save(customerDevice);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(List<CustomerDevice> customerDevice) {
		boolean success = dao.remove(customerDevice);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public CustomerDevice findById(int id) {
		return dao.findById(id);
	}

	public CustomerDevice findByDevice(Device device) {
		return dao.findByDevice(device);
	}

	public CustomerDevice findByCustomer(Customer customer) {
		return dao.findByCustomer(customer);
	}

	public CustomerDevice findByConsumeGroup(ConsumeGroup consumeGroup) {
		return dao.findByConsumeGroup(consumeGroup);
	}

	public List<CustomerDevice> findAll() {
		return dao.findAll();
	}

}
