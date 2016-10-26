package com.ntk.epcm.jms.listener;

import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class DeviceListener {

	public static void main(String[] args) {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
		
	}
}
