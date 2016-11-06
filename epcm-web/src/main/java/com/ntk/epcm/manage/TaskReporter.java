package com.ntk.epcm.manage;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.EpcmConstant;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.service.DeviceService;

@Component
public class TaskReporter {
	@Inject
	DeviceService deviceService;
	
	AtomicInteger totalTask = new AtomicInteger();
	AtomicInteger doneTask = new AtomicInteger();

	public void success(String macAddress) {
		doneTask.incrementAndGet();
	}

	public void fail(String macAddress) {
		doneTask.incrementAndGet();
	}
	
	public void disconnect(String macAddress) {
		Device device = deviceService.findDeviceByMacAddress(macAddress);
		if(device!=null){
			device.setIpAddress(EpcmConstant.NO_IP_ADDRESS);
			device.setStatus(false);
			device.setLastUpdate(new Date(System.currentTimeMillis()));
			deviceService.save(device);
		}
		fail(macAddress);
	}

	public void increase() {
		totalTask.incrementAndGet();
	}

	public int getProgress() {
		int done = doneTask.get();
		int total = totalTask.get();
		return total > 0 ? (done * 100) / total : 0;
	}

	public void reset() {
		doneTask.set(0);
		totalTask.set(0);
	}

}
