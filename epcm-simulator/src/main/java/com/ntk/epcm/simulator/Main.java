package com.ntk.epcm.simulator;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceBuilder;

public class Main {

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Connection connection;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private DataGenerator data;
	private Device device;

	public static void main(String[] args) throws Exception {

		Main simulator = new Main();
		// send request to epcm
		simulator.init();
		simulator.start();
		simulator.destroy();
	}

	private void init() throws JMSException {
		factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue deviceReportQueue = session.createQueue("DeviceReportQueue");
		producer = session.createProducer(deviceReportQueue);
		connection.start();

		data = new DataGenerator();
		// register EPCM Request Listener
		try {
			device = data.getDevice();
			Topic EpcmRequestTopic = session.createTopic("EPCMRequestTopic");
			consumer = session.createConsumer(EpcmRequestTopic, String.format("device='%s'", device.getMacAddress()));
			consumer.setMessageListener(new EpcmMessageListener());
			
			//test listener
//			producer = session.createProducer(EpcmRequestTopic);
//			TextMessage message = session.createTextMessage();
//			message.setStringProperty("device", device.getMacAddress());
//			producer.send(message);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void start() throws JMSException {
		Scanner scanner = new Scanner(System.in);
		int c = -1;
		do {
			menu();
			c = scanner.nextInt();
			switch (c) {
			case 0:
				System.out.println("bye");
				scanner.close();
				break;
			default: {
				System.out.println(String.format("%s isn't supported", c));
			}
				break;
			}
		} while (c != 0);

	}

	private void menu() {
		System.out.println("0. exit");
	}

	private void send(Object object) throws JMSException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(object);
			Message message = session.createTextMessage(json);
			producer.send(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void destroy() throws JMSException {
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}
}
