package com.fla.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.formula.functions.T;

import com.fla.common.base.SuperEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public final class BaseUtil {

	private static String format = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(format);

	public BaseUtil() {
	}

	/**
	 * 排除所有null ""
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean checkAllNull(Object obj) {
		if (obj == null || obj.toString().trim().equals("") || obj.toString().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	public static final String getStringDate() {
		Date date = new Date();
		return sdf.format(date);
	}

	public static final Date getDateFromString(String dateStr) {
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 生成在[min,max]之间的随机整数，
	 * 
	 * @param max
	 * @param min
	 */
	public static int randomCount(int max, int min) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

	/**
	 * 
	 * @param rowMap
	 * @param ja
	 */
	public static JSONArray ListToJSONArray(List<? extends SuperEntity> list) {
		JSONArray ja = new JSONArray();
		for (SuperEntity t : list) {
			JsonValueProcessor jv = new FlaJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, jv);
			String jsonString = JSONSerializer.toJSON(t, jsonConfig).toString();
			JSONObject json = JSONObject.fromObject(jsonString);
			ja.add(json);
		}
		return ja;
	}

}
