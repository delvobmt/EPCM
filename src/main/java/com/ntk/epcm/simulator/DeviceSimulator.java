package com.ntk.epcm.simulator;

import java.io.IOException;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.ntk.epcm.jms.listener.DeviceRegistrationListener;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceBuilder;

public class DeviceSimulator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceSimulator.class);
	
	private ActiveMQConnectionFactory factory;
	private Session session;
	private Destination destination;
	private Connection connection;
	private MessageProducer producer;
	private MessageConsumer listener;

	public static void main(String[] args) throws Exception {
		
		//test serialize and deserialize object
		Device device = new DeviceBuilder().build();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
		String json = mapper.writeValueAsString(device);
		System.out.println(json);
		System.out.println();
		Device object = mapper.readValue(json, Device.class);
		System.out.println(object);
		
//		DeviceSimulator simulator = new DeviceSimulator();
//		simulator.init();
//		simulator.start();
//		simulator.destroy();

	}

	private void start() throws JMSException {
		Scanner scanner = new Scanner(System.in);
		int c = -1;
		do{
			menu();
			c = scanner.nextInt();
			switch(c){
				case 1:
				{
					Device device = new DeviceBuilder().device_id(1).macAddress("ma:ca:dr:es:s0:00").build();
					send(device);
				}break;
				case 2:
				{
					receiveMessage();
					System.out.println(String.format("%s isn't supported", c));
					
				}break;
				case 5:
				{
					startListener();
				}break;
				case 6:
				{
					stopListener();
				}break;
				case 0:
					System.out.println("bye");
					break;
				default:
				{
					System.out.println(String.format("%s isn't supported", c));
				}break;
			}
		}while(c!=0);

	}

	private void startListener() throws JMSException {
		listener = session.createConsumer(destination);
		listener.setMessageListener(new DeviceRegistrationListener());
		System.out.println("listener started...");
	}
	
	private void stopListener() throws JMSException {
		listener.close();
		System.out.println("listener stopped...");
	}

	private void menu() {
		System.out.println("1. send message");
		System.out.println("2. receive message");
		System.out.println("3.");
		System.out.println("4.");
		System.out.println("5. start listener");
		System.out.println("6. stop listener");
		System.out.println("0. exit");
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
	
	private void receiveMessage() throws JMSException {
		MessageConsumer consumer = session.createConsumer(destination);
		Message message = consumer.receive(1000);
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
		
		consumer.close();
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