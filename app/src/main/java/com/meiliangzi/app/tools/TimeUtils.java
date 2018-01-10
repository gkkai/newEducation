package com.meiliangzi.app.tools;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private TimeUtils() {
    }

    public static String getCurrentTime() {
        return Long.toString(System.currentTimeMillis());
    }

    public static Long getCurrentTimes() {
        return System.currentTimeMillis();
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return format.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getScheduleTimeforStringOnce(String timeMillis) {
        long time = Long.parseLong(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getScheduleTimeforStringSecond(String timeMillis) {
        long time = Long.parseLong(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd  HH:mm");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getScheduleTimeforStringFour(String timeMillis) {
        long time = Long.parseLong(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd     HH:mm");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getScheduleTimeforStringThree(String timeMillis) {
        long time = Long.parseLong(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static Date getScheduleTime(long timeMillis) {
        return new Date(timeMillis);
    }

    @SuppressLint("SimpleDateFormat")
    public static long getLongTimeFromTimeStr(String timeStr) {
        long timeStemp = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
            Date date = simpleDateFormat.parse(timeStr);
            timeStemp = date.getTime();
            return timeStemp;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressLint("SimpleDateFormat")
    public static long getLongTimeFromTimeStr2(String timeStr) {
        long timeStemp = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(timeStr);
            timeStemp = date.getTime();
            return timeStemp;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }


}
