package com.rightcode.wellcar.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    //결재 날짜
    public static String getPaymentDate() {
        Date localDate = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("yyyyMMddHHmmss").format(localDate);
    }

    //현재 날짜
    public static String getDate() {
        Date localDate = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("yyyyMMdd").format(localDate);
    }


    //현재 날짜
    public static String getDate(String separation) {
        Date localDate = new Date(System.currentTimeMillis());
        switch (separation){
            case "/":{
                return new SimpleDateFormat("yyyy/MM/dd").format(localDate);
            }
            case "-":{
                return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
            }
            case ".":{
                return new SimpleDateFormat("yyyy.MM.dd").format(localDate);
            }
            default:
                return new SimpleDateFormat("yyyyMMdd").format(localDate);
        }
    }
    // 매개변수 날짜와 오늘날짜 비교
    public static long caldate(String paramString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long calDate = 0;
        try {
            Date firstDate = format.parse(paramString);
            Date secondDate = format.parse(getDate());

            calDate = (firstDate.getTime() - secondDate.getTime()) / (24 * 60 * 60 * 1000);
            return calDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calDate;
    }

    // 현재날짜 기준 몇일 후 날짜 계산
    public static String getLaterDay(int laterCnt) {
        String laterDay = null;
        try {
            Calendar cal = Calendar.getInstance();
            Date laterDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //날짜 형식에 따라서 달리 할 수 있다.

            cal.setTime(laterDate);

            cal.add(Calendar.DATE, laterCnt);        //laterCnt 만큼 후의 날짜를 구한다. laterCnt 자리에 -7 을 입력하면 일주일전에 날짜를 구할 수 있다.
            laterDate = cal.getTime();
            laterDay = format.format(laterDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return laterDay;
    }

    public static String getCurrentYear() {
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        return year;
    }

    public static String getCurrentMonth() {
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1);
        return month;
    }

    public static String getCurrentDay() {
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return day;
    }

    //현재 날짜
    public static int getMonthToInt() {
        Date localDate = new Date(System.currentTimeMillis());
        return Integer.parseInt(new SimpleDateFormat("MM").format(localDate));
    }

    //현재 날짜
    public static int getYearToInt() {
        Date localDate = new Date(System.currentTimeMillis());
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(localDate));
    }

    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        String currentTime = format.format(new Date());
        return currentTime;
    }


    public static String dotTransfer(String date) {
        try {
            return String.format("%4s.%2s.%2s", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
        } catch (Exception e) {
            return "";
        }
    }



    public static String dotMDTransfer(String date) {
        try {
            return String.format("%2s.%2s",date.substring(4, 6), date.substring(6, 8));
        } catch (Exception e) {
            return "";
        }
    }

    public static String korTransfer(String date) {
        try {
            return String.format("%4s년%2s월%2s일", date.substring(0, 4), date.substring(4, 6), date.substring(6, 8));
        } catch (Exception e) {
            return "";
        }
    }

}
