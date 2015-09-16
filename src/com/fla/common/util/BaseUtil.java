package com.fla.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class BaseUtil {
	
	private static String format = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat(format);  
	public BaseUtil() {
	}

	/**
	 *  排除所有null ""
	 * @param obj
	 * @return
	 */
	public static final  boolean checkAllNull(Object obj){
		if (obj == null || obj.toString().trim().equals("") || obj.toString().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static final  String getStringDate(){
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static final  Date getDateFromString(String dateStr){
		Date date = null;
		try 
		{
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 生成在[min,max]之间的随机整数，
	 * @param max
	 * @param min
	 */
	public static int randomCount(int max,int min) { 
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

}
