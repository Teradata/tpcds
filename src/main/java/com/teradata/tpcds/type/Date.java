/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teradata.tpcds.type;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

public class Date
{
    public static final int JULIAN_DATA_START_DATE = toJulianDays(new Date(1998, 1, 1)); // earliest date in the data set
    public static final int JULIAN_DATA_END_DATE = toJulianDays(new Date(2003, 12, 31)); // latest date in the data set
    public static final Date TODAYS_DATE = new Date(2003, 1, 8); // the generator's sense of "today"
    public static final int JULIAN_TODAYS_DATE = toJulianDays(TODAYS_DATE);
    public static final int CURRENT_QUARTER = 1;
    public static final int CURRENT_WEEK = 2;

    public static final Date DATE_MAXIMUM = new Date(2002, 12, 31);
    public static final Date DATE_MINIMUM = new Date(1998, 1, 1);
    public static final int JULIAN_DATE_MAXIMUM = toJulianDays(DATE_MAXIMUM);
    public static final int JULIAN_DATE_MINIMUM = toJulianDays(DATE_MINIMUM);
    public static final String[] WEEKDAY_NAMES = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final int[] MONTH_DAYS = {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
    private static final int[] MONTH_DAYS_LEAP_YEAR = {0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};

    private final int year;
    private final int month;
    private final int day;

    public Date(int year, int month, int day)
    {
        checkArgument(year > 0, "Year must be a positive value");
        checkArgument(month > 0 && month <= 12, "Month must be a number between 1 and 12 (inclusive)");
        checkArgument(day > 0 && day <= getDaysInMonth(month, year), "Day must be a positive value and cannot exceed the maximum number of days in the month");
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
        // This is NOT a correct computation of leap years.
        // There is a bug in the C code that doesn't handle century years correctly.
        return year % 4 == 0;
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

    private static int getDaysInMonth(int month, int year)
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

    // the ordinal reference into the calendar distribution for a given date
    public static int getDayIndex(Date date)
    {
        return getDaysThroughFirstOfMonth(date) + date.getDay();
    }

    private static int getDaysThroughFirstOfMonth(Date date)
    {
        return isLeapYear(date.getYear()) ? MONTH_DAYS_LEAP_YEAR[date.getMonth()] : MONTH_DAYS[date.getMonth()];
    }

    public static Date computeFirstDateOfMonth(Date date)
    {
        return new Date(date.year, date.month, 1);
    }

    public static Date computeLastDateOfMonth(Date date)
    {
        // copies a bug in the C code that adds all the days in the year
        // through the first of month instead of just the number of days in the month
        return fromJulianDays(toJulianDays(date) - date.day + getDaysThroughFirstOfMonth(date));
    }

    public static Date computeSameDayLastYear(Date date)
    {
        int day = date.day;
        if (isLeapYear(date.year) && date.month == 2 && date.day == 29) {
            day = 28;
        }
        return new Date(date.year - 1, date.month, day);
    }

    public static Date computeSameDayLastQuarter(Date date)
    {
        int quarter = (date.month - 1) / 3;  // zero-indexed quarter number
        int julianStartOfQuarter = toJulianDays(new Date(date.year, quarter * 3 + 1, 1));
        int julianDate = toJulianDays(date);
        int distanceFromStart = julianDate - julianStartOfQuarter;

        int lastQuarter = quarter > 0 ? quarter - 1 : 3;
        int lastQuarterYear = quarter > 0 ? date.year : date.year - 1;
        int julianStartOfPreviousQuarter = toJulianDays(new Date(lastQuarterYear, lastQuarter * 3 + 1, 1));

        return fromJulianDays(julianStartOfPreviousQuarter + distanceFromStart);
    }

    // Uses the doomsday algorithm to calculate the day of the week.
    // The doomsday algorithm is based on the knowledge that our calendar
    // repeats itself every 400 years.  Additionally, there are certain easy
    // to remember dates in the year that always fall on the same day as each
    // other.  The day of the week on which these dates fall is referred to as doomsday.
    // https://en.wikipedia.org/wiki/Doomsday_rule
    public static int computeDayOfWeek(Date date)
    {
        // doomsdays for the first year of each century in a 400 year cycle
        int[] centuryAnchors = {3, 2, 0, 5};

        // Dates in each month that are known to fall on the same day of the week as each other.
        // The zero at index zero is just a place holder because months are 1-indexed.
        // Other values of zero refer to the last day of the previous month.
        int[] known = {0, 3, 0, 0, 4, 9, 6, 11, 8, 5, 10, 7, 12};

        int year = date.getYear();
        if (isLeapYear(date.getYear())) {
            // adjust the known dates for January and February
            known[1] = 4;
            known[2] = 1;
        }

        // calculate the doomsday for the century
        int centuryIndex = year / 100;
        centuryIndex -= 15;  // the year 1500 would be at index zero
        centuryIndex %= 4;  // which century are we in in the 400 year cycle
        int centuryAnchor = centuryAnchors[centuryIndex];

        // and then calculate the doomsday for the year
        int yearOfCentury = year % 100;
        int q = yearOfCentury / 12;
        int r = yearOfCentury % 12;
        int s = r / 4;
        int doomsday = centuryAnchor + q + r + s;
        doomsday %= 7;

        // finally, calculate the day of week for our date
        int result = date.getDay();
        result -= known[date.getMonth()];
        while (result < 0) {
            result += 7;
        }
        while (result > 6) {
            result -= 7;
        }

        result += doomsday;
        return result % 7;
    }

    @Override
    public String toString()
    {
        return format("%4d-%02d-%02d", year, month, day);
    }

    public int getMonth()
    {
        return month;
    }
}
