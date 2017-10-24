/**
 * 
 */
package com.jetsen.pack.optram.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author <a href="mailto:zhangwei@jetsen.cn">张维</a>
 * @version 1.0.0
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 * 修订日期                 修订人            描述<br/>
 * 2013-12-19       zw          创建<br/>
 */
public class DateUtil {

	 /**
	  * 默认日期格式
	  */
	 private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";//Timestamp format must be yyyy-mm-dd hh:mm:ss[.fffffffff]

	 /**
	  * 默认构造函数
	  */
	 private DateUtil() {
	 }

	 /**
	  * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	  * @param str 字符串
	  * @param format 日期格式
	  * @return 日期
	  * @throws ParseException
	  */
	 public static Date str2Date(String str, String format){
	  if (null == str || "".equals(str)) {
	   return null;
	  }
	  // 如果没有指定字符串转换的格式，则用默认格式进行转换
	  if (null == format || "".equals(format)) {
	   format = DEFAULT_FORMAT;
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat(format);
	  Date date = null;
	   try {
	    date = sdf.parse(str);
	    return date;
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	  return null;
	 }

	 /** 日期转换为字符串
	  * @param date 日期
	  * @param format 日期格式
	  * @return 字符串
	  */
	 public static String date2Str(Date date, String format) {
	  if (null == date) {
	   return null;
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat(format);
	  return sdf.format(date);
	 }

	 /**
	  * 时间戳转换为字符串
	  * @param time
	  * @return
	  */
	 public static String timestamp2Str(Timestamp time) {
	  Date date = null;
	  if(null != time){
	   date = new Date(time.getTime());
	  }
	  return date2Str(date, DEFAULT_FORMAT);
	 }
	 
	 /** 
	     * 获得指定日期的一天
	     * 负数为前几天
	     * 正数为后几天
	     * @param specifiedDay 
	     * @return 
	     */  
	    public static String getBeforeOrAfterDay(int after) {  
	        Calendar c = Calendar.getInstance();  
	        Date date = new Date();  
	        c.setTime(date);  
	        int day = c.get(Calendar.DATE);  
	        c.set(Calendar.DATE, day + after);  
	        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());  
	        return dayAfter;  
	    }

	/**
	 * 获得指定日期的一天
	 * 负数为前几天
	 * 正数为后几天
	 * @param date
	 * @param after
	 * @return
	 */
	public static String getBeforeOrAfterDay(Date date, int after, String pattern) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + after);
        if (null == pattern || "".equals(pattern)) {
            pattern = "yyyy-MM-dd";
        }
		String dayAfter = new SimpleDateFormat(pattern).format(c.getTime());
		return dayAfter;
	}



	/** 字符串转换时间戳
	  * @param str
	  * @return
	  */
	 public static Timestamp str2Timestamp(String str) {
	  Date date = str2Date(str, DEFAULT_FORMAT);
	  return new Timestamp(date.getTime());
	 }
	 
	 public static void main(String[] args) throws Exception {
	  System.out.println(date2Str(new Date(), "yyyy-mm-dd hh:mm:ss"));
//	  String tm = "2011-01-01 10:00:00";
//	  Timestamp tstamp = str2Timestamp(tm);
//	  System.out.println(tstamp);
//	  System.out.println(timestamp2Str(null));
	 }

}
