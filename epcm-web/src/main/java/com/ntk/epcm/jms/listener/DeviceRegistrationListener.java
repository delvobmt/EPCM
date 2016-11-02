package com.ntk.epcm.jms.listener;

import java.io.IOException;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.IDeviceService;

public class DeviceRegistrationListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRegistrationListener.class);

	@Inject
	IDeviceService deviceService;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				LOGGER.debug("process device Registration");
				ObjectMapper mapper = new ObjectMapper();
				Device device = mapper.readValue(msg.getText(), Device.class);

				// check conflict Ip Address
				String ipAddress = device.getIpAddress();
				if (deviceService.checkExistenceIpAddress(ipAddress)&&!ipAddress.equals(EpcmConstant.DEFAULT_IP_ADDRESS)) {
					LOGGER.error("register new device FAIL, because of IP CONLFICT ({})", ipAddress);
				} else {
					String macAddress = device.getMacAddress();
					Device oldDevice = deviceService.findDeviceByMacAddress(macAddress);
					if (oldDevice == null) {
						// register new device
						if (deviceService.insert(device) != -1)
							LOGGER.debug("{} is registered into system", device.getMacAddress());
						else
							LOGGER.debug("FAIL: {} is NOT registered into system", device.getMacAddress());
					} else {
						// update Device
						device.setDevice_id(oldDevice.getDevice_id());
						deviceService.save(device);
					}
					//TODO get more info of device
				}
			} catch (JMSException | IOException e) {
				LOGGER.error("error while process message ", e);
			}

		} else {
			LOGGER.error("unsupport {}", message.getClass());
		}
	}
}
