package com.ntk.epcm.simulator.processor;

import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.manage.jms.EpcmRequestObject;

public class UpdateProcessor implements IRequestProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollProcessor.class);
	
	@Override
	public void process(TextMessage message) {
		try {
			String json = message.getText();
			LOGGER.debug("receive message: {}", json);
			ObjectMapper mapper = new ObjectMapper();
			EpcmRequestObject requestObject = mapper.readValue(json, EpcmRequestObject.class);
		} catch (Exception e) {
			LOGGER.error("ERROR while process UPDATE request",e);
			e.printStackTrace();
		}

	}

}
