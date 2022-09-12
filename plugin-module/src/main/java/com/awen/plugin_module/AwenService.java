package com.awen.plugin_module;

import android.content.Intent;
import android.widget.Toast;

public class AwenService extends BasePluginService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (int i = 0; i < 5; i++) {
            Toast.makeText(context, "服务正在运行i=" + i, Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
