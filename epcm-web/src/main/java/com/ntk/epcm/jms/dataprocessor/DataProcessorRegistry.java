package com.ntk.epcm.jms.dataprocessor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.DataType;

@Component
public class DataProcessorRegistry {
	private Map<DataType, IDataProcessor> registryMap = new HashMap<>();
	
	public void register(DataType dataType, IDataProcessor dataProcessor){
		registryMap.put(dataType, dataProcessor);
	}
	
	public IDataProcessor get(DataType dataType){
		return registryMap.get(dataType);
	}
}
