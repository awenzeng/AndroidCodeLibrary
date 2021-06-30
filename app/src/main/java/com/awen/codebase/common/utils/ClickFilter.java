package com.awen.codebase.common.utils;

/**
 * 点击过滤工具类
 */
public class ClickFilter {
    private static String TAG = "ClickFilter";
    private static long lastClickTime = 0L;
    private static final long CLICK_INTERVAL = 500L;

    /**
     * 重置点击过滤器, 在每次用代码模拟View点击事件的时候, 要先reset一下, 以防被过滤掉
     */
    public static void resetMultiClick() {
        lastClickTime = 0L;
    }

    public static boolean isQucikClick() {
        long time = System.currentTimeMillis();
        if ((time - lastClickTime) > CLICK_INTERVAL) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }
}
