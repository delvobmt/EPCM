package com.ntk.epcm.manage.bean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.jms.callback.PollCallback;
import com.ntk.epcm.jms.task.PollTask;
import com.ntk.epcm.jms.task.UpdateTask;
import com.ntk.epcm.manage.TaskManager;
import com.ntk.epcm.manage.TaskReporter;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceBuilder;
import com.ntk.epcm.service.DeviceNotificationService;
import com.ntk.epcm.service.DeviceService;

@Component
public class DeviceBean implements InitializingBean, Observer {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceBean.class);

	@Inject
	DeviceService deviceService;
	
	@Inject
	DeviceNotificationService deviceNotificationService;

	@Inject
	private JmsTemplate jmsTemplate;

	List<Device> list;
	List<Device> listFiltered;
	List<Device> listSelected;
	Device selectedDevice = new DeviceBuilder().build();
	List<String> executeList = new LinkedList<>();

	@Inject
	TaskReporter reporter;
	int progress = 0;

	@Override
	public void afterPropertiesSet() throws Exception {
		deviceService.addObserver(this);
		deviceNotificationService.addObserver(this);
		list = deviceService.findAll();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("database changed, call refresh data");
		refresh();
	}

	public void refresh() {
		list = deviceService.findAll();
	}

	public void poll() {
		TaskManager taskManager = TaskManager.instance();
		List<String> pollingList = taskManager.getPollingList();

		List<String> conflictList = new ArrayList<>();
		listSelected.stream().filter(device -> {
			if (!pollingList.contains(device.getMacAddress()))
				return true;
			else {
				conflictList.add(device.getMacAddress());
				return false;
			}
		}).forEach(device -> {
			executeList.add(device.getMacAddress());
			taskManager.put(new PollTask(new PollCallback(reporter), jmsTemplate, device.getMacAddress()));
		});
		if (!conflictList.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning: conflict",
							String.format("%s is polling, your request to those are ignored", conflictList)));
		}
	}

	public void save() {
		deviceService.save(selectedDevice);
		// TODO device information is changed locally, then we need to send
		// message to request device changes.
		TaskManager.instance()
				.put(new UpdateTask(jmsTemplate, selectedDevice.getMacAddress(), selectedDevice, DataType.BASICINFO));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				String.format("Saved %s", selectedDevice.getMacAddress()), ""));
	}

	public void delete() {
		deviceService.remove(listSelected);
		list.removeAll(listSelected);
		listSelected.clear();
	}

	public void cancel() {
		TaskManager taskManager = TaskManager.instance();
		executeList.forEach(mac -> taskManager.cancel(mac));
		reporter.reset();
	}

	public void onPollComplete() {
		executeList.clear();
		listSelected.clear();
		reporter.reset();
		// TODO handle on poll complete event from UI
	}

	public List<Device> getList() {
		return list;
	}

	public void setListSelected(List<Device> listSelected) {
		this.listSelected = listSelected;
	}

	public List<Device> getListSelected() {
		return listSelected;
	}

	public int getProgress() {
		progress = reporter.getProgress();
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}

	public List<Device> getListFiltered() {
		return listFiltered;
	}

	public void setListFiltered(List<Device> listFiltered) {
		this.listFiltered = listFiltered;
	}
}
