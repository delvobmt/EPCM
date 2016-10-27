package com.ntk.epcm.jms.listener;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;

public class DeviceRegistrationListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRegistrationListener.class);

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				ObjectMapper mapper = new ObjectMapper();
				Device device = mapper.readValue(msg.getText(), Device.class);
				LOGGER.debug("{} is registered into system", device.getDevice_id());
			} catch (JMSException | IOException e) {
				LOGGER.error("error while process message ", e);
			}

		} else {
			LOGGER.error("unsupport {}", message.getClass());
		}
	}
}
