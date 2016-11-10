package com.ntk.epcm.jms.dataprocessor;

import java.sql.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.Severity;
import com.ntk.epcm.manage.TaskManager;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceNotification;
import com.ntk.epcm.service.DeviceNotificationService;
import com.ntk.epcm.service.DeviceService;

@Component
public class BasicInfoProcessor implements IDataProcessor {
	public static final Logger LOGGER = LoggerFactory.getLogger(BasicInfoProcessor.class);
	
	@Inject
	DeviceService deviceService;

	@Inject
	DeviceNotificationService deviceNotificationService;
	
	private DataType dataType = DataType.BASICINFO;

	@Inject
	public BasicInfoProcessor(DataProcessorRegistry registry) {
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
					DeviceNotification notification = new DeviceNotification();
					notification.setDevice(oldDevice);
					notification.setSeverity(Severity.WARN);
					notification.setDescription(String.format("Ip address %s is used by %s and %s", 
							ipAddress, macAddress, oldDevice.getMacAddress()));
					deviceNotificationService.insert(notification);
					notification.setDevice(device);
					deviceNotificationService.insert(notification);
					LOGGER.debug("WARN IP CONLFICT ({}) on device {} and {}", ipAddress, macAddress, oldDevice.getMacAddress());
				}
			}
			// update Device
			if(device.getLastUpdate().getTime()>oldDevice.getLastUpdate().getTime()){
				//TODO add WARN override information to device
				LOGGER.debug("WARN override infomation on device {}", macAddress);
				//use mix data
				device.setLocation(oldDevice.getLocation());
			}
			if(device.getConsumeNumber()<oldDevice.getConsumeNumber()){
				LOGGER.debug("Consume Number ERROR: {} cannot update because lesser than {} on device {}", device.getConsumeNumber(), oldDevice.getConsumeNumber(),
						macAddress);
				//TODO add ERROR to device
				//use old data
				device.setConsumeNumber(oldDevice.getConsumeNumber());
			}
			if(device.getOldNumber()!=oldDevice.getOldNumber()){
				LOGGER.debug("Old Number ERROR: {} cannot changed to {} by device {}", oldDevice.getOldNumber(), device.getOldNumber(), macAddress);
				//TODO add ERROR to device
				device.setOldNumber(oldDevice.getOldNumber());
			}
			device.setDevice_id(oldDevice.getDevice_id());
			deviceService.save(device);
		}
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
