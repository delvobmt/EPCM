package com.ntk.epcm.simulator.processor;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.manage.jms.EpcmResponseObject;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.JmsSender;
import com.ntk.epcm.simulator.data.DataGenerator;

public class PollProcessor implements IRequestProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollProcessor.class);

	private MessageProducer sender;
	private Session session;
	private Destination destination;
	private DataGenerator data;
	
	public PollProcessor(MessageProducer reportSender, Session session, Destination defaultReplyTo, DataGenerator data) {
		this.sender = reportSender;
		this.session = session;
		this.destination = defaultReplyTo;
		this.data = data;
	}

	@Override
	public void process(TextMessage message) {
		try {
			LOGGER.debug("receive message: {}", message.getText());
			Destination replyTo = message.getJMSReplyTo();
			Device device = data.getDevice();
			EpcmResponseObject responseObject = new EpcmResponseObject(device.getMacAddress());
			responseObject.setSucess(true);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(responseObject);
			TextMessage reponseMessage = session.createTextMessage(json);
			System.out.println("send response for poll request -> EPCM");
			System.out.println(json);
			LOGGER.debug("send to service: {}", json);
			if (replyTo != null) {
				//receive device has received message
				sender.send(replyTo, reponseMessage);
			}
			//send poll result to service
			JmsSender reporter = JmsSender.instance(sender, session, destination, data);
			reporter.sendBasicInfo();
			//send others info
		} catch (JMSException | IOException e) {
			e.printStackTrace();
		}
	}

}
