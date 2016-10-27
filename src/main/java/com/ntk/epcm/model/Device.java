package com.ntk.epcm.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.WRAPPER_OBJECT)
public class Device implements Serializable{
	private static final long serialVersionUID = -3629350960312361263L;

	int device_id;
	String model;
	String version;
	String macAddress;
	long consumeNumber;
	long oldNumber;
	boolean status;
	String location;
	Date lastUpdate;
	
	public Device(int device_id, String model, String version, String macAddress, long consumeNumber, long oldNumber,
			boolean status, String location, Date lastUpdate) {
		super();
		this.device_id = device_id;
		this.model = model;
		this.version = version;
		this.macAddress = macAddress;
		this.consumeNumber = consumeNumber;
		this.oldNumber = oldNumber;
		this.status = status;
		this.location = location;
		this.lastUpdate = lastUpdate;
	}
	
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public long getConsumeNumber() {
		return consumeNumber;
	}
	public void setConsumeNumber(long consumeNumber) {
		this.consumeNumber = consumeNumber;
	}
	public long getOldNumber() {
		return oldNumber;
	}
	public void setOldNumber(long oldNumber) {
		this.oldNumber = oldNumber;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + device_id;
		result = prime * result + ((macAddress == null) ? 0 : macAddress.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (device_id != other.device_id)
			return false;
		if (macAddress == null) {
			if (other.macAddress != null)
				return false;
		} else if (!macAddress.equals(other.macAddress))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Device [device_id=" + device_id + ", model=" + model + ", version=" + version + ", macAddress="
				+ macAddress + ", consumeNumber=" + consumeNumber + ", oldNumber=" + oldNumber + ", status=" + status
				+ ", location=" + location + ", lastUpdate=" + lastUpdate + "]";
	}
	
}
