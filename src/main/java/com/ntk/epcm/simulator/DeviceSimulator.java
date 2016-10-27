package com.ntk.epcm.simulator;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceBuilder;

public class DeviceSimulator {
	private ActiveMQConnectionFactory factory;
	private Session session;
	private Destination destination;
	private Connection connection;
	private MessageProducer producer;

	public static void main(String[] args) throws JMSException {
		DeviceSimulator simulator = new DeviceSimulator();
		simulator.init();

		Device device = new DeviceBuilder().device_id(1).macAddress("ma:ca:dr:es:s0:00").build();

		simulator.send(device);

		simulator.destroy();

	}

	private void send(Object object) throws JMSException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(object);
			Message message = session.createTextMessage(json);
			producer.send(message);
		} catch (JsonProcessingException e) {
		}
	}

	private void init() throws JMSException {
		factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("DeviceRegistrationQueue");
		producer = session.createProducer(destination);
	}

	private void destroy() throws JMSException {
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}
}
