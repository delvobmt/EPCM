package com.ntk.epcm.manage.jms;

import com.ntk.epcm.constant.DataType;

public class EpcmRequestObject {
	Object data;
	DataType dataType;
	
	public EpcmRequestObject() {
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

}
