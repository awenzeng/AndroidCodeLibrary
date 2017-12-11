package com.awen.codebase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.awen.codebase.IMainService;
import com.awen.codebase.utils.LogUtil;

/**
 * AIDLService可以实现跨进程间交互
 *  1.可以startService()启动和stopService()关闭，此种方式启动为正常启动
 *  2.也可以bindService()启动和unbindService()关闭，如AIDLService在不同的进程中，其就实现了跨进程间的通信
 */
public class AIDLService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.androidLog("Received start command.");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.androidLog("Received binding.");
        return mBinder;
    }

    private final IMainService.Stub mBinder = new IMainService.Stub() {
        @Override
        public void start(String temp) throws RemoteException {
            LogUtil.androidLog("AIDLService服务端打印日志："+temp);
        }
    };
}
