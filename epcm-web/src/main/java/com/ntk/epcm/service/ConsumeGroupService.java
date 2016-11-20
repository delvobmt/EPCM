package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IConsumeGroupDAO;
import com.ntk.epcm.model.ConsumeGroup;

@Service
public class ConsumeGroupService extends Observable {

	@Inject
	IConsumeGroupDAO dao;

	public int insert(ConsumeGroup consumeGroup) {
		int id = dao.insert(consumeGroup);
		if (id != -1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	public boolean save(ConsumeGroup consumeGroup) {
		boolean success = dao.save(consumeGroup);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(ConsumeGroup consumeGroup) {
		boolean success = dao.remove(consumeGroup);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public ConsumeGroup findbyId(int id) {
		return dao.findbyId(id);
	}

	public List<ConsumeGroup> findAll() {
		return dao.findAll();
	}

	public ConsumeGroup findByName(String group) {
		return dao.findByName(group);
	}

}
