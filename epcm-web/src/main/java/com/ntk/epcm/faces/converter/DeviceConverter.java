package com.ntk.epcm.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.DeviceService;

@Component
public class DeviceConverter implements Converter {

	@Inject
	DeviceService deviceService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String macAddress) {
		Device device = null;
		if(!StringUtils.isEmpty(macAddress)){
			device = deviceService.findDeviceByMacAddress(macAddress);
		}
		return device;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object value) {
		if(value instanceof Device){
			return ((Device)value).getMacAddress();
		}
		return null;
	}

}
