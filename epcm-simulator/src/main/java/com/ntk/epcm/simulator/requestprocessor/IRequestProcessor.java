package com.ntk.epcm.simulator.requestprocessor;

import javax.jms.TextMessage;

public interface IRequestProcessor {
	
	void process(TextMessage message);
}
