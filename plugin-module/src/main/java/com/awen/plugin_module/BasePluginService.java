package com.awen.plugin_module;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.awen.plugin_lib.IPluginService;


public class BasePluginService extends Service implements IPluginService {

    Context context;

    @Override
    public void attachActivity(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
