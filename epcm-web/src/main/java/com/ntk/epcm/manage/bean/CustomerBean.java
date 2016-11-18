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
import com.ntk.epcm.service.CustomerService;

@Component
public class CustomerBean implements InitializingBean, Observer {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerBean.class);

	@Inject
	CustomerService customerService;

	Customer customer = new Customer();

	List<Customer> list;
	List<Customer> listSelected;
	List<Customer> listFiltered;

	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("database is changed, call refresh");
		refresh();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		customerService.addObserver(this);
		refresh();
	}

	private void refresh() {
		list = customerService.findAll();
	}

	public void save() {
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
}
