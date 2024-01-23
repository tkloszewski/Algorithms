package com.smart.tkl.euler.p19;

import java.util.ArrayList;
import java.util.List;



public class CountingSundays {

    private final static int[] months = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        long sundays = countSundays(1960, 2, 22, 1970, 1, 11);
        System.out.println("Sundays: " + sundays);
    }

    public static long countSundays(long year1, int month1, int day1, long year2, int month2, int day2) {
        long days1 = getDaysFrom1900(year1, month1);
        long days2 = getDaysFrom1900(year2, month2, day2);

        long sundaysOnFirst = 0;

        if(day1 == 1 && (days1 + 1) % 7 == 0) {
            sundaysOnFirst++;
        }

        long days = days1 + months[month1 - 1];
        if(isLeapYear(year1) && month1 == 2) {
            days++;
        }

        long year = year1;
        int month = month1 + 1;
        if(month > 12) {
            month = 1;
            year++;
        }

        boolean isLeapYear = isLeapYear(year);

        while (days + 1 <= days2) {
            if((days + 1) % 7 == 0) {
               sundaysOnFirst++;
            }

            days += months[month - 1];
            if(isLeapYear && month == 2) {
               days++;
            }

            month++;
            if(month > 12) {
                month = 1;
                year++;
            }

            isLeapYear = isLeapYear(year);
        }

        return sundaysOnFirst;
    }

    private static long getDaysFrom1900(long year, int month, int day) {
        long days = getDaysFrom1900(year, month);
        days += day;
        return days;
    }

    private static long getDaysFrom1900(long year, int month) {
        long leapYears = getLeapYears(year) - getLeapYears(1900);
        long days = (year - 1900) * 365 + leapYears;
        if(isLeapYear(year)) {
           days--;
        }
        for(int i = 0; i < month - 1; i++) {
            days += months[i];
        }
        if(month > 2 && isLeapYear(year)) {
            days++;
        }
        return days;
    }

    private static long getLeapYears(long year) {
        return year / 4 - year / 100 + year / 400;
    }

    private static boolean isLeapYear(long year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

}