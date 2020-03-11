package com.awen.codebase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.awen.codebase.common.utils.LogUtil;
import com.awen.codebase.common.utils.ToastUtil;

/**
 *  一般Service
 *  startService()启动和stopService()关闭
 */
public class NormalService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
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
    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        LogUtil.androidLog("=========onDestroy======");
        super.onDestroy();
    }

}
