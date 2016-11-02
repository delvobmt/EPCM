package com.ntk.epcm.simulator;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;
import com.ntk.epcm.simulator.listener.EpcmMessageListener;
import com.ntk.epcm.simulator.processor.PollProcessor;

public class Main {

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Connection connection;
	private DataGenerator data;
	private Queue deviceServiceQueue;
	private Topic EpcmServiceTopic;
	private MessageProducer sender;
	private MessageConsumer receiver;
	private Device device;

	public static void main(String[] args) throws Exception {
		Main simulator = new Main();
		simulator.init();
		simulator.start();
		simulator.destroy();
	}

	private void init() throws JMSException {
		factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = factory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		deviceServiceQueue = session.createQueue(EpcmConstant.DEVICE_SERVICE_QUEUE);
		EpcmServiceTopic = session.createTopic(EpcmConstant.EPCM_SERVICE_TOPIC);

		sender = session.createProducer(deviceServiceQueue);

		data = new DataGenerator();

		connection.start();

		try {
			device = data.getDevice();

			// send register request to epcm service
			ObjectMapper mapper = new ObjectMapper();
			String deviceJson = mapper.writeValueAsString(device);
			Message message = session.createTextMessage(deviceJson);
			System.out.println("Send register request -> EPCM service");
			System.out.println(deviceJson);
			sender.send(message);

			// register EPCM Request Listener
			receiver = session.createConsumer(EpcmServiceTopic, String.format("device='%s'", device.getMacAddress()));
			System.out.println("start request listener...");
			EpcmMessageListener listener = new EpcmMessageListener();
			listener.addProcessor(ReqType.POLL, new PollProcessor(sender, session, deviceServiceQueue, data));
			receiver.setMessageListener(listener);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void start() throws JMSException {
		System.out.println("Device started");
		Scanner scanner = new Scanner(System.in);
		int c = -1;
		do {
			menu();
			c = scanner.nextInt();
			switch (c) {
			case 0:
				break;
			default: {
				System.out.println(String.format("%s isn't supported", c));
			}
				break;
			}
		} while (c != 0);
		scanner.close();
	}

	private void menu() {
		System.out.println("******************************************");
		System.out.println("* 1. make report                         *");
		System.out.println("* 0. exit                                *");
		System.out.println("******************************************");
		System.out.print("choose:");
	}

	private void destroy() throws JMSException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (device != null) {
				device.setStatus(false);
				device.setIpAddress("0.0.0.0");
				String json;
				json = mapper.writeValueAsString(device);
				System.out.println("send shutdown report -> EPCM");
				sender.send(session.createTextMessage(json));
				System.out.println(json);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Shutting down...");
			if (sender != null)
				sender.close();
			if (receiver != null)
				receiver.close();
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
			System.out.println("Bye!");
		}
	}
}
