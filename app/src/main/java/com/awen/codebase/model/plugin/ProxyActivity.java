package com.awen.codebase.model.plugin;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.awen.codebase.R;
import com.awen.codebase.common.ui.statusbar.StatusBarCompat;
import com.awen.plugin_lib.IPluginActivity;

import java.lang.reflect.Constructor;

public class ProxyActivity extends FragmentActivity {

    private String activityName;
    IPluginActivity pluginActivityInterface;
    private static final String TAG = "awen-plugin-host-proxy";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activityName = getIntent().getStringExtra("activity_name");
        try {
            Class activityClass = getClassLoader().loadClass(activityName);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});

            pluginActivityInterface = (IPluginActivity) instance;
            pluginActivityInterface.attachActivity(this);
            Bundle bundle = new Bundle();
            pluginActivityInterface.onCreate(bundle);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *ProxyActivity的启动模式必须为standard
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        Intent intent1 = new Intent(this,ProxyActivity.class);
        intent1.putExtra("activity_name",intent.getComponent().getClassName());
        Log.d(TAG,"intent.getComponent().getClassName()="+intent.getComponent().getClassName());
        super.startActivity(intent1);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d(TAG,"宿主startService"+service.getComponent().getClassName());
        Intent serviceIntent = new Intent(this,ProxyService.class);
        serviceIntent.putExtra("service_name",service.getComponent().getClassName());
        return super.startService(serviceIntent);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        ProxyReceiver proxyReceiver = new ProxyReceiver(receiver.getClass().getName());
        return super.registerReceiver(proxyReceiver, filter);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }


    @Override
    protected void onStart() {
        super.onStart();
        pluginActivityInterface.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginActivityInterface.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pluginActivityInterface.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pluginActivityInterface.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pluginActivityInterface.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pluginActivityInterface.onRestart();
    }
}
