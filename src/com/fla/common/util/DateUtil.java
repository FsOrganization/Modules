package com.fla.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
	
	private static String format = "yyyy-MM-dd'T'HH:mm:ss";
	private static String formatNohms = "yyyy-MM-dd";
    private static SimpleDateFormat sdf = new SimpleDateFormat(format);
    private static SimpleDateFormat sdfNohms = new SimpleDateFormat(formatNohms);  
	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String formatDateToString(Date d){
		String value = "";
        if (d == null) {
            return value;
        } else if (d instanceof Date)
            return sdf.format((Date) d);
        else {
            return value.toString();
        }
	}
	public static Date strToDate(String s,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);  
		try 
		{
			return  sdf.parse( " 2002-10-8 15：30：22 ");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToStr(Date d,String format){
		String value = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);  
        if (d == null) {
            return value;
        } else if (d instanceof Date)
            return sdf.format((Date) d);
        else {
            return value.toString();
        }
	}
	
	public static String dateToNohmsStr(Date d, String format) {
		String value = "";
		if (d == null) {
			return value;
		} else if (d instanceof Date) {
			return sdfNohms.format(d);
		} else {
			return value.toString();
		}
	}

	/**
	 * 日期偏移
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date dateAdd(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	public static String dateAddToString(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return sdfNohms.format(cal.getTime());
	}

}
