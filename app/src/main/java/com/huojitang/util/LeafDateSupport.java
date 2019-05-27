package com.huojitang.util;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

/**
 * LeafDateSupport
 * Leaf 系统所需的日期工具方法集合
 * 对 java.time 下的相关 API 的简单包装
 *
 * @author 刘忠燏
 */
public class LeafDateSupport {
    /**
     * 获取当前的年月
     * @return 一个 YearMonth 对象
     */
    public static YearMonth getCurrentYearMonth() {
        return YearMonth.now();
    }

    /**
     * 从指定格式的字符串获取年月信息
     *
     * @param date 要转换的年月日期（格式为 yyyy-MM）
     * @return 一个 YearMonth 对象
     * @throws DateTimeParseException 解析失败时抛出该异常
     */
    public static YearMonth parseFromShortDate(String date) {
        return YearMonth.parse(date);
    }

    /**
     * 获取当前月的下一个月
     * @param current YearMonth 对象
     * @return 一个 YearMonth 对象，表示 current 一个月之后的 YearMonth
     */
    public static YearMonth nextMonth(@NotNull YearMonth current) {
        return current.plusMonths(1);
    }

    /**
     * 获取当前月的上一个月
     * @param current YearMonth 对象
     * @return 一个 YearMonth 对象，表示 current 一个月之后的 YearMonth
     */
    public static YearMonth prevMonth(@NotNull YearMonth current) {
        return current.minusMonths(1);
    }

    /**
     * 获取当前的日期
     * @return LocalDate 对象
     */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 从字符串中获取日期
     * @param dateStr 日期字符串（格式为 yyyy-MM-dd）
     * @return 一个 LocalDate 对象
     * @throws DateTimeParseException 如果解析失败
     */
    public static LocalDate parseFromLongDate(String dateStr) {
        return LocalDate.parse(dateStr);
    }

    public static LocalDate oneMonthBefore(@NotNull LocalDate date) {
        return date.minusMonths(1);
    }

    public static LocalDate oneMonthAfter(@NotNull LocalDate date) {
        return date.plusMonths(1);
    }
}
