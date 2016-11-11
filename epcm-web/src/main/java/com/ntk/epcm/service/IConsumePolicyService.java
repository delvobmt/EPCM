package com.ntk.epcm.service;

import com.ntk.epcm.model.ConsumePolicy;

public interface IConsumePolicyService {
	int insert(ConsumePolicy consumePolicy);
	boolean save(ConsumePolicy consumePolicy);
	boolean remove(ConsumePolicy consumePolicy);
	ConsumePolicy findById(int id);
	boolean needUdpate();
}
