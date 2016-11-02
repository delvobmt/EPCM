package com.ntk.epcm.manage.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

public class DeviceRequester {
	
	private JmsTemplate jmsTemplate;

	@Autowired
	public DeviceRequester(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	
}
