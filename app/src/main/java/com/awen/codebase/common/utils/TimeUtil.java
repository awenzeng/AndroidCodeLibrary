package com.awen.codebase.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtil {
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm";
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_MONTH_DAY_TIME = "MM dd HH:mm";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static final int YEAR = 365 * 24 * 60 * 60;// 年
	private static final int MONTH = 30 * 24 * 60 * 60;// 月
	private static final int DAY = 24 * 60 * 60;// 天
	private static final int HOUR = 60 * 60;// 小时
	private static final int MINUTE = 60;// 分钟

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + " years ago";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + " months ago";
		} else if (timeGap > DAY) {// 1天以上
			timeStr = timeGap / DAY + " days ago";
		} else if (timeGap > HOUR) {// 1小时-24小时
			timeStr = timeGap / HOUR + " hours ago";
		} else if (timeGap > MINUTE) {// 1分钟-59分钟
			timeStr = timeGap / MINUTE + " mins ago";
		} else {// 1秒钟-59秒钟
			timeStr = "just now";
		}
		return timeStr;
	}
	
	
	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestampForToday(long timestamp) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + " years ago";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + " months ago";
		} else if (timeGap > DAY) {// 1天以上
			timeStr = timeGap / DAY + " days ago";
			if((timeGap / DAY) == 1) {
				timeStr = "Yesterday";
			}
		} else if (timeGap > HOUR) {// 1小时-24小时
			String service = getFormatTimeFromTimestamp(timestamp, FORMAT_DATE);
			String loaction = getFormatTimeFromTimestamp(Calendar.getInstance().getTimeInMillis(), FORMAT_DATE);
			if(service.equals(loaction)) {
				timeStr = "Today";
			} else {
				timeStr = "Yesterday";
			}
			//timeStr = timeGap / HOUR + " hours ago";
		} else if (timeGap > MINUTE) {// 1分钟-59分钟
			timeStr = "Today";
		} else {// 1秒钟-59秒钟
			timeStr = "Today";
		}
		return timeStr;
	}
	
	/**
	 * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @param format
	 *            指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getFormatTimeFromTimestamp(long timestamp,
			String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE);
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int year = Integer.valueOf(sdf.format(new Date(timestamp))
					.substring(0, 4));
			if (currentYear == year) {// 如果为今年则不显示年份
				sdf.applyPattern(FORMAT_MONTH_DAY_TIME);
			} else {
				sdf.applyPattern(FORMAT_DATE_TIME);
			}
		} else {
			sdf.applyPattern(format);
		}
		Date date = new Date(timestamp);
		return sdf.format(date);
	}

	
	public static String getTimeForHHMM(long time) {
		Calendar c = Calendar.getInstance();
		Date date = new Date(time);
		c.setTime(date);	
		String s = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
		return s;
	}
	
	
	/**
	 * 根据时间戳获取时间字符串，并根据指定的时间分割数partionSeconds来自动判断返回描述性时间还是指定格式的时间
	 * 
	 * @param timestamp
	 *            时间戳 单位是毫秒
	 * @param partionSeconds
	 *            时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
	 * @param format
	 * @return
	 */
	public static String getMixTimeFromTimestamp(long timestamp,
			long partionSeconds, String format) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		if (timeGap <= partionSeconds) {
			return getDescriptionTimeFromTimestamp(timestamp);
		} else {
			return getFormatTimeFromTimestamp(timestamp, format);
		}
	}

	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(new Date());
	}

	/**
	 * 将日期字符串以指定格式转换为ms
	 * 
	 * @param time
	 *            日期字符串
	 * @param format
	 *            指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM:SS"
	 * @return
	 */
	public static long getTimeFromString(String timeStr, String format) {
		if(timeStr == null || timeStr.length()==0) {
			return 0;
		}
		try {
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
			sdf.applyPattern(FORMAT_DATE_TIME);
			long time = sdf.parse(timeStr).getTime();
			GregorianCalendar gc = new GregorianCalendar(Locale.US);
			gc.setTimeInMillis(time);
			gc.setTimeZone(TimeZone.getDefault());
			return gc.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 将Date以指定格式转换为日期时间字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getStringFromTime(Date time, String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(time);
	}
}
