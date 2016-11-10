package com.ntk.epcm.data;

import com.ntk.epcm.model.ConsumeGroup;

public interface IConsumeGroupDAO {
	int insert(ConsumeGroup consumeGroup);
	boolean save(ConsumeGroup consumeGroup);
	boolean remove(ConsumeGroup consumeGroup);
	
	ConsumeGroup findbyId(int id);
}
