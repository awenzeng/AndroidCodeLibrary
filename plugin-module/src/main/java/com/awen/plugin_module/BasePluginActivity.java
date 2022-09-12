package com.awen.plugin_module;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.awen.plugin_lib.IPluginActivity;


public class BasePluginActivity extends Activity implements IPluginActivity {

    Activity plugActivity;
    public static final String TAG ="awen-plugin";

    @Override
    public void attachActivity(Activity activity) {
        plugActivity = activity;
        Log.d(TAG,"attachActivity plugActivity="+plugActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        if (plugActivity == null) {
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        Log.d(TAG, "setContentView");
        if (plugActivity != null) {
            plugActivity.setContentView(layoutResID);
        }else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (plugActivity != null) {
            return plugActivity.getClassLoader();
        }
        return super.getClassLoader();
    }

    @Override
    public  View findViewById(int id) {
        if (plugActivity != null) {
            return plugActivity.findViewById(id);
        }
        return super.findViewById(id);
    }

    @Override
    public void startActivity(Intent intent) {
        if (plugActivity != null){
            plugActivity.startActivity(intent);
        }else {
            super.startActivity(intent);
        }
    }

    @Override
    public ComponentName startService(Intent service) {
        if (plugActivity!=null){
            return plugActivity.startService(service);
        }
        return super.startService(service);
    }

    @Override
    public void setContentView(View view) {
        if (plugActivity != null) {
            plugActivity.setContentView(view);
        }else {
            super.setContentView(view);
        }
    }

    @Override
    public Window getWindow() {
        if (plugActivity != null) {
            return plugActivity.getWindow();
        }
        return super.getWindow();
    }


    @Override
    public WindowManager getWindowManager() {
        if (plugActivity != null) {
            return plugActivity.getWindowManager();
        }
        return super.getWindowManager();
    }

    @Override
    public Intent getIntent() {
        if(plugActivity!=null){
            return plugActivity.getIntent();
        }
        return super.getIntent();
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        if(plugActivity!=null) {
            return plugActivity.getLayoutInflater();
        }
        return super.getLayoutInflater();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        if(plugActivity!=null) {
            return plugActivity.getApplicationInfo();
        }
        return super.getApplicationInfo();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        if (plugActivity == null) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        if (plugActivity == null) {
            super.onPause();
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        if (plugActivity == null) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        Log.d(TAG, "onRestart");
        if (plugActivity == null) {
            super.onRestart();
        }
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        if (plugActivity == null) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (plugActivity == null) {
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (plugActivity == null) {
            return super.registerReceiver(receiver, filter);
        }else {
            return plugActivity.registerReceiver(receiver, filter);
        }
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (plugActivity == null) {
            unregisterReceiver(receiver);
        }else {
            plugActivity.unregisterReceiver(receiver);
        }
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if (plugActivity == null) {
            super.sendBroadcast(intent);
        }else {
             plugActivity.sendBroadcast(intent);
        }
    }
}
