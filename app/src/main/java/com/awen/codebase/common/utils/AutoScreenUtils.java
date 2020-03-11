package com.awen.codebase.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
/**
 * Describe: 屏幕适配方案
 *
 * 1.设计以1080*1920(px)为标准，换成dp为360*640(dp)
 * 2.其他分辨率按宽为360dp为标准，density = displayWidth/360，保证所有机型宽都能铺满屏幕
 *
 * Created by AwenZeng on 2018/6/14.
 */
public class AutoScreenUtils {

    private static float originalScaledDensity;

    private static final int DEFAULT_STANDARD = 360;//默认标准

    public static void AdjustDensity(final Application application) {
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        final float originalDensity = displayMetrics.density;
        originalScaledDensity = displayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    originalScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }
            @Override
            public void onLowMemory() {
            }
        });
        LogUtil.androidLog("设备原始密度："+"density: " + displayMetrics.density + " densityDpi: "+
                displayMetrics.densityDpi +" scaledDensity: "+ displayMetrics.scaledDensity);
        float targetDensity = (float)displayMetrics.widthPixels / DEFAULT_STANDARD;
        float targetScaledDensity = targetDensity * (originalScaledDensity / originalDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaledDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = application.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;


        LogUtil.androidLog("新设备原始密度："+"density: " + displayMetrics.density + " densityDpi: "+
                displayMetrics.densityDpi +" scaledDensity: "+ displayMetrics.scaledDensity);

        application.registerActivityLifecycleCallbacks(new CreateActivityLifecycle() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                float targetDensity = (float)displayMetrics.widthPixels / DEFAULT_STANDARD;
                float targetScaledDensity = targetDensity * (originalScaledDensity / originalDensity);
                int targetDensityDpi = (int) (160 * targetDensity);
                displayMetrics.density = targetDensity;
                displayMetrics.scaledDensity = targetScaledDensity;
                displayMetrics.densityDpi = targetDensityDpi;

                DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
                activityDisplayMetrics.density = targetDensity;
                activityDisplayMetrics.scaledDensity = targetScaledDensity;
                activityDisplayMetrics.densityDpi = targetDensityDpi;
            }
        });

    }

    private static abstract class CreateActivityLifecycle implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

}