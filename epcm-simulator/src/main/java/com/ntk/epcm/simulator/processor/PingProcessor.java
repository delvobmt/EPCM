package com.ntk.epcm.simulator.processor;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

public class PingProcessor implements IRequestProcessor {

	private MessageProducer reportSender;

	public PingProcessor(MessageProducer reportSender) {
		this.reportSender = reportSender;
	}

	@Override
	public void process(Message message) {
		try {
			Destination replyTo = message.getJMSReplyTo();
			if (replyTo != null) {
				reportSender.send(replyTo, message);
			} else {
				reportSender.send(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
