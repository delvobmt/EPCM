package com.ntk.epcm.jms.dataprocessor;

import java.sql.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.DeviceService;

@Component
public class BasicInfoDataProcessor implements IDataProcessor {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicInfoDataProcessor.class);
	
	@Inject
	DeviceService deviceService;
	
	private DataType dataType = DataType.BASICINFO;

	@Inject
	public BasicInfoDataProcessor(DataProcessorRegistry registry) {
		registry.register(dataType, this);
	}
	
	@Override
	public void process(Object data) {
		ObjectMapper mapper = new ObjectMapper();
		Device device = mapper.convertValue(data, Device.class);
		String macAddress = device.getMacAddress();
		String ipAddress = device.getIpAddress();

		Device oldDevice = deviceService.findDeviceByMacAddress(macAddress);
		device.setLastUpdate(new Date(System.currentTimeMillis()));

		if (oldDevice == null) {
			// register new device
			// check conflict Ip Address
			if (deviceService.checkExistenceIpAddress(ipAddress)
					&& !ipAddress.equals(EpcmConstant.NO_IP_ADDRESS)) {
				LOGGER.error("register new device FAIL, because of IP CONLFICT ({})", ipAddress);
			} else if (deviceService.insert(device) != -1)
				LOGGER.debug("{} is registered into system", device.getMacAddress());
			else
				LOGGER.debug("FAIL: {} is NOT registered into system", device.getMacAddress());
		} else {
			//check conflict ip when IpAddress changed
			if(!ipAddress.equals(oldDevice.getIpAddress())){
				if (deviceService.checkExistenceIpAddress(ipAddress)
						&& !ipAddress.equals(EpcmConstant.NO_IP_ADDRESS)) {
					//TODO add WARN for all 2 devices
					LOGGER.error("WARN IP CONLFICT ({}) with mac {}", ipAddress, macAddress);
				}
			}
			// update Device
			device.setDevice_id(oldDevice.getDevice_id());
			deviceService.save(device);
		}
		// TODO get more info of device
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
