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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;
import com.ntk.epcm.simulator.listener.EpcmMessageListener;
import com.ntk.epcm.simulator.processor.PingProcessor;

public class Main {

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Connection connection;
	private DataGenerator data;
	private Queue deviceReportQueue;
	private Queue deviceRegistrationQueue;
	private Topic epcmRequestTopic;
	private MessageProducer reportSender;
	private MessageProducer registrationSender;
	private MessageConsumer requestReceiver;

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
		deviceReportQueue = session.createQueue(EpcmConstant.DEVICE_REPORT_QUEUE);
		deviceRegistrationQueue = session.createQueue(EpcmConstant.DEVICE_REGISTRATION_QUEUE);
		epcmRequestTopic = session.createTopic(EpcmConstant.EPCM_REQUEST_TOPIC);
		
		registrationSender = session.createProducer(deviceRegistrationQueue);
		reportSender = session.createProducer(deviceReportQueue);
		
		data = new DataGenerator();
		
		connection.start();
		
		try {
			Device device = data.getDevice();

			// send register request to epcm service
			ObjectMapper mapper = new ObjectMapper();
			String deviceJson = mapper.writeValueAsString(device);
			Message message = session.createTextMessage(deviceJson);
			System.out.println("Send register request -> EPCM service");
			System.out.println(deviceJson);
			registrationSender.send(message);
			
			// register EPCM Request Listener
			requestReceiver = session.createConsumer(epcmRequestTopic, String.format("device='%s'", device.getMacAddress()));
			System.out.println("start request listener...");
			EpcmMessageListener listener = new EpcmMessageListener();
			listener.addProcessor(ReqType.PING, new PingProcessor(reportSender));
			requestReceiver.setMessageListener(listener);
			
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
				System.out.println("Bye!");
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
		System.out.println("******************************************");
		System.out.println("* 1. make report                         *");
		System.out.println("* 0. exit                                *");
		System.out.println("******************************************");
		System.out.print("choose:");
	}

	private void destroy() throws JMSException {
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}
}
