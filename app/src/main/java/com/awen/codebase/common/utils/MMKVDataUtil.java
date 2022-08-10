package com.awen.codebase.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Base64;

import com.awen.codebase.CodeBaseApp;
import com.tencent.mmkv.MMKV;

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
public class MMKVDataUtil {

    private static MMKVDataUtil mmkvDataCacheUtil;

    public static final String FILENAME = CodeBaseApp.getAppContext().getPackageName();
    /**
     * --------------------------设置相关------------------------------------
     */
    public static final String KEY_SAVE_PATTERN_TIME = "savePatternTime";//缓存拉取模型时间
    public static final String KEY_SAVE_PATTERN_JSON = "savePatternJson";//缓存匹配模型


    public static MMKVDataUtil getInstance() {
        if (mmkvDataCacheUtil == null) {
            synchronized (MMKVDataUtil.class) {
                if (mmkvDataCacheUtil == null) {
                    mmkvDataCacheUtil = new MMKVDataUtil();
                }
            }
        }
        return mmkvDataCacheUtil;
    }

    public static void init(){
        MMKV.initialize(CodeBaseApp.getAppContext());
    }

    public MMKV getDefaultMMKV(){
        return MMKV.defaultMMKV();
    }

    public MMKV getWithIdMMKV(){
        return MMKV.mmkvWithID(FILENAME);
    }

    /**
     * 跨进程
     * @return
     */
    public MMKV getMultiProcessMMKV(){
        return MMKV.mmkvWithID(FILENAME,MMKV.MULTI_PROCESS_MODE);
    }
}
