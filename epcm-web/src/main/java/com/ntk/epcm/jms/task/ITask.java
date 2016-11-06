package com.ntk.epcm.jms.task;

public interface ITask extends Runnable{
	String getMacAddress();
	void cancel();
}
