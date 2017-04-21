package com.awen.codebase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static SimpleDateFormat dateFormaterWithoutMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat dateFormaterWithoutSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormaterTime = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat dateFormaterDateAndTime = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    

    
    public static String getYYYY_MM_DD(long time) {
        return format.format(time);
    }
    
    public static long getYYYY_MM_DD(String text) {
        try {
            return format.parse(text).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
    
    public static String getHHMMSS(long time){
    	long   hour   =   time / (60 * 60 * 1000); 
    	long   minute   =   (time - hour * 60 * 60 * 1000) / (60 * 1000); 
    	long   second   =   (time - hour * 60 * 60 * 1000 - minute * 60 * 1000)/1000; 
    	if(second >= 60) 
    	{ 
    	    second = second % 60; 
    	    minute += second / 60; 
    	} 
    	
    	if(minute >= 60) 
    	{ 
    	    minute = minute   %60; 
    	    hour += minute/60; 
    	} 
    	String sh = " "; 
    	String sm = " "; 
    	String ss = " "; 
		if (hour < 10) {
			sh = "0 " + String.valueOf(hour);
		} else {
			sh = String.valueOf(hour);
		}
		if (minute < 10) {
			sm = "0 " + String.valueOf(minute);
		} else {
			sm = String.valueOf(minute);
		}
		if (second < 10) {
			ss = "0 " + String.valueOf(second);
		} else {
			ss = String.valueOf(second);
		}
    	return sh + ": " + sm + ": " + ss;
    }
    
    /**
	 * ��ȡ����ʱ��
	 * @param pattern HH-mm
	 * @return 
	 */
	public static String getStringTime(long time)
	{
		java.util.Date date = new java.util.Date(time);
		return dateFormaterTime.format(date);
	}
    
	/**
	 * ��ȡ����ʱ��
	 * @param pattern yyyyMMdd����yyyy-MM-dd-HH-mm:ss:ms ���͵�ʱ���ʽ
	 * @return 
	 */
	public static String getStringTimeSS(long time)
	{
		java.util.Date date = new java.util.Date(time);
		return dateFormaterWithoutSS.format(date);
	}
	
	public static long getLongTime(String time,String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		long millionSeconds = 0;
		
		try {
			millionSeconds = sdf.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return millionSeconds;
	}
	
    public static String getDataByEnglish(long time){
        long timeTemp = time * 1000;
        Date myDate = new Date(timeTemp);
        sdf.applyPattern("EEE MMM dd HH:mm:ss Z yyyy");
        return sdf.format(myDate);
    }
    
    public static CharSequence formatUnitTime(long iUploadTime) {
    	 Date date = new Date(iUploadTime);
         long currentTime = System.currentTimeMillis();
         Date currentDate = new Date(currentTime);
         long diff = currentTime - iUploadTime;
         long diffTime = diff/(60000);
         long diffDate = currentDate.getDate() - date.getDate();
         long diffMonth = currentDate.getMonth() - date.getMonth();
         long diffYear = currentDate.getYear() - date.getYear();
         if (diffYear != 0) {
             return dateFormaterWithoutSS.format(date);
         } else if (diffMonth == 0 && diffDate == 0) {
             if (diffTime <= 5) {
                 return "�ո�";
             } else if (diffTime <= 30) {
                 return diffTime + " ����ǰ";
             } else {
                 return "���� " + dateFormaterTime.format(date);
             }
         } else if (diffMonth == 0 && diffDate == 1) {
             return "���� " + dateFormaterTime.format(date);
         } else {
             return dateFormaterDateAndTime.format(date);
         }
    }

}
