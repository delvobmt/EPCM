package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.ICustomerDAO;
import com.ntk.epcm.model.Customer;

@Service
public class CustomerService extends Observable implements ICustomerService {

	@Inject
	ICustomerDAO dao;
	
	@Override
	public int insert(Customer customer) {
		int id = dao.insert(customer);
		if(id!=-1){
			setChanged();
			notifyObservers();
		}
		return id;
	}

	@Override
	public boolean save(Customer customer) {
		boolean success = dao.save(customer);
		if(success){
			setChanged();
			notifyObservers();
		}
		return success;
	}

	@Override
	public boolean remove(List<Customer> customer) {
		boolean success = dao.remove(customer);
		if(success){
			setChanged();
			notifyObservers();
		}
		return success;
	}

	@Override
	public Customer findById(int id) {
		return dao.findById(id);
	}

	public List<Customer> findAll() {
		return dao.findAll();
	}

}
