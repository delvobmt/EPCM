package com.ntk.epcm.manage.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.model.Device;

public class PollTask implements IPollTask {
	public static final Logger LOGGER = LoggerFactory.getLogger(PollTask.class);

	IPollCallback callback;
	JmsTemplate JmsTemplate;
	Device device;
	String uuid;

	@Override
	public void run() {
		try {
			TextMessage message = (TextMessage) JmsTemplate.sendAndReceive(new MessageCreator() {

				@SuppressWarnings("finally")
				@Override
				public Message createMessage(Session session) throws JMSException {
					EpcmRequestObject reqObject = new EpcmRequestObject(device, ReqType.POLL, uuid);
					ObjectMapper mapper = new ObjectMapper();
					String json = "";
					try {
						json = mapper.writeValueAsString(reqObject);
					} catch (JsonProcessingException e) {
						LOGGER.error(e.getMessage(), e);
					} finally {
						return session.createTextMessage(json);
					}
				}
			});

			if (message != null && !StringUtils.isEmpty(message.getText())) {
			}
		} catch (JMSException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}
}
