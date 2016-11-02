package com.ntk.epcm.model;

import java.sql.Date;

public class DeviceBuilder {

	int device_id;
	String model;
	String version;
	String macAddress;
	String ipAddress;
	long consumeNumber;
	long oldNumber;
	boolean status;
	String location;
	Date lastUpdate;

	public DeviceBuilder cloneFrom(Device device) {
		this.device_id = device.device_id;
		this.model = device.model;
		this.version = device.version;
		this.macAddress = device.macAddress;
		this.ipAddress = device.getIpAddress();
		this.consumeNumber = device.consumeNumber;
		this.oldNumber = device.oldNumber;
		this.status = device.status;
		this.location = device.location;
		this.lastUpdate = device.lastUpdate;
		return this;
	}

	public DeviceBuilder device_id(int device_id) {
		this.device_id = device_id;
		return this;
	}

	public DeviceBuilder model(String model) {
		this.model = model;
		return this;
	}

	public DeviceBuilder version(String version) {
		this.version = version;
		return this;
	}

	public DeviceBuilder macAddress(String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	public DeviceBuilder consumeNumber(long consumeNumber) {
		this.consumeNumber = consumeNumber;
		return this;
	}

	public DeviceBuilder oldNumber(long oldNumber) {
		this.oldNumber = oldNumber;
		return this;
	}

	public DeviceBuilder status(boolean status) {
		this.status = status;
		return this;
	}

	public DeviceBuilder location(String location) {
		this.location = location;
		return this;
	}

	public DeviceBuilder lastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public Device build() {
		return new Device(device_id, model, version, macAddress, ipAddress, consumeNumber, oldNumber, status, location,
				lastUpdate);
	}

}
