package com.ntk.epcm.manage.bean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.Customer;
import com.ntk.epcm.service.CustomerDeviceService;
import com.ntk.epcm.service.CustomerService;

@Component
public class CustomerBean implements InitializingBean, Observer {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBean.class);

	@Inject
	CustomerService customerService;

	@Inject
	CustomerDeviceService customerDeviceService;

	Customer customer = new Customer();

	List<Customer> list;
	List<Customer> listSelected;
	List<Customer> listFiltered;
	List<Customer> listFree;

	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("database is changed, call refresh");
		if (o instanceof CustomerDeviceService) {
			loadFreeCustomers();
		} else {
			loadCustomers();
			loadFreeCustomers();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		customerService.addObserver(this);
		customerDeviceService.addObserver(this);
		loadCustomers();
		loadFreeCustomers();
	}

	public void refresh() {
		loadCustomers();
	}

	private void loadCustomers() {
		list = customerService.findAll();
	}

	private void loadFreeCustomers() {
		listFree = customerService.findFreeCustomers();
	}

	public void save() {
		// check whenever this is a new customer
		if (customer.getCustomer_id() == 0) {
			customerService.insert(customer);
		} else {
			customerService.save(customer);
		}
	}

	public void delete() {
		customerService.remove(listSelected);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getList() {
		return list;
	}

	public void setList(List<Customer> list) {
		this.list = list;
	}

	public List<Customer> getListSelected() {
		return listSelected;
	}

	public void setListSelected(List<Customer> listSelected) {
		this.listSelected = listSelected;
	}

	public List<Customer> getListFiltered() {
		return listFiltered;
	}

	public void setListFiltered(List<Customer> listFiltered) {
		this.listFiltered = listFiltered;
	}

	public Customer getNewCustomer() {
		return new Customer();
	}

	public List<Customer> getListFree() {
		return listFree;
	}

}
