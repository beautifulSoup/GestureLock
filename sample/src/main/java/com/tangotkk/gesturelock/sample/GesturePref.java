package com.tangotkk.gesturelock.sample;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kris on 16/9/11.
 */
public class GesturePref {
    private static final String KEY_GESTURE_MODEL = "gesture_model";

    private static final String KEY_GESTURE = "gesture";

    public static void storeGesturePassword(Context context, String gesturePassword){
        SharedPreferences sp = context.getSharedPreferences(KEY_GESTURE_MODEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_GESTURE, gesturePassword).apply();
    }

    public static String getGesturePassword(Context context){
        return context.getSharedPreferences(KEY_GESTURE_MODEL, Context.MODE_PRIVATE)
                .getString(KEY_GESTURE, "");
    }


}
