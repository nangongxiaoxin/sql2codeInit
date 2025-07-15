package com.slilio.sql2codeInit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: slilio @CreateTime: 日期转换 2025-07-15 @Description: @Version: 1.0
 */
public class DateUtils {
  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String _YYYYMMDD = "yyyy/MM/dd";
  public static final String YYYYMMDD = "yyyyMMdd";

  public static String format(Date date, String patten) {
    return new SimpleDateFormat(patten).format(date);
  }

  public static Date parse(String date, String patten) {
    try {
      new SimpleDateFormat(patten).parse(date);

    } catch (ParseException e) {
      e.printStackTrace();
    }
    return new Date();
  }
}
