package com.datviet.utils;

import com.datviet.model.History;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phong Phan on 22-Oct-17.
 */

public class DataManager {
    public static List<History> sHistoryData = new ArrayList<>();

    public static Gson gson = new Gson();

//    public static void clear(){
//        sHistoryData.clear();
//    }
}
