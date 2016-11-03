package com.ntk.epcm.manage.jms;

import com.ntk.epcm.constant.ReqType;
import com.ntk.epcm.model.Device;

public class EpcmRequestObject {
	Device device;
	ReqType reqType;
	String uuid;
	public EpcmRequestObject(Device device, ReqType reqType, String uuid) {
		this.device = device;
		this.reqType = reqType;
		this.uuid = uuid;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public ReqType getReqType() {
		return reqType;
	}
	public void setReqType(ReqType reqType) {
		this.reqType = reqType;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
