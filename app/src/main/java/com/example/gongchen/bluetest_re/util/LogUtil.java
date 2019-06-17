package com.example.gongchen.bluetest_re.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/6/27.
 */
//日志管理类
public class LogUtil {
    private static final boolean DEBUG = true;
    private static final String TAG = "666666";

    public static void v(String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }
}
