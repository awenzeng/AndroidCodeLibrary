package com.awen.codebase.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Base64;

import com.awen.codebase.CodeBaseApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;


/**
 * 数据缓存工具类（SharedPreferences）
 * Created by AwenZeng on 2019/1/8
 */
public class DataCacheUtil {

    private static DataCacheUtil dataCacheUtil;
    private static SharedPreferences sharedPreferences;

    public static final String FILENAME = CodeBaseApp.getAppContext().getPackageName();
    /**
     * --------------------------设置相关------------------------------------
     */
    public static final String KEY_SAVE_PATTERN_TIME = "savePatternTime";//缓存拉取模型时间
    public static final String KEY_SAVE_PATTERN_JSON = "savePatternJson";//缓存匹配模型



    private DataCacheUtil() {
        sharedPreferences = CodeBaseApp.getAppContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
    }

    public static DataCacheUtil getInstance() {
        if (dataCacheUtil == null) {
            synchronized (DataCacheUtil.class) {
                if (dataCacheUtil == null) {
                    dataCacheUtil = new DataCacheUtil();
                }
            }
        }
        return dataCacheUtil;
    }


    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    public void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }


    /**
     * 清除所有sharedPreferences缓存
     */
    public void cleanSharedPreference() {
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 清除智能设备退出缓存次数
     */
    public void cleanSmartDevicesSharedPreference() {
        Map<String, ?> allContent = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allContent.entrySet()) {
            String tempKey = entry.getKey();
            if (!tempKey.contains(":"))//设备的sid:00:f4:8d:0d:26:6d,非设备不清除
                continue;
            putInt(entry.getKey(), 0);
            LogUtil.androidLog("设备：" + entry.getKey() + ",用户退出次数：" + getInt(entry.getKey()));
        }
    }


    /**
     * 获取缓存值
     *
     * @param key
     * @param defValue
     * @return
     */
    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }


    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }


    /**
     * 保存偏好设置
     *
     * @param key
     * @param value
     */
    public void putString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        if (value == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, value);
        }
        editor.commit();
    }

    /**
     * 保存偏好设置
     *
     * @param map 需要保存的map集合
     */
    public void putMap(Map<String, String> map) {
        Editor editor = sharedPreferences.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {

            if (entry.getValue().length() == 0) {
                editor.putString(entry.getKey(), null);

            } else {
                editor.putString(entry.getKey(), entry.getValue());
            }
        }
        editor.commit();
    }


    /**
     * 保存int类型
     */
    public void putInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    /**
     * 获取int类型
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    /**
     * 获取数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean getBoolean(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * 保存偏好设置
     *
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 存放实体类以及任意类型
     * @param key
     * @param obj
     */
    public void putObject(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(), 0));
                putString(key, string64);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("the obj must implement Serializble");
        }

    }

    /**
     * 获取保存的对象
     * @param key
     * @return
     */
    public  Object getObject(String key) {
        Object obj = null;
        try {
            String base64 = getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     */
    public void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
    }


    /**
     * 按名字清除本应用数据库
     *
     * @param context
     * @param dbName
     */
    public void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context
     */
    public void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     *
     * @param filePath
     */
    public void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public void cleanApplicationData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    public void removeItem(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    private void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
