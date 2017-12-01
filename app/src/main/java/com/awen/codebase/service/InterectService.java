package com.awen.codebase.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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
        System.out.println("=========onCreate======");
    }

    /**
     * 服务启动的时候调用
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("=========onStartCommand======");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("=====onBind=====");
        return myBinder;
    }

    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        System.out.println("=========onDestroy======");
        super.onDestroy();
    }

    /**
     * 内部类继承Binder
     */
    public class MyBinder extends Binder {

        public void start(String temp) {
            System.out.println("=====InterectService打印："+temp);
        }
    }


}
