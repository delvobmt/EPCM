package com.ntk.epcm.model;

public class DeviceNotification {
	private int deviceNotification_id;
	private int device_id;
	private Device device;
	private String severity;
	private String description;

	public int getDeviceNotification_id() {
		return deviceNotification_id;
	}

	public void setDeviceNotification_id(int deviceNotification_id) {
		this.deviceNotification_id = deviceNotification_id;
	}

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + deviceNotification_id;
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
		DeviceNotification other = (DeviceNotification) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (deviceNotification_id != other.deviceNotification_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceNotification [deviceNotification_id=" + deviceNotification_id + ", device=" + device
				+ ", severity=" + severity + ", description=" + description + "]";
	}
}
