package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.ICustomerDAO;
import com.ntk.epcm.model.Customer;

@Service
public class CustomerService implements ICustomerService {

	@Inject
	ICustomerDAO dao;
	
	private boolean needUpdate = false;
	
	@Override
	public int insert(Customer customer) {
		int id = dao.insert(customer);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(Customer customer) {
		boolean success = dao.save(customer);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public boolean remove(Customer customer) {
		boolean success = dao.remove(customer);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public Customer findById(int id) {
		return dao.findById(id);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

}
