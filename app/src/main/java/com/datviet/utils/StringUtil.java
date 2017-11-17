package com.datviet.utils;


public class StringUtil {

    public static String convertToUTF8(String text){
        try {
            return new String(text.getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
