package com.awen.plugin_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.awen.plugin_lib.IPluginReceiver;


public class AwenBroadcastReceiver extends BroadcastReceiver implements IPluginReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("awen-plugin", "插件收到广播");
        Toast.makeText(context,"插件收到广播",Toast.LENGTH_SHORT).show();
    }
}
