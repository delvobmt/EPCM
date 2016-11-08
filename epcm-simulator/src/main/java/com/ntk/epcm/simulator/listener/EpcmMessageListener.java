package com.ntk.epcm.simulator.listener;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.simulator.requestprocessor.IRequestProcessor;

public class EpcmMessageListener implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(EpcmMessageListener.class);
	private Map<String, IRequestProcessor> processorRegistry;

	public EpcmMessageListener() {
		processorRegistry = new HashMap<String, IRequestProcessor>();
	}

	@Override
	public void onMessage(Message message) {
		LOGGER.debug("receive Message!");
		try {
			String requestType = message.getStringProperty(EpcmConstant.REQ_TYPE_KEY);
			IRequestProcessor processor = processorRegistry.get(requestType);
			if(processor!=null){
				processor.process((TextMessage) message);
			}else{
				LOGGER.error("there is no proccessor for requestType:{}",requestType);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void addProcessor(ReqType reqType, IRequestProcessor processor) {
		processorRegistry.put(reqType.toString(), processor);
	}

}
