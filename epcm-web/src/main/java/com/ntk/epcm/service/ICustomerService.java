package com.ntk.epcm.service;

import com.ntk.epcm.model.Customer;

public interface ICustomerService {
	int insert(Customer customer);
	boolean save(Customer customer);
	boolean remove(Customer customer);
	Customer findById(int id);
	boolean needUpdate();
}
