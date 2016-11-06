package com.ntk.epcm.jms.callback;

import javax.jms.TextMessage;

public interface ICallback {
	void processMessage(TextMessage message);
	void timeOut(String macAddress);
}
