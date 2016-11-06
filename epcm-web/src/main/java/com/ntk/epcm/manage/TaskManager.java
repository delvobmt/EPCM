package com.ntk.epcm.manage;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ntk.epcm.jms.task.ITask;

public class TaskManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);
	
	private static TaskManager taskManager = new TaskManager();
	private List<ITask> pollingList = new LinkedList<>();
	
	ThreadPoolExecutor taskExecutor;
	
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();

	private TaskManager() {
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		int maximumPoolSize = corePoolSize*2;
		long keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		taskExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	public static TaskManager instance() {
		return taskManager;
	}
	
	public void put(ITask task) {
		taskExecutor.execute(task);
		pollingList.add(task);
	}

	public void cancel(String mac){
		Optional<ITask> task = pollingList.stream().filter(t->t.getMacAddress().equals(mac)).findAny();
		try {
			ITask realTask = task.get();
			realTask.cancel();
			pollingList.remove(realTask);
		} catch (NoSuchElementException e) {
			LOGGER.error("no task with mac {}, ignore cancel request", mac);
		}
	}
	
	public void remove(ITask task){
		pollingList.remove(task);
	}

	public List<String> getPollingList() {
		return pollingList.stream().map(task->task.getMacAddress()).collect(Collectors.toList());
	}
}
