package com.ntk.epcm.manage.bean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.DeviceNotification;
import com.ntk.epcm.service.DeviceNotificationService;
import com.ntk.epcm.service.DeviceService;

@Component
public class NotificationBean implements InitializingBean, Observer{
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationBean.class);

	@Inject
	DeviceService deviceService;
	
	@Inject
	DeviceNotificationService deviceNotificationService;
	
	List<DeviceNotification> list;
	List<DeviceNotification> listSelected;

	public void delete() {
		deviceNotificationService.remove(listSelected);
		list.removeAll(listSelected);
		listSelected.clear();
	}
	
	public void refresh(){
		list = deviceNotificationService.findByAll();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("data is update, call refresh data");
		refresh();
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		list = deviceNotificationService.findByAll();
		deviceService.addObserver(this);
		deviceNotificationService.addObserver(this);
	}

	public List<DeviceNotification> getList() {
		return list;
	}

	public void setList(List<DeviceNotification> list) {
		this.list = list;
	}

	public List<DeviceNotification> getListSelected() {
		return listSelected;
	}

	public void setListSelected(List<DeviceNotification> listSelected) {
		this.listSelected = listSelected;
	}
	
	
}
