package com.awen.codebase.common.data;


import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.awen.codebase.common.utils.NetUtil;


public class AppVersion {
	private String mAppVersion = null;
	private String mAppLicense  = null;
	private String mUrlParam = null;
	private String mImei = null;
	private String mImsi = null;
	private int mAppVersionValue = 0;
	private static AppVersion mInstance = null;

	public static final AppVersion getInstance(final Context context) {
		if (mInstance == null) {
			mInstance = new AppVersion();
			mInstance.Init(context);
		}
		return mInstance;
	}

	public String getUrlParam() {
		return mUrlParam;
	}

	public String getImei() {
		return mImei;
	}
	
	public String getImsi() {
		return mImsi;
	}
	
	public String getAppVersion() {
		return mAppVersion;
	}
	
	public int getAppVersionValue()
	{
		return mAppVersionValue;
	}

	public String getAppLicense() {
		return mAppLicense;
	}

	private AppVersion() {
	}

	private void Init(final Context context) {
		final Context mContext = context;
		String ver = null;
		try {
			ver = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		mAppVersion = ver;
		mAppLicense = "ANDROID-" + mAppVersion;
		mUrlParam = generalUrlParam(context);
		mAppVersionValue = 0;
		String[] appVer = mAppVersion.split("\\.");
		for(int i = 0; i < appVer.length; i++)
		{
			mAppVersionValue += Integer.parseInt(appVer[i])<<(8*(appVer.length - i));
		}
	}
	
	private String generalUrlParam(final Context context) {
		final Context mContext = context;
		if (mContext != null) {
			TelephonyManager tm = (TelephonyManager) mContext
					.getSystemService(Context.TELEPHONY_SERVICE);
			mImei = getIMEI(mContext);
			
			DisplayMetrics dm=new DisplayMetrics();
			WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
			wm.getDefaultDisplay().getMetrics(dm);
	        int screenWidth = dm.widthPixels;
	        int screenHeight = dm.heightPixels;
	        
			StringBuilder sb = new StringBuilder();
			mImsi = tm.getSubscriberId();
			if(mImsi == null)
		    {
				mImsi = "";
		    }

			// op 运营商标识 -1=其它，0=移动，1=联通，2=电信
			String opName = tm.getNetworkOperator();
			int op = -1;
			if (opName.equals("46000") || opName.equals("46002")
					|| opName.equals("46007")) {
				op = 0;
			} else if (opName.equals("46001")) {
				op = 1;
			} else if (opName.equals("46003") || opName.equals("460003")) {
				op = 2;
			}
			/**
			 *  
				imei移动设备标识 系统参数，优先取Device ID，为NULL则取Android ID
				 pf  手机平台    系统参数，由AND_APILEVEL构成，如：AND_15
				 md  机型  系统参数，取model字段，空格转为下横线，如：Nexus_S
				 mf  品牌   系统参数，优先取brand字段，为NULL则取manufacturer字段，如sumsung
				 sw  屏宽   系统参数，如 480
				 sh  屏高   系统参数，如 800
				 ttp 终端类型  自定义参数，(Terminal Type) 0:其它，1:手机终端非智能机，2:手机智能机，3:Pad，4:Pc客户
				移动用户参数
				 imsi 移动用户识别码 系统参数，如460021122701225，取不到填空
				 op  运营商标识 数字型，如：-1=其它，0=移动，1=联通，2=电信
				 cc  国家码 数字型，如：86=中国，android取不到，填0
				 sc  短信中心号码 字符串型，如：8613800917500，android取不到，填0
				 nt  网络类型 数字型，如：0=unknow，1=gprs，2=3g，3=wifi …
				平台与应用参数
				 aid 应用的CLASSID 字符串型，如：0x030000A8
				 ver 应用版本号 字符串型，如：01.00.00.00 
				 abd
				 应用编译日期 字符串型，如：2011_11_15
				 rbd 平台编译日期字符串型，如：2011_11_15
			*/
			Locale l = context.getResources().getConfiguration().locale;
			
			sb
			.append("imei=").append(mImei)
			.append("&pf=").append(getPF())
			.append("&md=").append(getMD())
			.append("&mf=").append(getMF())
			.append("&sw=").append(screenWidth)
			.append("&sh=").append(screenHeight)
			.append("&ttp=").append(2)
			.append("&imsi=").append(mImsi)
			.append("&op=").append(op)
			.append("&cc=").append(l.equals(Locale.CHINA)?"86":"0") //唉 不知道直接写86 有啥意义
			.append("&sc=").append("0")
			.append("&nt=").append(NetUtil.getNetType(mContext))
			.append("&aid=").append("应用ID")  // 兼容新协议规定
			.append("&ver=").append(mAppVersion)
			.append("&abd=").append("应用编译日期")
			.append("&rbd=").append("应用编译日期");
//			.append("&rhv=COMMON")
//			.append("&aid=").append(Constant.APP_APPID)   // 兼容新协议规定
//			.append("&imsi=").append(mImsi)
			
//			.append("&lic=").append(mAppLicense);
			//.append("&ttp=").append(2)
			//.append("&svctp=").append("0");
			String params = sb.toString().replaceAll(" ", "_");
			return params;
		}
		return null;
	}

	public static String getIMEI(Context context)
	{
		String imei = null;
		if(mInstance != null)
		{
			imei = mInstance.getImei();
		}
		if(!TextUtils.isEmpty(imei))
		{
			return imei;
		}
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		if (imei == null || imei.length() == 0) {
			imei = Settings.System.getString(
					context.getContentResolver(),
					Settings.Secure.ANDROID_ID);
			if (imei == null || imei.length() == 0) {
				imei = "0000000000000000";
			}
		}
		if (imei.length() < 16) {
			imei = imei + "0";
		} else if (imei.length() > 16) {
			imei = imei.substring(0, 15);
		}
		return imei;
	}
	
	public static String getPF()
	{
		return filterWord("AND_" + String.valueOf(Build.VERSION.SDK_INT));// Util.appendString("AND_",String.valueOf(Build.VERSION.SDK_INT)).toString();
	}
	
/*	public String getEncodePF()
	{
		return filterWord(getPF());//URLEncoder.encode(getPF());
	}
	*/
	public static String getMD()
	{
		return  filterWord( Build.MODEL);
	}
	
/*	public String getEncodeMD()
	{
		return filterWord(getMD());//URLEncoder.encode(getMD());
	}*/
	
	public static String getMF()
	{
		return filterWord(Build.MANUFACTURER);
	}
	
/*	public String getEncodeMF()
	{
		return    filterWord(getMF()); //URLEncoder.encode(getMF());
	}*/
	
	private static String filterWord(String word)
	{
		int length = word.length();
		StringBuilder builder = new StringBuilder();
		char ch = 0;
		for(int i = 0; i< length; i ++ )
		{
			ch = word.charAt(i);
			if( (ch > 47 && ch < 58) ||(ch > 64 && ch < 91)||( ch > 96 && ch < 123) || ch == 0X5F )
			{
				builder.append(ch);
			}
		}
		return builder.toString();
	}
}

