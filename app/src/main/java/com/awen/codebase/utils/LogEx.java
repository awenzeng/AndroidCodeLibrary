package com.awen.codebase.utils;

import android.util.Log;

public class LogEx {

    public static final int DEBUG_LEVEL = 10;

    static public void v(String tag, Object info) {
        if (DEBUG_LEVEL < 0 && tag != null && info != null) {
            Log.v(tag, info.toString());
        }
    }

    static public void i(String tag, Object info) {
        if (DEBUG_LEVEL < 1 && tag != null && info != null) {
            Log.i(tag, info.toString());
        }
    }

    static public void d(String tag, Object info) {
        if (DEBUG_LEVEL < 2 && tag != null && info != null) {
            Log.d(tag, info.toString());
        }
    }

    static public void w(String tag, Object info) {
        if (DEBUG_LEVEL < 3 && tag != null && info != null) {
            Log.w(tag, info.toString());
        }
    }

    public static void e(String tag, Object info) {
        if (DEBUG_LEVEL < 4 && tag != null && info != null) {
            Log.e(tag, info.toString());
        }
    }
}

