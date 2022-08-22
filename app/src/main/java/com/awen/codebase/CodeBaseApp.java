package com.awen.codebase;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.awen.codebase.common.utils.AutoScreenUtils;
import com.danikula.videocache.HttpProxyCacheServer;

import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;


public class CodeBaseApp extends Application {
    private static CodeBaseApp instance;
    private Typeface mGobalFont = null;
    public static final boolean DEBUG = true;
    public static boolean bUseFont = true;
    public static int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    public static HttpProxyCacheServer httpProxyCacheServer;
    private final static String PROCESS_NAME = "com.test";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        bUseFont = false;
        AutoScreenUtils.AdjustDensity(this);

        if (isAppMainProcess()) {//主进程
            //do something for init
        }

//        //这个ProxyActivity在清单文件中注册过，以后所有的Activitiy都可以用ProxyActivity无需声明，绕过监测
//        HookAmsUtil hookAmsUtil = new HookAmsUtil(ProxyActivity.class, this);
//        hookAmsUtil.hookSystemHandler();
//        hookAmsUtil.hookAms();
        httpProxyCacheServer = new HttpProxyCacheServer.Builder(getAppContext())
                .maxCacheFilesCount(30)
                .maxCacheSize(1024 * 1024 * 1024)     // 设置可存储1G资源
                .build();


        initARouter();
        initDKPlayer();

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
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isAppMainProcess() {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(getAppContext(), pid);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else if (PROCESS_NAME.equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private void initARouter(){
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }


    private void initDKPlayer(){
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
//                //使用使用IjkPlayer解码
//                .setPlayerFactory(IjkPlayerFactory.create())
//                //使用ExoPlayer解码
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
                //使用MediaPlayer解码
                .setPlayerFactory(AndroidMediaPlayerFactory.create())
                .build());
    }


    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager)                   context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
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
