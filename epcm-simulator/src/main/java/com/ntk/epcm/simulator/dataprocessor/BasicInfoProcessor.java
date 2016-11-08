package com.ntk.epcm.simulator.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.simulator.data.DataGenerator;

public class BasicInfoProcessor implements IDataProcessor{
	
	private DataGenerator data;

	public BasicInfoProcessor(DataGenerator data) {
		this.data = data;
	}

	@Override
	public void proccess(Object objData) {
		ObjectMapper mapper = new ObjectMapper();
		Device device = mapper.convertValue(objData, Device.class);
		data.save(device);
	}
	
}
