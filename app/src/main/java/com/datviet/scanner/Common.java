package com.datviet.scanner;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.datviet.utils.DataManager;

/**
 * Created by Phong Phan on 03-Nov-17.
 */

public  class Common {

    private static SharedPreferences.Editor editor;
    private static SharedPreferences sharedPrefs;

    public static void  saveData(){
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(CoreApplication.getsInstance().getApplicationContext());
        editor = sharedPrefs.edit();
        String json = DataManager.gson.toJson(DataManager.sHistoryData);
        editor.putString("GSON", json);
        editor.commit();
    }


}
