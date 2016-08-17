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

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.DateDimColumn.D_CURRENT_DAY;
import static com.teradata.tpcds.column.DateDimColumn.D_CURRENT_MONTH;
import static com.teradata.tpcds.column.DateDimColumn.D_CURRENT_QUARTER;
import static com.teradata.tpcds.column.DateDimColumn.D_CURRENT_WEEK;
import static com.teradata.tpcds.column.DateDimColumn.D_CURRENT_YEAR;
import static com.teradata.tpcds.column.DateDimColumn.D_DATE_ID;
import static com.teradata.tpcds.column.DateDimColumn.D_DATE_SK;
import static com.teradata.tpcds.column.DateDimColumn.D_DAY_NAME;
import static com.teradata.tpcds.column.DateDimColumn.D_DOM;
import static com.teradata.tpcds.column.DateDimColumn.D_DOW;
import static com.teradata.tpcds.column.DateDimColumn.D_FIRST_DOM;
import static com.teradata.tpcds.column.DateDimColumn.D_FOLLOWING_HOLIDAY;
import static com.teradata.tpcds.column.DateDimColumn.D_FY_QUARTER_SEQ;
import static com.teradata.tpcds.column.DateDimColumn.D_FY_WEEK_SEQ;
import static com.teradata.tpcds.column.DateDimColumn.D_FY_YEAR;
import static com.teradata.tpcds.column.DateDimColumn.D_HOLIDAY;
import static com.teradata.tpcds.column.DateDimColumn.D_LAST_DOM;
import static com.teradata.tpcds.column.DateDimColumn.D_MONTH_SEQ;
import static com.teradata.tpcds.column.DateDimColumn.D_MOY;
import static com.teradata.tpcds.column.DateDimColumn.D_QOY;
import static com.teradata.tpcds.column.DateDimColumn.D_QUARTER_NAME;
import static com.teradata.tpcds.column.DateDimColumn.D_QUARTER_SEQ;
import static com.teradata.tpcds.column.DateDimColumn.D_SAME_DAY_LQ;
import static com.teradata.tpcds.column.DateDimColumn.D_SAME_DAY_LY;
import static com.teradata.tpcds.column.DateDimColumn.D_WEEKEND;
import static com.teradata.tpcds.column.DateDimColumn.D_WEEK_SEQ;
import static com.teradata.tpcds.column.DateDimColumn.D_YEAR;
import static java.lang.String.format;

public class DateDimRow
        extends TableRowWithNulls
{
    private final long dDateSk;
    private final String dDateId;
    private final int dMonthSeq;
    private final int dWeekSeq;
    private final int dQuarterSeq;
    private final int dYear;
    private final int dDow;
    private final int dMoy;
    private final int dDom;
    private final int dQoy;
    private final int dFyYear;
    private final int dFyQuarterSeq;
    private final int dFyWeekSeq;
    private final String dDayName;
    private final boolean dHoliday;
    private final boolean dWeekend;
    private final boolean dFollowingHoliday;
    private final int dFirstDom;
    private final int dLastDom;
    private final int dSameDayLy;
    private final int dSameDayLq;
    private final boolean dCurrentDay;
    private final boolean dCurrentWeek;
    private final boolean dCurrentMonth;
    private final boolean dCurrentQuarter;
    private final boolean dCurrentYear;

    public DateDimRow(long nullBitMap,
            long dDateSk,
            String dDateId,
            int dMonthSeq,
            int dWeekSeq,
            int dQuarterSeq,
            int dYear,
            int dDow,
            int dMoy,
            int dDom,
            int dQoy,
            int dFyYear,
            int dFyQuarterSeq,
            int dFyWeekSeq,
            String dDayName,
            boolean dHoliday,
            boolean dWeekend,
            boolean dFollowingHoliday,
            int dFirstDom,
            int dLastDom,
            int dSameDayLy,
            int dSameDayLq,
            boolean dCurrentDay,
            boolean dCurrentWeek,
            boolean dCurrentMonth,
            boolean dCurrentQuarter,
            boolean dCurrentYear)
    {
        super(nullBitMap, D_DATE_SK);
        this.dDateSk = dDateSk;
        this.dDateId = dDateId;
        this.dMonthSeq = dMonthSeq;
        this.dWeekSeq = dWeekSeq;
        this.dQuarterSeq = dQuarterSeq;
        this.dYear = dYear;
        this.dDow = dDow;
        this.dMoy = dMoy;
        this.dDom = dDom;
        this.dQoy = dQoy;
        this.dFyYear = dFyYear;
        this.dFyQuarterSeq = dFyQuarterSeq;
        this.dFyWeekSeq = dFyWeekSeq;
        this.dDayName = dDayName;
        this.dHoliday = dHoliday;
        this.dWeekend = dWeekend;
        this.dFollowingHoliday = dFollowingHoliday;
        this.dFirstDom = dFirstDom;
        this.dLastDom = dLastDom;
        this.dSameDayLy = dSameDayLy;
        this.dSameDayLq = dSameDayLq;
        this.dCurrentDay = dCurrentDay;
        this.dCurrentWeek = dCurrentWeek;
        this.dCurrentMonth = dCurrentMonth;
        this.dCurrentQuarter = dCurrentQuarter;
        this.dCurrentYear = dCurrentYear;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(dDateSk, D_DATE_SK),
                getStringOrNull(dDateId, D_DATE_ID),
                getDateStringOrNullFromJulianDays(dDateSk, D_DATE_SK),
                getStringOrNull(dMonthSeq, D_MONTH_SEQ),
                getStringOrNull(dWeekSeq, D_WEEK_SEQ),
                getStringOrNull(dQuarterSeq, D_QUARTER_SEQ),
                getStringOrNull(dYear, D_YEAR),
                getStringOrNull(dDow, D_DOW),
                getStringOrNull(dMoy, D_MOY),
                getStringOrNull(dDom, D_DOM),
                getStringOrNull(dQoy, D_QOY),
                getStringOrNull(dFyYear, D_FY_YEAR),
                getStringOrNull(dFyQuarterSeq, D_FY_QUARTER_SEQ),
                getStringOrNull(dFyWeekSeq, D_FY_WEEK_SEQ),
                getStringOrNull(dDayName, D_DAY_NAME),
                getStringOrNull(format("%4dQ%d", dYear, dQoy), D_QUARTER_NAME),
                getStringOrNullForBoolean(dHoliday, D_HOLIDAY),
                getStringOrNullForBoolean(dWeekend, D_WEEKEND),
                getStringOrNullForBoolean(dFollowingHoliday, D_FOLLOWING_HOLIDAY),
                getStringOrNull(dFirstDom, D_FIRST_DOM),
                getStringOrNull(dLastDom, D_LAST_DOM),
                getStringOrNull(dSameDayLy, D_SAME_DAY_LY),
                getStringOrNull(dSameDayLq, D_SAME_DAY_LQ),
                getStringOrNullForBoolean(dCurrentDay, D_CURRENT_DAY),
                getStringOrNullForBoolean(dCurrentWeek, D_CURRENT_WEEK),
                getStringOrNullForBoolean(dCurrentMonth, D_CURRENT_MONTH),
                getStringOrNullForBoolean(dCurrentQuarter, D_CURRENT_QUARTER),
                getStringOrNullForBoolean(dCurrentYear, D_CURRENT_YEAR)
        );
    }
}
