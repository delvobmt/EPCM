package com.ntk.epcm.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;
import com.ntk.epcm.simulator.dataprocessor.BasicInfoProcessor;
import com.ntk.epcm.simulator.dataprocessor.IDataProcessor;
import com.ntk.epcm.simulator.listener.EpcmMessageListener;
import com.ntk.epcm.simulator.requestprocessor.PollProcessor;
import com.ntk.epcm.simulator.requestprocessor.UpdateProcessor;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

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

		sender = session.createProducer(null);

		data = new DataGenerator();

		connection.start();

		try {
			device = data.getDevice();

			JmsSender.instance(sender, session, deviceServiceQueue, data).sendBasicInfo();

			// register EPCM Request Listener
			receiver = session.createConsumer(EpcmServiceTopic,
					String.format("%s='%s'", EpcmConstant.DEVICE_MAC, device.getMacAddress()));
			System.out.println("start request listener...");
			EpcmMessageListener listener = new EpcmMessageListener();
			
			PollProcessor pollProcessor = new PollProcessor(sender, session, deviceServiceQueue, data);
			listener.addProcessor(ReqType.POLL, pollProcessor);
			
			Map<DataType, IDataProcessor> dataRegistry = new HashMap<>();
			dataRegistry.put(DataType.BASICINFO, new BasicInfoProcessor(data));
			UpdateProcessor updateProcessor = new UpdateProcessor(dataRegistry);
			listener.addProcessor(ReqType.UPDATE, updateProcessor);
			
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
			JmsSender.instance(sender, session, deviceServiceQueue, data).sendOffDevice();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
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
