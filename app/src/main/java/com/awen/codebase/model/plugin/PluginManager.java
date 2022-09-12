package com.awen.codebase.model.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

public class PluginManager {

    private static PluginManager instance = new PluginManager();
    private Context context;
    private String plugPath = "";
    private DexClassLoader dexClassLoader;
    private Resources resources;
    private PackageInfo packageInfo;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        return instance;
    }

    /**
     * 加载插件
     */
    public void loadPlugin(Context context, String plugPath) {
        this.context = context.getApplicationContext();
        this.plugPath = plugPath;
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(plugPath, PackageManager.GET_ACTIVITIES);

        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(plugPath, dexOutFile.getAbsolutePath()
                , null, context.getClassLoader());
        registerPluginReceiver(plugPath);
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, plugPath);
            resources = new Resources(assetManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    /**
     * 返回ClassLoader，未安装的apk中的类需要自己创建classloader来加载
     * <p>
     * DexClassLoader 可以加载外部的 apk、jar 或 dex文件
     * <p>
     * 这里使用DexClassLoader是为了加载 插件apk 中的dex文件，
     * 加载dex文件后系统就可以在dex中找到我们要使用的class类R.java，
     * 在R.java中包含着资源等的id，通过id我们可以获取到资源。
     *
     * @return
     */
    public DexClassLoader getClassLoader() {
        Log.d("awen-plugin", "dexClassLoader=" + dexClassLoader);
        return dexClassLoader;
    }

    /**
     * 返回Resources，未安装的apk中的资源文件需要自己创建Resources来加载
     *
     * @return
     */
    public Resources getResources() {
        Log.d("awen-plugin", "resources=" + resources);
        return resources;
    }

    /**
     * 通过查看PMS源码分析，模仿通过一个PackageParser解析apk,得到插件apk静态注册的广播信息（广播全类名，IntentFilter集合），在宿主apk中进行动态注册
     * 广播全类名 = PackageItemInfo 中的name属性  Activity属性ActivityInfo extends ComponentInfo extends PackageItemInfo
     * IntentFilter集合 = Component 中的属性 ArrayList<II> intents  Activity extend Component
     */
    public void registerPluginReceiver(String plugPath) {
        try {
            //1.得到PackageParser对象
            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
//            Constructor<?> constructor = packageParserClass.getConstructor(new Class[]{});
//            Object packageParser = constructor.newInstance(new Object[]{});
            Object packageParser = packageParserClass.newInstance();

            //2.得到parsePackage方法,调用得到Package
            Method parsePackageMethod = packageParserClass.getMethod("parsePackage", File.class, int.class);
            parsePackageMethod.setAccessible(true);

            //3.根据Package对象拿到属性receivers ArrayList<Activity> activities = new ArrayList<Activity>(0);
            Object packageObj = parsePackageMethod.invoke(packageParser, new File(plugPath), PackageManager.GET_ACTIVITIES);
            Field receiversField = packageObj.getClass().getDeclaredField("receivers");
            receiversField.setAccessible(true);
            List<Object> receivers = (List<Object>) receiversField.get(packageObj);


            //拿到intentFields属性
            Class<?> componentClass = Class.forName("android.content.pm.PackageParser$Component");
            Field fileFilter = componentClass.getDeclaredField("intents");
            fileFilter.setAccessible(true);

            //4.遍历receivers集合，取出activity对象，拿到ActiivtyInfo中的name属性，也就是插件中静态注册的广播全类名,并得到IntentFilter
            for (Object activity : receivers) {
                //
                Field infoField = activity.getClass().getField("info");
                infoField.setAccessible(true);
                ActivityInfo activityInfo = (ActivityInfo) infoField.get(activity);
                String receiverName = activityInfo.name;
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) getClassLoader().loadClass(receiverName).newInstance();
                //从android.content.pm.PackageParser$Component拿到插件静态广播的IntentFilter
                List<? extends IntentFilter> intents = (ArrayList) fileFilter.get(activity);
                for (IntentFilter intentFilter : intents) {
                    //5.参数都拿到了，动态注册广播
                    context.registerReceiver(broadcastReceiver, intentFilter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("awen-plugin", "注册插件静态广播异常:" + e.getMessage());
        }
    }

}
