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
				if (deviceService.insert(device) != -1)
					LOGGER.debug("{} is registered into system", device.getMacAddress());
				else
					LOGGER.debug("{} is NOT registered into system", device.getMacAddress());
			} catch (JMSException | IOException e) {
				LOGGER.error("error while process message ", e);
			}

		} else {
			LOGGER.error("unsupport {}", message.getClass());
		}
	}
}
