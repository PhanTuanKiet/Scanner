package com.datviet.utils;

import android.text.TextUtils;
import com.datviet.model.RecyclerViewItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DataManager {
    public static List<RecyclerViewItem> sBookHistoryData = new ArrayList<>();
    public static List<RecyclerViewItem> sBookBorrowData = new ArrayList<>();
    public static List<RecyclerViewItem> sStudentHistoryData = new ArrayList<>();

    public static Gson sGson = new Gson();


    public static void saveBookHistory() {
        if (sBookHistoryData != null) {
            String json = sGson.toJson(sBookHistoryData);
            SharedPreferenceUtil.getInstance().saveString(Constant.BOOK_HISTORY_DATA, json);
        }
    }

    public static void loadBookHistoryData() {
        String json = SharedPreferenceUtil.getInstance().getString(Constant.BOOK_HISTORY_DATA);
        if (!TextUtils.isEmpty(json)) {
            Type listType = new TypeToken<List<RecyclerViewItem>>() {}.getType();
            sBookHistoryData = sGson.fromJson(json, listType);
        }
        if (sBookHistoryData == null) sBookHistoryData = new ArrayList<>();
    }

    public static void saveStudentHistory() {
        if (sStudentHistoryData != null) {
            String json = sGson.toJson(sStudentHistoryData);
            SharedPreferenceUtil.getInstance().saveString(Constant.STUDENT_HISTORY_DATA, json);
        }
    }

    public static void loadStudentHistoryData() {
        String json = SharedPreferenceUtil.getInstance().getString(Constant.STUDENT_HISTORY_DATA);
        if (!TextUtils.isEmpty(json)) {
            Type listType = new TypeToken<List<RecyclerViewItem>>() {}.getType();
            sStudentHistoryData = sGson.fromJson(json, listType);
        }
        if (sStudentHistoryData == null) sStudentHistoryData = new ArrayList<>();
    }


}
