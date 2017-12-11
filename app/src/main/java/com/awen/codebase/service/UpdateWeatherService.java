package com.awen.codebase.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.awen.codebase.activity.FragmentsActivity;
import com.awen.codebase.activity.GroupsActivity;
import com.awen.codebase.activity.MainActivity;
import com.awen.codebase.R;
import com.awen.codebase.widget.CityWeather;
import com.awen.codebase.widget.CodeBaseWidget;
import com.awen.codebase.widget.WeatherBean;

/**
 * @author way
 */
public class UpdateWeatherService extends Service {
	private static final int UPDATE = 0x123;
	private RemoteViews remoteViews;
	private WeatherBean weather;
	private boolean isGetWeather = false;
	
	// 数字时间图片资源数组
	private int[] imgs = { R.drawable.n0, R.drawable.n1, R.drawable.n2,
			R.drawable.n3, R.drawable.n4, R.drawable.n5, R.drawable.n6,
			R.drawable.n7, R.drawable.n8, R.drawable.n9, };
	
	// 将显示小时、分钟的ImageView定义成数组
	private int[] dateViews = { R.id.h1, R.id.h2, R.id.m1, R.id.m2 };
	
	// 按照中国天气网的天气图片顺序排列好本地资源图片，我这里是随意的~嘿嘿
	private int[] weatherImg = { R.drawable.sunny, R.drawable.cloudy,
			R.drawable.chance_of_rain, R.drawable.chance_of_sleet,
			R.drawable.chance_of_snow, R.drawable.chance_of_storm,
			R.drawable.clock1, R.drawable.fog, R.drawable.haze,
			R.drawable.mist, R.drawable.mostly_sunny, R.drawable.mostly_cloudy,
			R.drawable.lower, R.drawable.middle };
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATE:
				updateTime();
				updateWeather();
				isGetWeather = true;
				break;
			}
		}
	};
	// 广播接收者去接收系统每分钟的提示广播，来更新时间
	private BroadcastReceiver mTimePickerBroadcast = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			updateTime();
		}
	};

	private void updateWeather(){
		remoteViews.setTextViewText(R.id.condition, weather.getWeather());
		remoteViews.setTextViewText(R.id.tem, (weather.getTemp()));
		// 根据图片名，获取天气图片资源
		if (weather.getImg() != null || !"".equals(weather.getImg())) 
			remoteViews.setImageViewResource(R.id.weather,weatherImg[Integer.parseInt(weather.getImg())]);
		
		// 执行更新
		ComponentName componentName = new ComponentName(getApplicationContext(), CodeBaseWidget.class);
		AppWidgetManager.getInstance(getApplicationContext()).updateAppWidget(componentName, remoteViews);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		weather = new WeatherBean();
		remoteViews = new RemoteViews(getApplication().getPackageName(),R.layout.widget);// 实例化RemoteViews
		if (isNetworkAvailable()) {
			new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					weather = CityWeather.getWeather();// json解析中国天气网天气
					Message msg = handler.obtainMessage();
					msg.what = UPDATE;
					handler.sendMessage(msg);
				}
			}).start(); 
		} else {
			toast();
		}
		// 点击天气图片，进入MainActivity
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0, intent, 0);
		
		Intent intent1 = new Intent(getApplicationContext(), GroupsActivity.class);
		PendingIntent pi1 = PendingIntent.getActivity(getApplicationContext(),0, intent1, 0);
		
		Intent intent2 = new Intent(getApplicationContext(), FragmentsActivity.class);
		PendingIntent pi2 = PendingIntent.getActivity(getApplicationContext(),0, intent2, 0);
		
		remoteViews.setOnClickPendingIntent(R.id.weather, pi);
		remoteViews.setOnClickPendingIntent(R.id.linearLayout1, pi1);
		remoteViews.setOnClickPendingIntent(R.id.linearLayout2, pi2);

		// 定义一个定时器去更新天气。实际开发中更新时间间隔可以由用户设置，
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				if(isGetWeather){
					msg.what = UPDATE;
					handler.sendMessage(msg);
				}
			}
		}, 1, 3600 * 1000);// 每小时更新一次天气
	}

	private void updateTime() {
		Date date = new Date();
		// 定义SimpleDateFormat对象
		SimpleDateFormat df = new SimpleDateFormat("HHmm");
		// 将当前时间格式化成HHmm的形式
		String timeStr = df.format(date);

		for (int i = 0; i < timeStr.length(); i++) {
			// 将第i个数字字符转换为对应的数字
			int num2 = Integer.parseInt(timeStr.substring(i, i + 1));
			// 将第i个图片的设为对应的数字图片
			remoteViews.setImageViewResource(dateViews[i], imgs[num2]);
		}
		remoteViews.setTextViewText(R.id.city, weather.getCity());
		remoteViews.setTextViewText(R.id.date, "0" + (date.getMonth() + 1)
				+ "-" + date.getDate() + " 周" + date.getDay());
		ComponentName componentName = new ComponentName(getApplication(),
				CodeBaseWidget.class);
		AppWidgetManager.getInstance(getApplication()).updateAppWidget(
				componentName, remoteViews);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// 注册系统每分钟提醒广播（注意：这个广播只能在代码中注册）
		IntentFilter updateIntent = new IntentFilter();
		updateIntent.addAction("android.intent.action.TIME_TICK");
		registerReceiver(mTimePickerBroadcast, updateIntent);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// 注销系统的这个广播
		unregisterReceiver(mTimePickerBroadcast);
		//被系统干掉后，服务重启,做一次流氓软件,哈哈
		Intent intent = new Intent(getApplicationContext(), UpdateWeatherService.class);
		getApplication().startService(intent); 
		super.onDestroy();
	}

	/**
	 * 判断手机网络是否可用
	 * 
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	private void toast() {
		new AlertDialog.Builder(getApplicationContext())
				.setTitle("提示")
				.setMessage("网络连接未打开")
				.setPositiveButton("前往打开",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
								startActivity(intent);
							}
						}).setNegativeButton("取消", null).create().show();
	}
}
