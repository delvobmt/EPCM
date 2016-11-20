package com.ntk.epcm.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ntk.epcm.model.ConsumeGroup;
import com.ntk.epcm.service.ConsumeGroupService;

@Component
public class ConsumeGroupConverter implements Converter{

	@Inject
	ConsumeGroupService consumeGroupService;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent ui, String group) {
		ConsumeGroup g = null;
		if(!StringUtils.isEmpty(group)){
			g = consumeGroupService.findByName(group);
		}
		if(g==null){
			g = new ConsumeGroup();
			g.setGroup(group);
		}
		return g;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent ui, Object group) {
		return ((ConsumeGroup) group).getGroup();
	}

	
}
