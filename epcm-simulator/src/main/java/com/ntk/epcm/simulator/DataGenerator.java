package com.ntk.epcm.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;
import com.ntk.epcm.model.DeviceBuilder;

public class DataGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataGenerator.class);
	
	public static final String JSON_FILE = "basicinfo.json";
	
	public DataGenerator() {
		try {
			init();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	private void init() throws Exception {
		String workingDir = System.getProperty("user.dir");
		Path path = Paths.get(workingDir+File.separator+JSON_FILE);
		if(Files.exists(path)){
			ObjectMapper mapper = new ObjectMapper();
			byte[] src = Files.readAllBytes(path);
			LOGGER.debug("{}",src);
			Device build = new DeviceBuilder().status(true).build();
			String json = mapper.writeValueAsString(build);
			Device device = mapper.readValue(src, Device.class);
			LOGGER.debug("device {}",device);
		}else{
			throw new FileNotFoundException(String.format("%s is not found in %s", JSON_FILE, workingDir));
		}
	}
}
