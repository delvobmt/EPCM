package com.ntk.epcm.simulator.requestprocessor;

import java.util.Map;

import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.constant.DataType;
import com.ntk.epcm.manage.jms.EpcmRequestObject;
import com.ntk.epcm.simulator.dataprocessor.IDataProcessor;

public class UpdateProcessor implements IRequestProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollProcessor.class);
	private Map<DataType, IDataProcessor> dataRegistry;
	
	public UpdateProcessor(Map<DataType, IDataProcessor> dataRegistry) {
		this.dataRegistry = dataRegistry;
	}
	
	@Override
	public void process(TextMessage message) {
		try {
			String json = message.getText();
			LOGGER.debug("receive message: {}", json);
			ObjectMapper mapper = new ObjectMapper();
			EpcmRequestObject requestObject = mapper.readValue(json, EpcmRequestObject.class);
			DataType dataType = requestObject.getDataType();
			Object data = requestObject.getData();
			IDataProcessor processor = dataRegistry.get(dataType);
			if(processor!=null){
				processor.proccess(data);
			}else{
				LOGGER.debug(String.format("unsupport %s", dataType));
			}
		} catch (Exception e) {
			LOGGER.error("ERROR while process UPDATE request",e);
			e.printStackTrace();
		}

	}

}
