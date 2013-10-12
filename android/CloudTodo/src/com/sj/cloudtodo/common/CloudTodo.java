package com.sj.cloudtodo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CloudTodo {
	public static final String dateFormat = "yyyy-MM-dd";
	public static final String oldDateFormat = "yyyy-MM-dd HH:mm";
	public static final String tag = "CloudTodo";
	
	public static String dateToString ( Date date ) {
		
		/*
		 * Converts a Date object to string of @dateFormat
		 */
		if ( date == null ) return null;
		
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String strDate  = format.format(date);
		
		return strDate;
	}
	
	public static Date stringToDate(String dateString) throws Exception {
		
		/*
		 * Converts a date string in @dateFormat to a Date object
		 */
		
		if ( "".equals(dateString) || dateString==null || "null".equalsIgnoreCase(dateString)) return null;
		
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = format.parse(dateString);
		
		return date;
	}
}
