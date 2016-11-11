package com.ntk.epcm.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IConsumeGroupDAO;
import com.ntk.epcm.model.ConsumeGroup;

@Service
public class ConsumeGroupSevice implements IConsumeGroupSevice, IConsumeGroupDAO {

	@Inject
	IConsumeGroupDAO dao;
	
	private boolean needUpdate;
	
	@Override
	public int insert(com.ntk.epcm.model.ConsumeGroup consumeGroup) {
		int id = dao.insert(consumeGroup);
		needUpdate = needUpdate?needUpdate:id!=-1;
		return id;
	}

	@Override
	public boolean save(com.ntk.epcm.model.ConsumeGroup consumeGroup) {
		boolean success = dao.save(consumeGroup);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public boolean remove(com.ntk.epcm.model.ConsumeGroup consumeGroup) {
		boolean success = dao.remove(consumeGroup);
		needUpdate = needUpdate?needUpdate:success;
		return success;
	}

	@Override
	public ConsumeGroup findbyId(int id) {
		return dao.findbyId(id);
	}

	@Override
	public boolean needUpdate() {
		return needUpdate;
	}

}
