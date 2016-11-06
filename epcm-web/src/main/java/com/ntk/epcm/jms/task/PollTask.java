package com.ntk.epcm.jms.task;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.jms.callback.ICallback;
import com.ntk.epcm.manage.TaskManager;
import com.ntk.epcm.manage.jms.EpcmRequestObject;

public class PollTask extends AbstractTask {
	public static final Logger LOGGER = LoggerFactory.getLogger(PollTask.class);

	public PollTask(ICallback callback, org.springframework.jms.core.JmsTemplate jmsTemplate, String macAddress) {
		super(callback, jmsTemplate, macAddress);
	}

	@Override
	public void run() {
		if (isCancelled)
			return;
		TextMessage message = (TextMessage) jmsTemplate.sendAndReceive(new MessageCreator() {

			@SuppressWarnings("finally")
			@Override
			public Message createMessage(Session session) throws JMSException {
				EpcmRequestObject reqObject = new EpcmRequestObject(macAddress, ReqType.POLL);
				ObjectMapper mapper = new ObjectMapper();
				String json = "";
				try {
					json = mapper.writeValueAsString(reqObject);
				} catch (JsonProcessingException e) {
					LOGGER.error(e.getMessage(), e);
				} finally {
					TextMessage message = session.createTextMessage(json);
					message.setStringProperty(EpcmConstant.DEVICE_MAC, macAddress);
					message.setStringProperty(EpcmConstant.REQ_TYPE_KEY, ReqType.POLL.toString());
					return message;
				}
			}
		});
		if (isCancelled)
			return;
		if(message == null){
			LOGGER.debug("poll {} is timeout", macAddress);
			callback.timeOut(macAddress);
		}else{
			callback.processMessage(message);
		}
		TaskManager.instance().remove(this);
	}
}
