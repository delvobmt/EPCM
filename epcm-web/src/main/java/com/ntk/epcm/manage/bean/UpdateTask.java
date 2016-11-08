package com.ntk.epcm.manage.bean;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.jms.task.AbstractTask;
import com.ntk.epcm.manage.jms.EpcmRequestObject;

public class UpdateTask extends AbstractTask {
	
	public UpdateTask(JmsTemplate jmsTemplate, String macAddress) {
		super(null, jmsTemplate, macAddress);
	}

	@Override
	public void run() {
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMapper mapper = new ObjectMapper();
				EpcmRequestObject requestObject = new EpcmRequestObject(macAddress, ReqType.UPDATE);
//				requestObject.setData(data);
				Message message = session.createTextMessage();
				return message;
			}
		});

	}

}
