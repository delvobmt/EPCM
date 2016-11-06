package com.ntk.epcm.jms.callback;

import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.manage.TaskManager;
import com.ntk.epcm.manage.TaskReporter;
import com.ntk.epcm.manage.jms.EpcmResponseObject;

public class PollCallback implements ICallback {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollCallback.class);
	
	private TaskReporter reporter;
	
	public PollCallback(TaskReporter reporter) {
		this.reporter = reporter;
		reporter.increase();
	}

	@Override
	public void processMessage(TextMessage message){
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = message.getText();
			LOGGER.debug("recive repsonse from device: {}", json);
			EpcmResponseObject responseObject = mapper.readValue(json, EpcmResponseObject.class);
			pollDone(responseObject);
		} catch (Exception e) {
			LOGGER.error("error while proccess reponse message from device",e);
		}
	}
	

	@Override
	public void timeOut(String macAddress) {
		reporter.disconnect(macAddress);
	}
	
	private void pollDone(EpcmResponseObject responseObject) {
		//TODO process result
		String macAddress = responseObject.getMacAddress();
		if(responseObject.isSuccess()){
			reporter.success(macAddress);
		}else{
			reporter.fail(macAddress);
		}
	}
}
