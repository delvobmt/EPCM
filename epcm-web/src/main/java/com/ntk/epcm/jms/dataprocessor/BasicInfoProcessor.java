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
	
	private final DataType dataType = DataType.BASICINFO;

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
					Device conflict = deviceService.findByIpAddress(ipAddress);
					DeviceNotification notification = new DeviceNotification();
					notification.setDevice(oldDevice);
					notification.setSeverity(Severity.WARN.toString());
					notification.setDescription(String.format("Ip address (%s) is used by (%s) and (%s)", 
							ipAddress, macAddress, conflict.getMacAddress()));
					deviceNotificationService.insert(notification);
					notification.setDevice(conflict);
					deviceNotificationService.insert(notification);
					LOGGER.debug("WARN IP CONLFICT ({}) on device {} and {}",
							ipAddress, macAddress, conflict.getMacAddress());
				}
			}
			// update Device
			if(device.getLastUpdate().getTime()>oldDevice.getLastUpdate().getTime()){
				LOGGER.debug("WARN override infomation on device {}", macAddress);
				DeviceNotification notification = new DeviceNotification();
				notification.setDevice(oldDevice);
				notification.setDescription(String.format("Update device (%s) uses mixed information", macAddress));
				notification.setSeverity(Severity.INFO.toString());
				deviceNotificationService.insert(notification);
				//use mix data
				device.setLocation(oldDevice.getLocation());
			}
			if(device.getConsumeNumber()<oldDevice.getConsumeNumber()){
				LOGGER.debug("Consume Number ERROR: {} cannot update because lesser than {} on device {}",
						device.getConsumeNumber(), oldDevice.getConsumeNumber(), macAddress);
				DeviceNotification notification = new DeviceNotification();
				notification.setDevice(oldDevice);
				notification.setDescription(String.format("New Consume Number (%s) cannot update because lesser than current(%s) by device (%s)", 
						device.getConsumeNumber(), oldDevice.getConsumeNumber(), macAddress));
				notification.setSeverity(Severity.ERROR.toString());
				deviceNotificationService.insert(notification);
				//use old data
				device.setConsumeNumber(oldDevice.getConsumeNumber());
			}
			if(device.getOldNumber()!=oldDevice.getOldNumber()){
				LOGGER.debug("Old Number ERROR: {} cannot changed to {} by device {}",
						oldDevice.getOldNumber(), device.getOldNumber(), macAddress);
				DeviceNotification notification = new DeviceNotification();
				notification.setDevice(oldDevice);
				notification.setDescription(String.format("Old Number (%s) cannot changed to (%s) by device (%s)", 
						oldDevice.getOldNumber(), device.getOldNumber(), macAddress));
				notification.setSeverity(Severity.ERROR.toString());
				deviceNotificationService.insert(notification);
				device.setOldNumber(oldDevice.getOldNumber());
			}
			device.setDevice_id(oldDevice.getDevice_id());
			device.setLastUpdate(new Date(System.currentTimeMillis()));
			deviceService.save(device);
		}
	}

	public DataType getDataType() {
		return dataType;
	}
}
