package com.ntk.epcm.simulator.processor;

import java.io.IOException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;

public class PollProcessor implements IRequestProcessor {

	private MessageProducer sender;
	private Session session;
	private Destination destination;
	private DataGenerator data;
	
	public PollProcessor(MessageProducer reportSender, Session session, Destination destination, DataGenerator data) {
		this.sender = reportSender;
		this.session = session;
		this.destination = destination;
		this.data = data;
	}

	@Override
	public void process(Message message) {
		try {
			Destination replyTo = message.getJMSReplyTo();
			Device device = data.getDevice();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(device);
			TextMessage reponseMessage = session.createTextMessage(json);
			System.out.println("send response for poll request -> EPCM");
			System.out.println(json);
			if (replyTo != null) {
				sender.send(replyTo, reponseMessage);
			} else if(destination !=null){
				sender.send(destination, reponseMessage);
			} else{
				sender.send(reponseMessage);
			}
		} catch (JMSException | IOException e) {
			e.printStackTrace();
		}
	}

}
