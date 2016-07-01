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

package com.teradata.tpcds;

import com.teradata.tpcds.type.Date;

import static com.teradata.tpcds.BusinessKeyGenerator.makeBusinessKey;
import static com.teradata.tpcds.DateDimColumn.D_NULLS;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.distribution.CalendarDistribution.getIsHolidayFlagAtIndex;
import static com.teradata.tpcds.distribution.CalendarDistribution.getQuarterAtIndex;
import static com.teradata.tpcds.type.Date.CURRENT_QUARTER;
import static com.teradata.tpcds.type.Date.CURRENT_WEEK;
import static com.teradata.tpcds.type.Date.TODAYS_DATE;
import static com.teradata.tpcds.type.Date.WEEKDAY_NAMES;
import static com.teradata.tpcds.type.Date.computeDayOfWeek;
import static com.teradata.tpcds.type.Date.computeFirstDateOfMonth;
import static com.teradata.tpcds.type.Date.computeLastDateOfMonth;
import static com.teradata.tpcds.type.Date.computeSameDayLastQuarter;
import static com.teradata.tpcds.type.Date.computeSameDayLastYear;
import static com.teradata.tpcds.type.Date.fromJulianDays;
import static com.teradata.tpcds.type.Date.getDayIndex;
import static com.teradata.tpcds.type.Date.isLeapYear;
import static com.teradata.tpcds.type.Date.toJulianDays;

public class DateDimRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(D_NULLS);

        Date baseDate = new Date(1900, 1, 1);
        long dDateSk = rowNumber + toJulianDays(baseDate);
        String dDateId = makeBusinessKey(dDateSk);
        Date date = fromJulianDays((int) dDateSk);
        int dYear = date.getYear();
        int dDow = computeDayOfWeek(date);
        int dMoy = date.getMonth();
        int dDom = date.getDay();

        // set the sequence counts; assumes that the date table starts on a year boundary
        int dWeekSeq = ((int) rowNumber + 6) / 7;
        int dMonthSeq = (dYear - 1900) * 12 + dMoy - 1;
        int dQuarterSeq = (dYear - 1900) * 4 + dMoy / 3 + 1;
        int dayIndex = getDayIndex(date);
        int dQoy = getQuarterAtIndex(dayIndex);

        // fiscal year is identical to calendar year
        int dFyYear = dYear;
        int dFyQuarterSeq = dQuarterSeq;
        int dFyWeekSeq = dWeekSeq;
        String dDayName = WEEKDAY_NAMES[dDow];
        boolean dHoliday = getIsHolidayFlagAtIndex(dayIndex) != 0;
        boolean dWeekend = dDow == 5 || dDow == 6;
        boolean dFollowingHoliday;
        if (dayIndex == 1) {
            // This is not correct-- the last day of the previous year is always the 366th day.
            // Copying behavior of C code.
            int lastDayOfPreviousYear = 365 + (isLeapYear(dYear - 1) ? 1 : 0);
            dFollowingHoliday = getIsHolidayFlagAtIndex(lastDayOfPreviousYear) != 0;
        }
        else {
            dFollowingHoliday = getIsHolidayFlagAtIndex(dayIndex - 1) != 0;
        }
        int dFirstDom = toJulianDays(computeFirstDateOfMonth(date));
        int dLastDom = toJulianDays(computeLastDateOfMonth(date));
        int dSameDayLy = toJulianDays(computeSameDayLastYear(date));
        int dSameDayLq = toJulianDays(computeSameDayLastQuarter(date));
        boolean dCurrentDay = dDateSk == TODAYS_DATE.getDay();
        boolean dCurrentYear = dYear == TODAYS_DATE.getYear();
        boolean dCurrentMonth = dCurrentYear && dMoy == TODAYS_DATE.getMonth();
        boolean dCurrentQuarter = dCurrentYear && dQoy == CURRENT_QUARTER;
        boolean dCurrentWeek = dCurrentYear && dWeekSeq == CURRENT_WEEK;

        return new RowGeneratorResult(new DateDimRow(nullBitMap,
                dDateSk,
                dDateId,
                dMonthSeq,
                dWeekSeq,
                dQuarterSeq,
                dYear,
                dDow,
                dMoy,
                dDom,
                dQoy,
                dFyYear,
                dFyQuarterSeq,
                dFyWeekSeq,
                dDayName,
                dHoliday,
                dWeekend,
                dFollowingHoliday,
                dFirstDom,
                dLastDom,
                dSameDayLy,
                dSameDayLq,
                dCurrentDay,
                dCurrentWeek,
                dCurrentMonth,
                dCurrentQuarter,
                dCurrentYear));
    }

    @Override
    public void reset() {}
}
