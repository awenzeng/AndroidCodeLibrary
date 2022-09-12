package com.awen.plugin_lib;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service插件接口
 */
public interface IPluginService {

    public void attachActivity(Context context);

    public void onCreate();

    public IBinder onBind(Intent intent);

    public int onStartCommand(Intent intent, int flags, int startId);

    public boolean onUnbind(Intent intent);

    public void onRebind(Intent intent);

    public void onDestroy();

}
