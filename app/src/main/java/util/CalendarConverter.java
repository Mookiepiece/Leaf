package util;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarTransUtil {
    private static final SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat sdf10 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 输入string转日期
     * 不判断正误
     * 例：“2022-02”，“2022-02-22”
     * @param str yyyy-MM-dd/yyyy-MM-dd
     */
    public static Calendar ToCalendar(String str){
        SimpleDateFormat sdf;

        //先转Date类型再转Calendar类型
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

    /**
     * 输入日期转年月yyyy-MM
     */
    public static String StrYearMonth(Calendar calendar){
        return sdf7.format(calendar.getTime());
    }

    /**
     * 输入日期转年月日yyyy-MM-DD
     */
    public static String StrYearMonthDay(Calendar calendar){
        return sdf10.format(calendar.getTime());
    }
}