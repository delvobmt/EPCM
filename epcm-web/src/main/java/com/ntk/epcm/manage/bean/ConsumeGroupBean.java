package com.ntk.epcm.manage.bean;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.service.ConsumeGroupService;

@Component
public class ConsumeGroupBean implements InitializingBean, Observer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeGroupBean.class);

	@Inject
	ConsumeGroupService consumeGroupService;

	List<ConsumeGroup> list;

	@Override
	public void update(Observable o, Object arg) {
		LOGGER.debug("data is changed, call load data");
		loadGroups();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		consumeGroupService.addObserver(this);
		loadGroups();
	}

	private void loadGroups() {
		list = consumeGroupService.findAll();
	}

	public List<ConsumeGroup> completeMethod(String text) {
		if(!StringUtils.isEmpty(text)){
			List<ConsumeGroup> result = list.stream().filter(p->p.getGroup().contains(text.trim())).collect(Collectors.toList());
			List<String> groupList = result.stream().map(ConsumeGroup::getGroup).collect(Collectors.toList());
			if(!groupList.contains(text.trim())){
				ConsumeGroup newGroup = new ConsumeGroup();
				newGroup.setGroup(text);
				result.add(0, newGroup);
			}
			return result;
		}else{
			return list.subList(0, list.size()>5?5:list.size()-1);
		}
	}
	
	public List<ConsumeGroup> getList() {
		return list;
	}

	public void setList(List<ConsumeGroup> list) {
		this.list = list;
	}
}
