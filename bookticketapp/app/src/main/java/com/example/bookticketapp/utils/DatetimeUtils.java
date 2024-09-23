package com.example.bookticketapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatetimeUtils {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_FORMAT2 = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DAY_FORMAT = "E d/M";

    public static final Calendar stringToCalendar(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(dateString));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return calendar;
    }

    public static final String calendarToString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static final String dateToString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static final String dateToString2(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static final String timeToString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(calendar.getTime());
    }

    public static final String dateTimeToString(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(calendar.getTime());
    }
}
