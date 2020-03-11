package com.awen.codebase.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.awen.codebase.common.utils.LogUtil;

/**
 *  可以与Activity交互的Service
 *  1.可以startService()启动和stopService()关闭
 *  2.也可以bindService()启动和unbindService()关闭
 */
public class InterectService extends Service {

    private MyBinder myBinder = new MyBinder();
    /**
     * 服务创建的时候调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.androidLog("=========onCreate======");
    }

    /**
     * 服务启动的时候调用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.androidLog("=========onStartCommand======");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.androidLog("=====onBind=====");
        return myBinder;
    }

    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        LogUtil.androidLog("=========onDestroy======");
        super.onDestroy();
    }

    /**
     * 内部类继承Binder
     */
    public class MyBinder extends Binder {

        public void start(String temp) {
            LogUtil.androidLog("=====InterectService打印："+temp);
        }
    }


}
