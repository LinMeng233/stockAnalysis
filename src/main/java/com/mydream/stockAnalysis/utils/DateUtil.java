package com.mydream.stockAnalysis.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getCurrentTime(String format){
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return DateTimeFormatter.ofPattern(format).format(zonedDateTime);
    }

    public static int getCurrentDate(){
      String date = getCurrentTime("yyyyMMdd");
      return Integer.parseInt(date);
    }
}
