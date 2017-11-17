package com.datviet.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    //2017-11-12 19:51:01.783
    private static SimpleDateFormat sdfServerWithMills = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static SimpleDateFormat sdfServerWithoutMills = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat sdfClient = new SimpleDateFormat("dd/MM/yyyy");
    private static DateFormat sdfHistory = new SimpleDateFormat("dd-MM-yyyy,HH:mm");


    public static Date parseDateServerWithMills(String dateFromServer) {
        try {
            Date date = sdfServerWithMills.parse(dateFromServer);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date parseDateServerWithoutMills(String dateFromServer) {
        try {
            Date date = sdfServerWithoutMills.parse(dateFromServer);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatToString(Date date) {

        if (date != null) {
            String txt = sdfClient.format(date);
            return txt;
        }

        return null;
    }


    public static String getCurrentDate() {
        sdfHistory.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        return sdfHistory.format(new Date());
    }

}
