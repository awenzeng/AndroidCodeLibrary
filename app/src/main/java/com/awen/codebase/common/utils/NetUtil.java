package com.awen.codebase.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


/**
 * 与网络相关的类,用于网络的检测
 * 
 */
public class NetUtil {
	public static boolean isNetConnected(Context context) {
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

		if (networkInfo != null) 
			return networkInfo.isAvailable();

		return false;
	}
	/**
	 * @param context
	 * @return unknown 0, gprs 1, 3g 2, wifi 3
	 */
	public static int getNetType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context  
	            .getSystemService(Context.CONNECTIVITY_SERVICE);  
	    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
	    if (activeNetInfo != null) { 
	    	int type = activeNetInfo.getType();
	            if(type == ConnectivityManager.TYPE_WIFI) 
	            	return 3;
	            else if(type == ConnectivityManager.TYPE_MOBILE) {
	            	type = activeNetInfo.getSubtype();
	            	if(type == TelephonyManager.NETWORK_TYPE_UMTS ||
	            			type == TelephonyManager.NETWORK_TYPE_CDMA)
	            		return 2;
	            	else
	            		return 1;
	            }
	    }  
	    return 0;  
	} 
	
}
