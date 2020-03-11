package com.awen.codebase;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.awen.codebase.activity.ProxyActivity;
import com.awen.codebase.common.utils.AutoScreenUtils;
import com.awen.codebase.common.utils.HookAmsUtil;
import com.awen.codebase.common.utils.LogUtil;
import com.awen.messagebus.IHandleMessage;
import com.awen.messagebus.MessageBus;


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

        //这个ProxyActivity在清单文件中注册过，以后所有的Activitiy都可以用ProxyActivity无需声明，绕过监测
        HookAmsUtil hookAmsUtil = new HookAmsUtil(ProxyActivity.class, this);
        hookAmsUtil.hookSystemHandler();
        hookAmsUtil.hookAms();

        MessageBus.getDefault().handleMessage(new IHandleMessage() {
            @Override
            public void handleMessage(Message msg) {
                LogUtil.androidLog("handleMessage:"+ JSONObject.toJSONString(msg.obj));
            }
        });

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
