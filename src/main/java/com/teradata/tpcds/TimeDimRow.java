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
import static com.teradata.tpcds.TimeDimColumn.T_AM_PM;
import static com.teradata.tpcds.TimeDimColumn.T_HOUR;
import static com.teradata.tpcds.TimeDimColumn.T_MEAL_TIME;
import static com.teradata.tpcds.TimeDimColumn.T_MINUTE;
import static com.teradata.tpcds.TimeDimColumn.T_SECOND;
import static com.teradata.tpcds.TimeDimColumn.T_SHIFT;
import static com.teradata.tpcds.TimeDimColumn.T_SUB_SHIFT;
import static com.teradata.tpcds.TimeDimColumn.T_TIME;
import static com.teradata.tpcds.TimeDimColumn.T_TIME_ID;
import static com.teradata.tpcds.TimeDimColumn.T_TIME_SK;

public class TimeDimRow
        extends TableRowWithNulls
{
    private final long tTimeSk;
    private final String tTimeId;
    private final int tTime;
    private final int tHour;
    private final int tMinute;
    private final int tSecond;
    private final String tAmPm;
    private final String tShift;
    private final String tSubShift;
    private final String tMealTime;

    public TimeDimRow(long nullBitMap, long tTimeSk, String tTimeId, int tTime, int tHour, int tMinute, int tSecond, String tAmPm, String tShift, String tSubShift, String tMealTime)
    {
        super(nullBitMap, T_TIME_SK);
        this.tTimeSk = tTimeSk;
        this.tTimeId = tTimeId;
        this.tTime = tTime;
        this.tHour = tHour;
        this.tMinute = tMinute;
        this.tSecond = tSecond;
        this.tAmPm = tAmPm;
        this.tShift = tShift;
        this.tSubShift = tSubShift;
        this.tMealTime = tMealTime;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(tTimeSk, T_TIME_SK),
                getStringOrNull(tTimeId, T_TIME_ID),
                getStringOrNull(tTime, T_TIME),
                getStringOrNull(tHour, T_HOUR),
                getStringOrNull(tMinute, T_MINUTE),
                getStringOrNull(tSecond, T_SECOND),
                getStringOrNull(tAmPm, T_AM_PM),
                getStringOrNull(tShift, T_SHIFT),
                getStringOrNull(tSubShift, T_SUB_SHIFT),
                getStringOrNull(tMealTime, T_MEAL_TIME));
    }
}
