package com.ntk.epcm.simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntk.epcm.model.Device;

public class DataGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataGenerator.class);
	
	public static final String JSON_FILE = "basicinfo.json";
	
	public DataGenerator() {
	}
	
	public Device getDevice() throws IOException{
		String workingDir = System.getProperty("user.dir");
		Path path = Paths.get(workingDir+File.separator+JSON_FILE);
		if(Files.exists(path)){
			ObjectMapper mapper = new ObjectMapper();
			byte[] src = Files.readAllBytes(path);
			Device device = mapper.readValue(src, Device.class);
			return device;
			
		}else{
			throw new FileNotFoundException(String.format("%s is not found in %s", JSON_FILE, workingDir));
		}
	}
}
