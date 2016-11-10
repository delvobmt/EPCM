package com.ntk.epcm.data;

import com.ntk.epcm.model.ConsumePolicy;

public interface IConsumePolicyDAO {
	int insert(ConsumePolicy consumePolicy);
	boolean save(ConsumePolicy consumePolicy);
	boolean remove(ConsumePolicy consumePolicy);
	ConsumePolicy findById(int id);
}
