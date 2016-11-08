package com.ntk.epcm.simulator;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.manage.jms.EpcmDataMessage;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;

public class JmsSender {

	public static final Logger LOGGER = LoggerFactory.getLogger(JmsSender.class);
	private MessageProducer sender;
	private Session session;
	private Destination destination;
	private DataGenerator data;
	private static JmsSender instance;

	private JmsSender(MessageProducer sender, Session session, Destination destionation, DataGenerator data) {
		this.sender = sender;
		this.session = session;
		this.destination = destionation;
		this.data = data;
	}

	public static JmsSender instance(MessageProducer reportSender, Session session, Destination destination,
			DataGenerator data) {
		if (instance == null) {
			instance = new JmsSender(reportSender, session, destination, data);
		}
		return instance;
	}

	public void sendBasicInfo() throws JMSException, IOException {
		sendBasicInfo(destination);
	}

	public void sendBasicInfo(Destination destination) throws JMSException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Device device = data.getDevice();
		EpcmDataMessage dataMessage = new EpcmDataMessage(DataType.BASICINFO, device.getMacAddress(), device);
		String json = mapper.writeValueAsString(dataMessage);
		Message message = session.createTextMessage(json);
		LOGGER.debug("send to Service: {}", json);
		sender.send(destination, message);
	}

	public void sendOffDevice() throws IOException, JMSException {
		ObjectMapper mapper = new ObjectMapper();
		Device device = data.getDevice();
		device.setStatus(false);
		device.setIpAddress(EpcmConstant.NO_IP_ADDRESS);
		EpcmDataMessage dataMessage = new EpcmDataMessage(DataType.BASICINFO, device.getMacAddress(), device);
		String json = mapper.writeValueAsString(dataMessage);
		System.out.println("send shutdown report -> EPCM");
		sender.send(destination, session.createTextMessage(json));
		LOGGER.debug("send to Service: {}", json);
		System.out.println(json);
	}
	
}
