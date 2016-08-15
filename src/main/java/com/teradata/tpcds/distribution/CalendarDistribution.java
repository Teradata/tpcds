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

package com.teradata.tpcds.distribution;

import com.google.common.collect.ImmutableList;
import com.teradata.tpcds.distribution.DistributionUtils.WeightsBuilder;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.type.Date;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.getListFromCommaSeparatedValues;
import static com.teradata.tpcds.distribution.DistributionUtils.getWeightForIndex;
import static com.teradata.tpcds.distribution.DistributionUtils.pickRandomValue;
import static com.teradata.tpcds.type.Date.isLeapYear;
import static java.lang.Integer.parseInt;

public class CalendarDistribution
{
    private static final int NUM_WEIGHT_FIELDS = Weights.values().length;
    private static final String VALUES_AND_WEIGHTS_FILENAME = "calendar.dst";
    private static final int[][] DAYS_BEFORE_MONTH = {{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334}, {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335}};
    private static final CalendarDistribution CALENDAR_DISTRIBUTION = buildCalendarDistribution();

    private final ImmutableList<Integer> daysOfYear; // ordinal for the day of year.  Numbering is consistent across leap years and non-leap years   For example, March 1st will always be 61.
    private final ImmutableList<Integer> quarters;
    private final ImmutableList<Integer> holidayFlags;
    private final ImmutableList<ImmutableList<Integer>> weightLists;

    private CalendarDistribution(ImmutableList<Integer> daysOfYear, ImmutableList<Integer> quarters, ImmutableList<Integer> holidayFlags, ImmutableList<ImmutableList<Integer>> weightLists)
    {
        this.daysOfYear = daysOfYear;
        this.quarters = quarters;
        this.holidayFlags = holidayFlags;
        this.weightLists = weightLists;
    }

    private static CalendarDistribution buildCalendarDistribution()
    {
        ImmutableList.Builder<Integer> daysOfYearBuilder = ImmutableList.builder();
        ImmutableList.Builder<Integer> quartersBuilder = ImmutableList.builder();
        ImmutableList.Builder<Integer> holidayFlagsBuilder = ImmutableList.builder();

        List<WeightsBuilder> weightsBuilders = new ArrayList<>(NUM_WEIGHT_FIELDS);
        for (int i = 0; i < NUM_WEIGHT_FIELDS; i++) {
            weightsBuilders.add(new WeightsBuilder());
        }

        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);

            List<String> values = getListFromCommaSeparatedValues(fields.get(0));
            checkState(values.size() == 8, "Expected line to contain 8 values, but it contained %d, %s", values.size(), values);

            // month names, days of month, seasons, month numbers, and firsts of month are never used, so we ignore them
            daysOfYearBuilder.add(parseInt(values.get(0)));
            quartersBuilder.add(parseInt(values.get(5)));
            holidayFlagsBuilder.add(parseInt(values.get(7)));

            List<String> weights = getListFromCommaSeparatedValues(fields.get(1));
            checkState(weights.size() == NUM_WEIGHT_FIELDS, "Expected line to contain %d weights, but it contained %d, %s", NUM_WEIGHT_FIELDS, weights.size(), values);
            for (int i = 0; i < weights.size(); i++) {
                weightsBuilders.get(i).computeAndAddNextWeight(parseInt(weights.get(i)));
            }
        }

        ImmutableList.Builder<ImmutableList<Integer>> weightsListBuilder = ImmutableList.<ImmutableList<Integer>>builder();
        for (WeightsBuilder weightsBuilder : weightsBuilders) {
            weightsListBuilder.add(weightsBuilder.build());
        }

        return new CalendarDistribution(daysOfYearBuilder.build(),
                quartersBuilder.build(),
                holidayFlagsBuilder.build(),
                weightsListBuilder.build());
    }

    public static int getIndexForDate(Date date)
    {
        return DAYS_BEFORE_MONTH[isLeapYear(date.getYear()) ? 1 : 0][date.getMonth() - 1] + date.getDay() - 1;
    }

    public static int getQuarterAtIndex(int index)
    {
        return CALENDAR_DISTRIBUTION.quarters.get(index - 1); // number passed in is a 1-based index
    }

    public static int getIsHolidayFlagAtIndex(int index)
    {
        return CALENDAR_DISTRIBUTION.holidayFlags.get(index - 1); // number passed in is a 1-based index
    }

    public static int getWeightForDayNumber(int dayNumber, Weights weights)
    {
        return getWeightForIndex(dayNumber, getWeights(weights));
    }

    public static int getMaxWeight(Weights weights)
    {
        ImmutableList<Integer> weightsList = getWeights(weights);
        return weightsList.get(weightsList.size() - 1);
    }

    public static int pickRandomDayOfYear(Weights weights, RandomNumberStream stream)
    {
        return pickRandomValue(CALENDAR_DISTRIBUTION.daysOfYear, getWeights(weights), stream);
    }

    private static ImmutableList<Integer> getWeights(Weights weights)
    {
        return CALENDAR_DISTRIBUTION.weightLists.get(weights.ordinal());
    }

    public enum Weights
    {
        UNIFORM,
        UNIFORM_LEAP_YEAR,
        SALES,
        SALES_LEAP_YEAR,
        RETURNS,
        RETURNS_LEAP_YEAR,
        COMBINED_SKEW,
        LOW,
        MEDIUM,
        HIGH
    }
}
