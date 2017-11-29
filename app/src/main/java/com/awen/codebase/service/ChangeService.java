package com.awen.codebase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.awen.codebase.R;
import com.awen.codebase.utils.ToastUtil;

public class ChangeService extends Service{
    private static int index = 0;       //  保存res\raw目录中图像资源的ID       
    private int[] resId ={R.drawable.one,  R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two};  
    @Override
    public IBinder onBind(Intent intent) {
	return null;
    }

    @Override
    public void onCreate() {
	super.onCreate();
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
	super.onStart(intent, startId);
	if(index == 5){
	    index = 0;
	}
	ToastUtil.showToast(this, resId[index++], false);
    }

}
