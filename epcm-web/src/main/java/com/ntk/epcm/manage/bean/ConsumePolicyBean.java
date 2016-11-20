package com.ntk.epcm.manage.bean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.model.ConsumePolicy;
import com.ntk.epcm.service.ConsumeGroupService;
import com.ntk.epcm.service.ConsumePolicyService;

@Component
public class ConsumePolicyBean implements Observer, InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumePolicyBean.class);

	@Inject
	ConsumePolicyService consumePolicyService;

	@Inject
	ConsumeGroupService consumeGroupService;

	List<ConsumePolicy> list;
	List<ConsumePolicy> listSelected;
	List<ConsumePolicy> listFiltered;
	ConsumePolicy consumePolicy;

	@Override
	public void afterPropertiesSet() throws Exception {
		consumePolicyService.addObserver(this);
		loadPolicies();
	}

	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("Database is changed, reload data!");
		loadPolicies();
	}

	public void refresh() {
		loadPolicies();
	}

	private void loadPolicies() {
		list = consumePolicyService.findAll();
	}

	public void save(){
		ConsumeGroup group = consumePolicy.getGroup();
		//check whenever group is a new group
		if(group.getConsumeGroup_id()==0){
			consumeGroupService.insert(group);
		}
		//check whenever policy is a new policy
		if(consumePolicy.getConsumePolicy_id()==0) {
			consumePolicyService.insert(consumePolicy);
		}else{
			consumePolicyService.save(consumePolicy);
		}
	}
	
	public void delete() {
		consumePolicyService.remove(listSelected);
	}
	
	public List<ConsumePolicy> getList() {
		return list;
	}

	public void setList(List<ConsumePolicy> list) {
		this.list = list;
	}

	public List<ConsumePolicy> getListSelected() {
		return listSelected;
	}

	public void setListSelected(List<ConsumePolicy> listSelected) {
		this.listSelected = listSelected;
	}

	public List<ConsumePolicy> getListFiltered() {
		return listFiltered;
	}

	public void setListFiltered(List<ConsumePolicy> listFiltered) {
		this.listFiltered = listFiltered;
	}

	public ConsumePolicy getNewConsumePolicy() {
		ConsumePolicy policy = new ConsumePolicy();
		policy.setGroup(new ConsumeGroup());
		return policy;
	}

	public ConsumePolicy getConsumePolicy() {
		return consumePolicy;
	}

	public void setConsumePolicy(ConsumePolicy consumePolicy) {
		this.consumePolicy = consumePolicy;
	}
}
