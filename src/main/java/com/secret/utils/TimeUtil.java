package com.secret.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @program: card-voucher_new
 * @description: 时间工具类
 * @author: 陈迪
 * @create: 2022/10/19 14:26
 */
public class TimeUtil {


    public static DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static DateTimeFormatter dfDate = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    public static String localDateTimeToStr(DateTimeFormatter dfDateTime){
        LocalDateTime now = LocalDateTime.now();
        return dfDateTime.format(now);
    }

    public static String localDateTimeToStr(LocalDateTime localDateTime, DateTimeFormatter dfDateTime) {
        LocalDateTime now = localDateTime;
        return dfDateTime.format(now);
    }

    public static LocalDateTime dateStrToLocalDateTime(String dateStr, DateTimeFormatter dfDateTime) {
        LocalDate parse = LocalDate.parse(dateStr, dfDateTime);
        return parse.atStartOfDay();
    }

    /**
     * 获取一个月的最后一天
     * @return
     * @exception
     * @date       2019/5/17 10:35
     */
    public static LocalDateTime getLastDayOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime lastDayTime = localDateToLocalDateTime(lastDay).withHour(23).withMinute(59).withSecond(59);
        return lastDayTime;
    }

    /**
     * 获取上个月第一天
     * @return
     */
    public static LocalDateTime getFirstDayOfLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.plusMonths(-1);
        LocalDate firstDay = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime of = LocalDateTime.of(firstDay, LocalTime.MIN);
        return of;
    }


    public static LocalDateTime getLastDayOfLastMonth() {
        LocalDate today = LocalDate.now();
        today = today.plusMonths(-1);
        LocalDate lastDay = today.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime of = LocalDateTime.of(lastDay, LocalTime.MAX);
        return of;
    }

    /**
     * 获取上周第一天
     * @return
     */
    public static LocalDateTime getFirstDayOfLastWeek() {
        LocalDate today = LocalDate.now().with(DayOfWeek.MONDAY).plusDays(-7);
        LocalDateTime of = LocalDateTime.of(today, LocalTime.MIN);
        return of;
    }
    /**
     * 获取上周最后一天
     * @return
     */
    public static LocalDateTime getLastDayOfLastWeek() {
        LocalDate today = LocalDate.now().with(DayOfWeek.SUNDAY).plusDays(-7);
        LocalDateTime of = LocalDateTime.of(today, LocalTime.MAX);
        return of;
    }
    /**
     * 获取本周最后一天
     * @return
     */
    public static LocalDateTime getLastDayOfWeek() {
        LocalDate today = LocalDate.now().with(DayOfWeek.SUNDAY);
        LocalDateTime of = LocalDateTime.of(today, LocalTime.MAX);
        return of;
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static LocalDateTime getFirstDayOfWeek() {
        LocalDate today = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDateTime of = LocalDateTime.of(today, LocalTime.MIN);
        return of;
    }





    /**
     * LocalDate To LocalDateTime
     * @return
     * @exception
     * @date       2019/5/17 14:13
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate) {

        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date =  Date.from(instant);

        instant = date.toInstant();
        zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 获取一个月的第一天
     * @return
     * @exception
     * @date       2019/5/17 10:34
     */
    public static LocalDateTime getFirstDayOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstday = LocalDate.of(today.getYear(),today.getMonth(),1);
        LocalDateTime firstDayTime = localDateToLocalDateTime(firstday);

        return firstDayTime;
    }


    /**
     * localDateTime2YMDHMS
     * 返回的格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @throws
     * @date 2019/1/7 12:04
     */
    public static String localDateTime2YMDHMS(LocalDateTime localDateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(localDateTime);
    }

    public static void main(String[] args) {
        System.out.println( getFirstDayOfLastWeek());
        System.out.println( LocalDateTime.of(LocalDate.now().plusDays(-6), LocalTime.MIN));
        System.out.println(getFirstDayOfLastMonth());
    }

    public static String defLocalDateTimeToStr() {
        LocalDateTime now = LocalDateTime.now();
        return dfDateTime.format(now);
    }

}
