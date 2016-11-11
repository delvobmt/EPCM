package com.ntk.epcm.service;

import com.ntk.epcm.model.ConsumeGroup;

public interface IConsumeGroupSevice {
	int insert(ConsumeGroup consumeGroup);
	boolean save(ConsumeGroup consumeGroup);
	boolean remove(ConsumeGroup consumeGroup);
	
	ConsumeGroup findbyId(int id);
	boolean needUpdate();
}
