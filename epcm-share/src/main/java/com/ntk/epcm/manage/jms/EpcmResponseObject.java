package com.ntk.epcm.manage.jms;

public class EpcmResponseObject {
	String macAddress;
	boolean success;

	public EpcmResponseObject() {
	}

	public EpcmResponseObject(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSucess(boolean success) {
		this.success = success;
	}
}
