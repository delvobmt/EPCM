package com.ntk.epcm.manage.jms;

public interface IControlUnit {
	void cancel();
	
	void setTask(IPollTask task);
}
