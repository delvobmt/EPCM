package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.model.Customer;

public interface ICustomerDAO {
	int insert(Customer customer);

	boolean save(Customer customer);

	boolean remove(List<Customer> customer);

	Customer findById(int id);

	List<Customer> findAll();

	List<Customer> findFreeCustomers();
}
