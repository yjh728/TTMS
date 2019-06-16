package com.yjh.test.util;

import java.sql.Date;
import java.sql.Time;

public class TimeUtil {
    public static int compareDate(Date startDate, Date nowDate) {
        int startYear = startDate.getYear();
        int startMonth = startDate.getMonth();
        int statrtDay = startDate.getDate();
        int nowYear = nowDate.getYear();
        int nowMonth = nowDate.getMonth();
        int nowDay = nowDate.getDate();
        if (startYear < nowYear) {
            return -1;
        } else if (startYear == nowYear) {
            if (startMonth < nowMonth) {
                return -1;
            } else if (startMonth == nowMonth) {
                if (statrtDay < nowDay) {
                    return -1;
                } else if (statrtDay == nowDay) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public static int compareTime(Time startTime, Time nowTime) {
        int startHour = startTime.getHours();
        int startminutes = startTime.getMinutes();
        int statrtSeconds = startTime.getSeconds();
        int nowHour = nowTime.getHours();
        int nowMinutes = nowTime.getMinutes();
        int nowSeconds = nowTime.getSeconds();
        if (startHour < nowHour) {
            return -1;
        } else if (startHour == nowHour) {
            if (startminutes < nowMinutes) {
                return -1;
            } else if (startminutes == nowMinutes) {
                if (statrtSeconds < nowSeconds) {
                    return -1;
                } else if (statrtSeconds == nowSeconds) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}
