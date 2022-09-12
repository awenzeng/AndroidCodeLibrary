package com.awen.codebase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.model.plugin.PluginManager;
import com.awen.codebase.model.plugin.ProxyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Route(path = ActivityRouter.AROUTER_PluginActivity)
public class PluginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_plugin);
    }

    public void load(View view) {
        try {
            InputStream inputSt = getAssets().open("plugin-app.apk");
            String plugPath = getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath() + "/plugin.apk";
            File file = new File(plugPath);
            if (file.exists()) {
                file.delete();
            }
            Log.d("awen-plugin-host", "plugPath=" + plugPath);
            FileOutputStream fos = new FileOutputStream(plugPath);
            byte[] b = new byte[1024];
            int off = 0;
            while ((off = inputSt.read(b)) != -1) {
                fos.write(b, 0, off);
            }
            fos.close();
            inputSt.close();
            Toast.makeText(this, "拷贝插件成功", Toast.LENGTH_SHORT).show();
            PluginManager.getInstance().loadPlugin(this, plugPath);
            Toast.makeText(this, "加载插件成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("awen-plugin-host", "加载插件失败e=" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jump(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        //这里有个坑，activities[0].name只是清单文件排第一的activity，不是插件的启动activity
        intent.putExtra("activity_name", PluginManager.getInstance().getPackageInfo().activities[0].name);
        Log.d("awen-plugin-host", "跳转activity name=" + PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
    }

    public void send(View view) {
        Intent intent = new Intent("com.awen.pluginmodule.action");
        sendBroadcast(intent);
    }
}
