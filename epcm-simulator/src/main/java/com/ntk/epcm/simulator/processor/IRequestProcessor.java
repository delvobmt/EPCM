package com.ntk.epcm.simulator.processor;

import javax.jms.Message;
import javax.jms.MessageProducer;

import com.ntk.epcm.constant.ReqType;

public interface IRequestProcessor {
	
	void process(Message message);
}
