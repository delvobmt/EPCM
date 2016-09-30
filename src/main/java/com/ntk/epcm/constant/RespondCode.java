package com.ntk.epcm.constant;

public enum RespondCode {
	FAIL(0), SUCCES(1),ERROR(2), INACTIVE(3);
	int value;
	RespondCode(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
