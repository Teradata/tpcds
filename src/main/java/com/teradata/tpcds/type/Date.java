package com.teradata.tpcds.type;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Date
{
    public static final int JULIAN_DATA_START_DATE = toJulianDays(new Date(1998, 1, 1)); // earliest date in the data set
    public static final int JULIAN_DATA_END_DATE = toJulianDays(new Date(2003, 12, 31)); // latest date in the data set
    public static final int JULIAN_DATE_MAXIMUM = toJulianDays(new Date(2002, 12,31));
    public static final int JULIAN_DATE_MINIMUM = toJulianDays(new Date(1998, 1, 1));

    private final int year;
    private final int month;
    private final int day;

    public Date(int year, int month, int day)
    {
        checkArgument(year > 0, "Year must be a positive value");
        checkArgument(month > 0 && month <= 12, "Month must be a number between 1 and 12 (inclusive)");
        checkArgument(day > 0 && day < getDaysInMonth(month, year), "Day must be a positive value");
        this.year = year;
        this.month = month;
        this.day = day;
    }

    // Algorithm: Fleigel and Van Flandern (CACM, vol 11, #10, Oct. 1968, p. 657)
    public static Date fromJulianDays(int julianDays)
    {
        checkArgument(julianDays >= 0, "Days must be a positive value");
        int l = julianDays + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1) / 1461001);
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;

        int day = l - (2447 * j) / 80;
        l = j / 11;
        int month = j + 2 - 12 * l;
        int year = 100 * (n - 49) + i + l;

        return new Date(year, month, day);
    }

    // http://quasar.as.utexas.edu/BillInfo/JulianDatesG.html
    public static int toJulianDays(Date date)
    {
        int month = date.month;
        int year = date.year;

        // Start years in March so you don't have to account for February.
        if (month <= 2) {
            month += 12;
            year -= 1;
        }

        int daysBceInJulianEpoch = 1721118;  //Days Before the Common Era (before year 1) in the Julian Epoch

        // The month calculation looks convoluted, but can be thought of as follows:
        // There are a little over 30.6 (153/5) days in a month (excluding February)
        // Subtract 3 months because we start from the third month and don't include the current month
        // (153/5 * 3 = 459/5)
        // adding another 2/5 gets you 31 days at the right times.
        return date.day +
                (153 * month - 457) / 5 +
                365 * year + year / 4 - year / 100 + year / 400 +  // 365 days in a year + leap years
                daysBceInJulianEpoch + 1;
    }

    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static int getDaysInYear(int year)
    {
        return isLeapYear(year) ? 366 : 365;
    }

    public int getDay()
    {
        return day;
    }

    public int getYear()
    {
        return year;
    }

    private int getDaysInMonth(int month, int year)
    {
        Set<Integer> longMonths = ImmutableSet.of(1, 3, 5, 7, 8, 10, 12);
        Set<Integer> thirtyDayMonths = ImmutableSet.of(4, 6, 9, 11);
        if (longMonths.contains(month)) {
            return 31;
        }

        if (thirtyDayMonths.contains(month)) {
            return 30;
        }

        checkState(month == 2, "Invalid value for month " + month);
        if (isLeapYear(year)) {
            return 29;
        }

        return 28;
    }
}
