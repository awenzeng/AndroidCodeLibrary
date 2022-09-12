package com.awen.codebase.model.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * Hook技术实现Activity不需要注册就能启动
 */
public class HookAmsUtils {

    private static final HookAmsUtils hookUtils = new HookAmsUtils();

    private static Context mContext;
    private static final String TAG = "hook-ams";

    private HookAmsUtils() {

    }

    public static HookAmsUtils getInstance() {
        return hookUtils;
    }

    /**
     * hook startActivity流程，实现不在清单注册Activity也能进行开启
     *
     * @param context
     */
    public void hookAms(Context context) {
        try {
            mContext = context;
            //1.得到系统的IAcitivtyManager实例
            Class<?> amsClass = Class.forName("android.app.ActivityManager");
            Field iActivityManagerSingleton = amsClass.getDeclaredField("IActivityManagerSingleton");
            Log.d(TAG, "declaredField:123");
            iActivityManagerSingleton.setAccessible(true);
            Object singletonObj = iActivityManagerSingleton.get(null);
            Class<?> singleClass = Class.forName("android.util.Singleton");
            Field mInstanceFiled = singleClass.getDeclaredField("mInstance");
            mInstanceFiled.setAccessible(true);
            Object iActivityManagerObj = mInstanceFiled.get(singletonObj);

            //2.创建IAcitivtyManager动态代理对象
            AMSInvocationHandler invocationHandler = new AMSInvocationHandler(iActivityManagerObj);
            Object proxyInstance = Proxy.newProxyInstance(context.getClassLoader(), iActivityManagerObj.getClass().getInterfaces(), invocationHandler);

            //3.将系统的IAcitivtyManager对象替换为动态代理对象，实现对系统的IAcitivtyManager hook
            mInstanceFiled.set(singletonObj, proxyInstance);

            //4。hookHandler
            hookHandler();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "hook 出错：" + e.getMessage());
        }
    }

    public void hookHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            sCurrentActivityThreadField.setAccessible(true);
            Object activityThreadObj = sCurrentActivityThreadField.get(null);
            Handler handleObj = (Handler) mHField.get(activityThreadObj);
            Class handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(handleObj, new HandlerCallBack((Handler) handleObj));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "hookHandler error:" + e.getMessage());
        }
    }

    class AMSInvocationHandler implements InvocationHandler {

        Object sysIActivityManagerObj;

        public AMSInvocationHandler(Object iActivityManagerObj) {
            sysIActivityManagerObj = iActivityManagerObj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("startActivity")) {
                Log.d(TAG, "invoke startActivity");
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        Intent realIntent = (Intent) args[i];
                        Log.d(TAG, "realIntent1=" + realIntent);
                        Intent intent = new Intent();
                        ComponentName componentName = new ComponentName(mContext, ProxyActivity.class);
                        intent.setComponent(componentName);
                        intent.putExtra("real_intent", realIntent);
                        args[i] = intent;
                    }
                }
            }
            return method.invoke(sysIActivityManagerObj, args);
        }
    }

    class HandlerCallBack implements Handler.Callback {

        private Handler sysHandler;

        public HandlerCallBack(Handler handler) {
            sysHandler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            // 继续让系统处理  9.0 的是159    9.0之下的是100
            if (msg.what == 100 || msg.what == 159) {
                Log.d(TAG, "handleMessage msg.what=" + msg.what);
                checkAndChangeMessage(msg);
            }
            sysHandler.handleMessage(msg);
            return true;
        }

        private void checkAndChangeMessage(Message msg) {
            Object obj = msg.obj;
            try {
                Field intentFiled = obj.getClass().getDeclaredField("intent");
                intentFiled.setAccessible(true);
                Intent intentObj = (Intent) intentFiled.get(obj);
                //没有登录就跳转到LoginActivity，登录成功直接跳转
                Intent realIntent = intentObj.getParcelableExtra("real_intent");
                if (realIntent != null) {
//                    if (!Config.isLogin) {
//                        Log.d("lkx_debug", "没有登录跳转LoginActivity realIntent=" + realIntent);
//                        if (!realIntent.getComponent().getClassName().equals(MainActivity.class.getName())) {
//                            intentObj.setComponent(new ComponentName(mContext, LoginActivity.class));
//                        } else {
//                            intentFiled.set(obj, realIntent);
//                        }
//                    } else {
                        Log.d(TAG, "已经登录跳转LoginActivityreal_intent=" + realIntent);
                        intentFiled.set(obj, realIntent);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "handleMessage error:" + e.getMessage());
            }
        }
    }

}
