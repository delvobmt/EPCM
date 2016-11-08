package com.ntk.epcm.jms.task;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.manage.jms.EpcmRequestObject;

public class UpdateTask extends AbstractTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTask.class);
	private Object data;
	private DataType dataType;

	public UpdateTask(JmsTemplate jmsTemplate, String macAddress, Object data, DataType dataType) {
		super(null, jmsTemplate, macAddress);
		this.data = data;
		this.dataType = dataType;
	}

	@Override
	public void run() {
		try{
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMapper mapper = new ObjectMapper();
				EpcmRequestObject requestObject = new EpcmRequestObject();
				requestObject.setData(data);
				requestObject.setDataType(dataType);
				String json;
				try {
					json = mapper.writeValueAsString(requestObject);
					LOGGER.debug("send Update request to-> device: {}",json);
					TextMessage message = session.createTextMessage(json);
					message.setStringProperty(EpcmConstant.DEVICE_MAC, macAddress);
					message.setStringProperty(EpcmConstant.REQ_TYPE_KEY, ReqType.UPDATE.toString());
					return message;
				} catch (JsonProcessingException e) {
					LOGGER.error("ERROR while create message ",e);
				}
				return null;
			}
		});
		}catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}

}
