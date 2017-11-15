package com.datviet.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Phong Phan on 12-Nov-17.
 */

public class DateUtil {
    //2017-11-12 19:51:01.783
    private static SimpleDateFormat sdfServer = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static SimpleDateFormat sdfClient = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static DateFormat sdfHistory = new SimpleDateFormat("dd-MM-yyyy,HH:mm");


    public static Date parseDateServer(String dateFromServer) {
        try {
            Date date = sdfServer.parse(dateFromServer);
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
