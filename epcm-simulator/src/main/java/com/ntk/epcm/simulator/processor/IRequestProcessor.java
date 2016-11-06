package com.ntk.epcm.simulator.processor;

import javax.jms.TextMessage;

public interface IRequestProcessor {
	
	void process(TextMessage message);
}
