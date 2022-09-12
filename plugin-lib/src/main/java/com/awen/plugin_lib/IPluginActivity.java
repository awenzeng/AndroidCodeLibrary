package com.awen.plugin_lib;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;


/**
 * Activity插件接口
 */
public interface IPluginActivity {
    public void attachActivity(Activity activity);
    public void onCreate(Bundle savedInstanceState);
    public void onResume();
    public void onPause();
    public void onStart();
    public void onRestart();
    public void onStop();
    public void onDestroy();
    public void onSaveInstanceState(Bundle outState);
    public boolean onTouchEvent(MotionEvent event);
    public void onBackPressed();
}
