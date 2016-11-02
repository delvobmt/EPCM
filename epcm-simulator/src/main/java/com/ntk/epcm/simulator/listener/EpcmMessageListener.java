package com.ntk.epcm.simulator.listener;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.simulator.processor.IRequestProcessor;

public class EpcmMessageListener implements MessageListener{
	
	private Map<String, IRequestProcessor> processorRegistry;

	public EpcmMessageListener() {
		processorRegistry = new HashMap<String, IRequestProcessor>();
	}

	@Override
	public void onMessage(Message message) {
		try {
			String requestType = message.getStringProperty(EpcmConstant.REQ_TYPE_KEY);
			processorRegistry.get(requestType).process(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void addProcessor(ReqType reqType, IRequestProcessor processor){
		processorRegistry.put(reqType.toString(), processor);
	}

}
