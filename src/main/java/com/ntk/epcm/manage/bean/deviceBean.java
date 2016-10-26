package com.ntk.epcm.manage.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.Device;

@ManagedBean
@Scope("request")
@Component
public class deviceBean {
	
	List<Device> list;

	public List<Device> getAll() {
		return list;
	}
}
