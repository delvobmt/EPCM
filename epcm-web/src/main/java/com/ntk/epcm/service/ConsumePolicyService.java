package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IConsumePolicyDAO;
import com.ntk.epcm.model.ConsumePolicy;

@Service
public class ConsumePolicyService implements IConsumePolicyService {

	@Inject
	IConsumePolicyDAO dao;
	private boolean needUpdate;
	
	@Override
	public int insert(ConsumePolicy consumePolicy) {
		int id = dao.insert(consumePolicy);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(ConsumePolicy consumePolicy) {
		boolean success = dao.save(consumePolicy);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public boolean remove(ConsumePolicy consumePolicy) {
		boolean success = dao.remove(consumePolicy);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public ConsumePolicy findById(int id) {
		return dao.findById(id);
	}

	@Override
	public boolean needUdpate() {
		return needUpdate;
	}

}
