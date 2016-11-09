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
import com.ntk.epcm.jms.dataprocessor.DataProcessorRegistry;
import com.ntk.epcm.manage.jms.EpcmDataMessage;
import com.ntk.epcm.service.IDeviceService;

public class DeviceReportListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceReportListener.class);

	@Inject
	IDeviceService deviceService;
	
	@Inject
	DataProcessorRegistry processorRegistry;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				LOGGER.debug("receive message {}", msg.getText());
				ObjectMapper mapper = new ObjectMapper();
				EpcmDataMessage data = mapper.readValue(msg.getText(), EpcmDataMessage.class);
				processorRegistry.get(data.getDataType()).process(data.getData());
			} catch (JMSException | IOException e) {
				LOGGER.error("error while process message ", e);
			}

		} else {
			LOGGER.error("unsupport {}", message.getClass());
		}
	}
}
