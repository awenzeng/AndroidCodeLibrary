package com.awen.codebase;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.awen.codebase.utils.AutoScreenUtils;

public class CodeBaseApp extends Application {
    private static CodeBaseApp instance;
    private Typeface mGobalFont = null;
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static boolean bUseFont = true;
    public static int SDK_VERSION = android.os.Build.VERSION.SDK_INT;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        bUseFont = false;
        AutoScreenUtils.AdjustDensity(this);
    }

    public static CodeBaseApp getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static CodeBaseApp getApplication() {
        return instance;
    }

    /**
     * 修改字体
     *
     * @return
     */
    public Typeface getDefaultFont() {
        if (!CodeBaseApp.bUseFont) {
            return null;
        } else if (CodeBaseApp.getInstance().mGobalFont == null) {
            CodeBaseApp.getInstance().mGobalFont = Typeface.createFromAsset(CodeBaseApp.getInstance().getAssets(), "font/umfont.ttf");
        }
        return CodeBaseApp.getInstance().mGobalFont;
    }

}
