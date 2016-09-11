package com.tangotkk.gesturelock.library;

import android.util.Log;

/**
 * Created by kris on 16/9/3.
 */
public class Logs {
    private static final String TAG = "GestureLock";

    public static void debug(String text){
        Log.d(TAG, text);
    }

    public static void error(String error){
        Log.e(TAG, error);
    }

    public static void info(String info){
        Log.i(TAG, info);
    }
}
