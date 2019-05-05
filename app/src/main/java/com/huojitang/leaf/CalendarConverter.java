package com.huojitang.leaf;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class CalendarConverter {
    private static final SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 输入string转日期
     * 不判断正误
     * 例：“2022-02”，“2022-02-22”
     * @param str yyyy-MM-dd/yyyy-MM-dd
     */
    static Calendar ToCalendar(String str){
        SimpleDateFormat sdf;

        Date date = null;
        try {
            if(str.length()==7)
                date = sdf7.parse(str);
            else
                date = sdf10.parse(str);
        } catch (ParseException e) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    static String StrYearMonth(Calendar calendar){
        return sdf7.format(calendar.getTime());
    }
    static String StrYearMonthDay(Calendar calendar){
        return sdf10.format(calendar.getTime());
    }
}