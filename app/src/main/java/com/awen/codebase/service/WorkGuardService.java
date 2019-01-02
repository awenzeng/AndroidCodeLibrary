package com.awen.codebase.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.awen.codebase.IMainService;

/**
 * Describe:服务端守护进程
 * Created by AwenZeng on 2018/12/26
 */
public class WorkGuardService extends Service {
    private final int GuardId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GuardId, new Notification());
        startBindService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSmartHomeServiceBinder;
    }


    /**
     * 开始唤起和绑定主要进程服务
     */
    private void startBindService(){
        startService(new Intent(WorkGuardService.this,WorkService.class));
        bindService(new Intent(WorkGuardService.this,WorkService.class),mServiceConnection,BIND_IMPORTANT);
    }


    private final IMainService.Stub mSmartHomeServiceBinder = new IMainService.Stub() {
        @Override
        public void start(String temp) throws RemoteException {

        }
    };

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SmartHome", "服务端-守护进程：已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SmartHome", "服务端-守护进程：已断开");
            startBindService();
        }
    };
}
