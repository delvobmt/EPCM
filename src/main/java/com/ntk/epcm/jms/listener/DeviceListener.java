package com.ntk.epcm.jms.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class DeviceListener implements MessageListener{

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("DeviceRegisterQueue");
		MessageProducer producer = session.createProducer(destination);
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new DeviceListener());
		
		
	}

	@Override
	public void onMessage(Message message) {
		
	}
}
