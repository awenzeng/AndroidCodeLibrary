package com.awen.codebase.common.utils;

import com.awen.codebase.CodeBaseApp;


/**
 * 介绍
 * MMKV 是腾讯旗下开发的一款存储组件，采用 key - value方式存储，存储数据种类比较多，读写效率高。
 * 一款比sharepreferences稍重，远远小于数据库量级，但性能卓越的存储框架。
 * 必答：mmap+protobuf
 *
 * 特性
 * - 非常高效。MMKV使用mmap与文件保持内存同步，使用protobuf对数值进行编码/解码，充分利用Android，实现最佳性能。
 * - 多进程并发：MMKV支持进程之间的并发读写访问。
 * - 易于使用的。你可以随时使用MMKV。所有的更改都会立即保存，不需要同步，也不需要apply调用。
 * - 小。少数几个文件:MMKV包含进程锁、编码/解码帮助程序和mmap逻辑等等。很整洁。
 *   大约60K的二进制大小:MMKV在每个架构上增加了大约60K的应用程序大小，而压缩(apk)时增加的就少多了。
 *
 *
 * 详细介绍文章：https://www.jianshu.com/p/c12290a9a3f7
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

//    public static void init(){
//        MMKV.initialize(CodeBaseApp.getAppContext());
//    }
//
//    public MMKV getDefaultMMKV(){
//        return MMKV.defaultMMKV();
//    }
//
//    public MMKV getWithIdMMKV(){
//        return MMKV.mmkvWithID(FILENAME);
//    }
//
//    /**
//     * 跨进程
//     * @return
//     */
//    public MMKV getMultiProcessMMKV(){
//        return MMKV.mmkvWithID(FILENAME,MMKV.MULTI_PROCESS_MODE);
//    }
}
