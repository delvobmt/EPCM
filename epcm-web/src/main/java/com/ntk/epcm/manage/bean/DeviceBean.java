package com.ntk.epcm.manage.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.model.SelectableDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.IDeviceService;

@Scope("request")
@Component
public class DeviceBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceBean.class);

	@Inject
	IDeviceService deviceService;

	List<Device> list;
	List<Device> listSelected;
	
	/**
	 * refresh device list. If there is no change, cache is returned.
	 * @return
	 */
	@PostConstruct
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

//	public void onRowSelect(SelectEvent event) {
//        FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
// 
//    public void onRowUnselect(UnselectEvent event) {
//        FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//	
}
