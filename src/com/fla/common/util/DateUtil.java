package com.fla.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
	
	private static String format = "yyyy-MM-dd'T'HH:mm:ss";  
    private static SimpleDateFormat sdf = new SimpleDateFormat(format);  
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

}
