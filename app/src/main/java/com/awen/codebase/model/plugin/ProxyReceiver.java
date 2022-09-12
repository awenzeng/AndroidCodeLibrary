package com.awen.codebase.model.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.awen.plugin_lib.IPluginReceiver;

import java.lang.reflect.Constructor;

public class ProxyReceiver extends BroadcastReceiver {

    String className;
    IPluginReceiver pluginReceiverInterface;

    public ProxyReceiver(String className){
        this.className = className;
        try {
            Class<?> aClass = PluginManager.getInstance().getClassLoader().loadClass(className);
            Constructor<?> constructor = aClass.getConstructor(new Class[]{});
            Object obj = constructor.newInstance(new Object[]{});
            pluginReceiverInterface = (IPluginReceiver) obj;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("awen-plugin","宿主ProxyReceiver收到广播");
        if (pluginReceiverInterface != null) {
            pluginReceiverInterface.onReceive(context,intent);
        }
    }
}
