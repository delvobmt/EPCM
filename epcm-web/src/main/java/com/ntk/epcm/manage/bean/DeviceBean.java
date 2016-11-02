package com.ntk.epcm.manage.bean;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.IDeviceService;

@Component
public class DeviceBean implements InitializingBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceBean.class);

	@Inject
	IDeviceService deviceService;

	List<Device> list;
	List<Device> listSelected;
	
	int progress = 0;
	
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
	
	public void poll() {
		progress = 0;
	}
	
	public void onPollComplete() {
		// TODO handle on poll complete event from UI
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

	public int getProgress() {
		LOGGER.debug("progress: {}", progress);
		progress = progress>=100?100:progress+10;
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
}
