package com.ntk.epcm.service;

import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ntk.epcm.data.IConsumePolicyDAO;
import com.ntk.epcm.model.ConsumePolicy;

@Service
public class ConsumePolicyService extends Observable {

	@Inject
	IConsumePolicyDAO dao;

	public int insert(ConsumePolicy consumePolicy) {
		int id = dao.insert(consumePolicy);
		if (id != -1) {
			setChanged();
			notifyObservers();
		}
		return id;
	}

	public boolean save(ConsumePolicy consumePolicy) {
		boolean success = dao.save(consumePolicy);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public boolean remove(List<ConsumePolicy> consumePolicy) {
		boolean success = dao.remove(consumePolicy);
		if (success) {
			setChanged();
			notifyObservers();
		}
		return success;
	}

	public ConsumePolicy findById(int id) {
		return dao.findById(id);
	}

	public List<ConsumePolicy> findAll() {
		return dao.findAll();
	}

}
