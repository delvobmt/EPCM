package com.ntk.epcm.service;

import java.util.List;

import com.ntk.epcm.model.Customer;

public interface ICustomerService {
	int insert(Customer customer);
	boolean save(Customer customer);
	boolean remove(List<Customer> customer);
	Customer findById(int id);
}
