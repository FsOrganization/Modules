package com.fla.common.controller.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesConfig {
	
	private static Properties prop = new Properties();

	public PropertiesConfig() {
	}

	public static String getPropertiesByKey(String key) {
		InputStream in = null;
		try 
		{
			if (prop.size() == 0) {
				in = PropertiesConfig.class.getResourceAsStream("/com/fla/common/controller/Config/systemConfig.properties");
				prop.load(in); //加载属性列表
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try 
			{
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Object obj =  prop.getProperty(key);
		if (obj != null) {
			return obj.toString();
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		Object ff = PropertiesConfig.getPropertiesByKey("system.ip");
		System.out.println(ff.toString());
	}
	 
}
