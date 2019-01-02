package com.awen.codebase.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.awen.codebase.IMainService;

public class WorkService extends Service {

    private ServiceHandler mHandler;

    private final int GuardId = 1;

    private static final int MSG_DEVICE_STATUS_CHANGED = 1;


    @Override
    public void onCreate() {
        //提高进程的优先级
        startForeground(GuardId, new Notification());
        mHandler = new ServiceHandler();
        mHandler.sendEmptyMessage(MSG_DEVICE_STATUS_CHANGED);
        startBindService();
        //SDK>=21,android5.0版本及以上，可以采用JobScheduler轮询器轮询保活
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this,WorkJobGuardService.class));
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SmartHome", "WorkService启动");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSmartHomeServiceBinder;
    }


    private final IMainService.Stub mSmartHomeServiceBinder = new IMainService.Stub() {
        @Override
        public void start(String temp) throws RemoteException {

        }
    };


    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("SmartHome", "服务端-进程：已连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("SmartHome", "服务端-进程：已断开");
            startBindService();
        }
    };


    /**
     * 开始启动绑定守护进程
     */
    private void startBindService(){
        startService(new Intent(this,WorkGuardService.class));
        bindService(new Intent(this, WorkGuardService.class), mServiceConnection, Context.BIND_IMPORTANT);
    }


    @Override
    public void onDestroy() {
        mHandler.removeMessages(MSG_DEVICE_STATUS_CHANGED);
        Log.d("SmartHome","服务已停止");
    }


     class ServiceHandler extends Handler {

        private int mValue = 0;
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_STATUS_CHANGED: {
                    Log.d("WorkService","我在工作：+" + mValue);
                    // Repeat every 1 second.
                    mValue++;
                    sendMessageDelayed(obtainMessage(MSG_DEVICE_STATUS_CHANGED), 1 * 1000);
                }
                break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
