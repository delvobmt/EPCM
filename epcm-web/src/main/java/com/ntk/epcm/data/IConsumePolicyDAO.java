package com.ntk.epcm.data;

import java.util.List;

import com.ntk.epcm.model.ConsumePolicy;

public interface IConsumePolicyDAO {
	int insert(ConsumePolicy consumePolicy);
	boolean save(ConsumePolicy consumePolicy);
	boolean remove(List<ConsumePolicy> consumePolicy);
	ConsumePolicy findById(int id);
	List<ConsumePolicy> findAll();
}
