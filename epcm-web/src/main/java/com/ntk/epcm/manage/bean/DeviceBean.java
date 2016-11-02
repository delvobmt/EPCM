package com.ntk.epcm.manage.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.IDeviceService;

@Scope("request")
@Component
public class DeviceBean implements InitializingBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceBean.class);

	@Inject
	IDeviceService deviceService;

	List<Device> list;
	List<Device> listSelected;
	Device selected;
	
	/**
	 * refresh device list. If there is no change, cache is returned.
	 * @return
	 */
	public void refresh() {
		if(list==null||deviceService.needUpdate()){
			LOGGER.debug("refresh device list from service");
			list = deviceService.findAll();
		}else{
			LOGGER.debug("refresh device list from cache");
		}
	}

	public List<Device> getList() {
		return list;
	}
	
	public void setListSelected(List<Device> listSelected) {
		this.listSelected = listSelected;
	}
	
	public List<Device> getListSelected() {
		return listSelected;
	}

	public Device getSelected() {
		return selected;
	}

	public void setSelected(Device selected) {
		this.selected = selected;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
}
