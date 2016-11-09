package com.ntk.epcm.model;

public class DeviceConfig {
	private int deviceConfig_id;
	private Device device;
	private int limitedConsume;
	private int alarmConsume;

	public int getDeviceConfig_id() {
		return deviceConfig_id;
	}

	public void setDeviceConfig_id(int deviceConfig_id) {
		this.deviceConfig_id = deviceConfig_id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public int getLimitedConsume() {
		return limitedConsume;
	}

	public void setLimitedConsume(int limitedConsume) {
		this.limitedConsume = limitedConsume;
	}

	public int getAlarmConsume() {
		return alarmConsume;
	}

	public void setAlarmConsume(int alarmConsume) {
		this.alarmConsume = alarmConsume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + deviceConfig_id;
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
		DeviceConfig other = (DeviceConfig) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (deviceConfig_id != other.deviceConfig_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceConfig [deviceConfig_id=" + deviceConfig_id + ", device=" + device + ", limitedConsume="
				+ limitedConsume + ", alarmConsume=" + alarmConsume + "]";
	}

}
