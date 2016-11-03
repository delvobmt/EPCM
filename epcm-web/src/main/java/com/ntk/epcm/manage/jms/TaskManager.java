package com.ntk.epcm.manage.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskManager {
	private TaskManager taskManager;
	private Map<IControlUnit, IPollTask> taskMap = new HashMap<>();

	private TaskManager() {
	}

	public TaskManager instance() {
		if (taskManager == null) {
			synchronized (taskManager) {
				if (taskManager == null)
					taskManager = new TaskManager();
			}
		}
		return taskManager;
	}
	
	public void putTask(IPollTask task) {
		UUID.randomUUID().toString();
	}

}
