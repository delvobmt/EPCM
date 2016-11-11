package com.ntk.epcm.manage.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
import com.ntk.epcm.service.IDeviceService;

@Component
public class DeviceBean implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceBean.class);

	@Inject
	IDeviceService deviceService;

	@Inject
	private JmsTemplate jmsTemplate;

	List<Device> list;
	List<Device> listSelected;
	Device selectedDevice = new Device();
	List<String> executeList = new LinkedList<>();

	@Inject
	TaskReporter reporter;
	int progress = 0;

	/**
	 * refresh device list. If there is no change, cache is returned.
	 * 
	 * @return
	 */
	public void refresh() {
		if (list == null || deviceService.needUpdate()) {
			LOGGER.debug("refresh device list from service");
			list = deviceService.findAll();
		} else {
			LOGGER.debug("refresh device list from cache");
		}
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
		//TODO device information is changed locally, then we need to send message to request device changes.
		TaskManager.instance().put(new UpdateTask(jmsTemplate, selectedDevice.getMacAddress(), selectedDevice, DataType.BASICINFO));
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, String.format("Saved %s", selectedDevice.getMacAddress()), ""));
	}
	
	public void delete() {
		deviceService.remove(listSelected);
		list.removeAll(listSelected);
		listSelected.clear();
	}

	public void cancel() {
		TaskManager taskManager = TaskManager.instance();
		executeList.forEach(mac-> taskManager.cancel(mac));
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

	@Override
	public void afterPropertiesSet() throws Exception {
		refresh();
	}

	public Device getSelectedDevice() {
		return selectedDevice;
	}

	public void setSelectedDevice(Device selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
}
