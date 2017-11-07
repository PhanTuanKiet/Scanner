package com.datviet.utils;

import android.text.TextUtils;

import com.datviet.model.History;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phong Phan on 22-Oct-17.
 */

public class DataManager {
    public static List<History> sHistoryData = new ArrayList<>();

    public static Gson sGson = new Gson();

//    public static void clear(){
//        sHistoryData.clear();
//    }


    public static void saveHistory() {
        if (sHistoryData != null) {
            String json = sGson.toJson(sHistoryData);
            SharedPreferenceUtil.getInstance().saveString(Constant.HISTORY_DATA, json);
        }
    }

    public static void loadHistoryData() {
        String json = SharedPreferenceUtil.getInstance().getString(Constant.HISTORY_DATA);
        if (!TextUtils.isEmpty(json)) {
            Type listType = new TypeToken<List<History>>() {}.getType();
            sHistoryData = sGson.fromJson(json, listType);
        }
        if (sHistoryData == null) sHistoryData = new ArrayList<>();
    }
}
