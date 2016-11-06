package com.ntk.epcm.manage.jms;

import com.ntk.epcm.constant.DataType;

public class EpcmDataMessage {
	private DataType dataType;
	private String macAddress;
	private Object data;
	
	public EpcmDataMessage() {
	}

	public EpcmDataMessage(DataType dataType, String macAddress, Object data) {
		this.dataType = dataType;
		this.macAddress = macAddress;
		this.data = data;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
