package com.datviet.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.datviet.scanner.CoreApplication;


public class SharedPreferenceUtil {

    private static SharedPreferenceUtil sSharedPreferenceUtil;
    private final String NAME = "SharedPreferenceUtil";

    private SharedPreferences mSharedPreferences;

    public SharedPreferenceUtil() {
        mSharedPreferences = CoreApplication.getsInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance() {
        if (sSharedPreferenceUtil == null) sSharedPreferenceUtil = new SharedPreferenceUtil();

        return sSharedPreferenceUtil;
    }

    public void saveString(String key, String value) {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public String getString(String key) {
        if (mSharedPreferences != null) {
            String value = mSharedPreferences.getString(key, "");
            return value;
        }
        return "";
    }

    public void saveBoolean(String key,Boolean value){
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(key,value);
            editor.commit();
        }
    }

    public Boolean getBoolean(String key) {
        if (mSharedPreferences != null) {
            Boolean value = mSharedPreferences.getBoolean(key,false);
            return value;
        }
        return false;
    }

}
