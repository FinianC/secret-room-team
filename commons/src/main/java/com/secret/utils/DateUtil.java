package com.secret.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class DateUtil {


    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 当前时间
     * @return
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }


    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }



    public static String dateToStrNotSS(LocalDateTime localDateTime) {
        // 时间格式转换
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        return simpleDateFormat.format(localDateTime);
    }


    public static DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    public static String localDateTimeToStr(LocalDateTime localDateTime, DateTimeFormatter dfDateTime) {
        LocalDateTime now = localDateTime;
        return dfDateTime.format(now);
    }


}


