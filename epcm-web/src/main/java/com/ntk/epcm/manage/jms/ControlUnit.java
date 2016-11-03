package com.ntk.epcm.manage.jms;

public class ControlUnit implements IControlUnit {

	private IPollTask task;

	@Override
	public void cancel() {
	}

	@Override
	public void setTask(IPollTask task) {
		this.task = task;
	}

}
