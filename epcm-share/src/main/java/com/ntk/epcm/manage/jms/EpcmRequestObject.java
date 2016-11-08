package com.ntk.epcm.manage.jms;

import com.ntk.epcm.constant.ReqType;

public class EpcmRequestObject {
	String macAddress;
	ReqType reqType;
	Object data;
	
	public EpcmRequestObject() {
	}

	public EpcmRequestObject(String macAddress, ReqType reqType) {
		this.macAddress = macAddress;
		this.reqType = reqType;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public ReqType getReqType() {
		return reqType;
	}

	public void setReqType(ReqType reqType) {
		this.reqType = reqType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
