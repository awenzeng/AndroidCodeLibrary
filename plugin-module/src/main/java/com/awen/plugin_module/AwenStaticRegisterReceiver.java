package com.awen.plugin_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AwenStaticRegisterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"插件静态注册广播收到收到！",Toast.LENGTH_SHORT).show();
    }
}
