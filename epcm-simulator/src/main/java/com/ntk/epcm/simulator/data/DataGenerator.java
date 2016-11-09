package com.ntk.epcm.simulator.data
;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	
	public static final String WORKING_DIR = System.getProperty("user.dir");
	public static final String BASIC_INFO_FILE = "basicinfo.json";
	public static final Path BASIC_INFO_PATH = Paths.get(WORKING_DIR+File.separator+BASIC_INFO_FILE);
	
	public DataGenerator() {
	}
	
	public Device getDevice() throws IOException{
		if(Files.exists(BASIC_INFO_PATH)){
			ObjectMapper mapper = new ObjectMapper();
			byte[] src = Files.readAllBytes(BASIC_INFO_PATH);
			Device device = mapper.readValue(src, Device.class);
			return device;
			
		}else{
			throw new FileNotFoundException(String.format("%s is not found in %s", BASIC_INFO_FILE, WORKING_DIR));
		}
	}
	
	public void save(Device device){
		File file = BASIC_INFO_PATH.toFile();
		ObjectMapper mapper = new ObjectMapper();
		byte[] bytes;
		FileOutputStream fos = null;
		try {
			bytes = mapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(device);
			fos = new FileOutputStream(file, false);
			fos.write(bytes);
			fos.flush();
			LOGGER.debug("Save SUCCESSFULLY Basic Info");
		} catch (Exception e) {
			LOGGER.error("ERROR while writing basic info file",e);
			e.printStackTrace();
		}finally {
			if(fos!=null)
				try {
					fos.close();
				} catch (IOException e) {
					LOGGER.error("ERROR while close stream",e);
					e.printStackTrace();
				}
		}
	}
}
