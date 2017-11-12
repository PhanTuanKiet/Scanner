package com.datviet.utils;

import android.util.Log;

import com.datviet.scanner.BuildConfig;

/**
 * Created by Phong Phan on 11-Nov-17.
 */

public class AppLog {

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }

}
