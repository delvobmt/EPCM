package com.ntk.epcm.data;

import com.ntk.epcm.model.Customer;

public interface ICustomerDAO {
	int insert(Customer customer);
	boolean save(Customer customer);
	boolean remove(Customer customer);
	Customer findById(int id);
}
