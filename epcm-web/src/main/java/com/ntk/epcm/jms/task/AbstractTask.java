package com.ntk.epcm.jms.task;

import org.springframework.jms.core.JmsTemplate;

import com.ntk.epcm.jms.callback.ICallback;

public abstract class AbstractTask implements ITask {
	protected ICallback callback;
	protected JmsTemplate jmsTemplate;
	protected String macAddress;
	protected boolean isCancelled = false;
	
	public AbstractTask(ICallback callback, org.springframework.jms.core.JmsTemplate jmsTemplate, String macAddress) {
		this.callback = callback;
		this.jmsTemplate = jmsTemplate;
		this.macAddress = macAddress;
	}
	
	@Override
	public void cancel() {
		isCancelled = true;
	}
	
	@Override
	public String getMacAddress(){
		return macAddress;
	}
}
