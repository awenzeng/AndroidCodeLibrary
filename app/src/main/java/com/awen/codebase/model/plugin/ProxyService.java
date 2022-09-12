package com.awen.codebase.model.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.awen.plugin_lib.IPluginService;

import java.lang.reflect.Constructor;

import dalvik.system.DexClassLoader;

public class ProxyService extends Service {

    private IPluginService pluginServiceInterface;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getPlugService(Intent intent){
        String serviceName = intent.getStringExtra("service_name");
        DexClassLoader classLoader = PluginManager.getInstance().getClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass(serviceName);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            pluginServiceInterface = (IPluginService) constructor.newInstance(new Object[]{});
            pluginServiceInterface.attachActivity(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("awen-plugin","宿主服务开启");
        if (pluginServiceInterface == null) {
            getPlugService(intent);
        }
        pluginServiceInterface.onStartCommand(intent,flags,startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        pluginServiceInterface.onDestroy();
        super.onDestroy();
    }
}
