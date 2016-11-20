package com.ntk.epcm.manage.bean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.model.Customer;
import com.ntk.epcm.model.CustomerDevice;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.CustomerDeviceService;

@Component
public class CustomerDeviceBean implements InitializingBean, Observer {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDeviceBean.class);

	@Inject
	CustomerDeviceService customerDeviceService;

	List<CustomerDevice> list;
	List<CustomerDevice> listSelected;
	List<CustomerDevice> listFiltered;
	CustomerDevice customerDevice;

	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("database is changed, call refresh {}",o.getClass().getName());
		loadCustomerDevices();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		customerDeviceService.addObserver(this);
		loadCustomerDevices();
	}

	public void refresh() {
		loadCustomerDevices();
	}

	private void loadCustomerDevices() {
		list = customerDeviceService.findAll();
	}
	
	public void save() {
//		check whenever create new customer Device
		if(customerDevice.getCustomerDevice_id()==0){
			customerDeviceService.insert(customerDevice);
		}else{
			customerDeviceService.save(customerDevice);
		}
	}
	
	public void delete() {
		customerDeviceService.remove(listSelected);
	}

	public List<CustomerDevice> getList() {
		return list;
	}

	public List<CustomerDevice> getListSelected() {
		return listSelected;
	}

	public void setListSelected(List<CustomerDevice> listSelected) {
		this.listSelected = listSelected;
	}

	public List<CustomerDevice> getListFiltered() {
		return listFiltered;
	}

	public void setListFiltered(List<CustomerDevice> listFiltered) {
		this.listFiltered = listFiltered;
	}

	public CustomerDevice getCustomerDevice() {
		return customerDevice;
	}

	public CustomerDevice getNewCustomerDevice() {
		CustomerDevice newCustomer = new CustomerDevice();
		newCustomer.setCustomer(new Customer());
		newCustomer.setConsumeGroup(new ConsumeGroup());
		newCustomer.setDevice(new Device());
		return newCustomer;
	}

	public void setCustomerDevice(CustomerDevice customerDevice) {
		this.customerDevice = customerDevice;
	}

}
