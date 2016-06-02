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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.getListFromCommaSeparatedValues;
import static com.teradata.tpcds.distribution.DistributionUtils.pickRandomValue;
import static java.lang.Integer.parseInt;

public class HoursDistribution
{
    private static final int NUM_WEIGHT_FIELDS = Weights.values().length;
    private static final String VALUES_AND_WEIGHTS_FILENAME = "hours.dst";
    private static final HoursDistribution HOURS_DISTRIBUTION = buildHoursDistribution();

    private final ImmutableList<Integer> hours;
    private final ImmutableList<String> amPm;
    private final ImmutableList<String> shifts;
    private final ImmutableList<String> subShifts;
    private final ImmutableList<String> meals;
    private final ImmutableList<ImmutableList<Integer>> weightsLists;

    public HoursDistribution(ImmutableList<Integer> hours, ImmutableList<String> amPm, ImmutableList<String> shifts, ImmutableList<String> subShifts, ImmutableList<String> meals, ImmutableList<ImmutableList<Integer>> weightsLists)
    {
        this.hours = hours;
        this.amPm = amPm;
        this.shifts = shifts;
        this.subShifts = subShifts;
        this.meals = meals;
        this.weightsLists = weightsLists;
    }

    private static HoursDistribution buildHoursDistribution()
    {
        ImmutableList.Builder<Integer> hoursBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> amPmBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> shiftsBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> subShiftsBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> mealsBuilder = ImmutableList.builder();

        List<WeightsBuilder> weightsBuilders = new ArrayList<>(NUM_WEIGHT_FIELDS);
        for (int i = 0; i < NUM_WEIGHT_FIELDS; i++) {
            weightsBuilders.add(new WeightsBuilder());
        }

        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);

            List<String> values = getListFromCommaSeparatedValues(fields.get(0));
            checkState(values.size() == 5, "Expected line to contain 5 values, but it contained %d, %s", values.size(), values);
            hoursBuilder.add(parseInt(values.get(0)));
            amPmBuilder.add(values.get(1));
            shiftsBuilder.add(values.get(2));
            subShiftsBuilder.add(values.get(3));
            mealsBuilder.add(values.get(4));

            List<String> weights = getListFromCommaSeparatedValues(fields.get(1));
            checkState(weights.size() == NUM_WEIGHT_FIELDS, "Expected line to contain %d weights, but it contained %d, %s", NUM_WEIGHT_FIELDS, weights.size(), values);
            for (int i = 0; i < weights.size(); i++) {
                weightsBuilders.get(i).computeAndAddNextWeight(Integer.valueOf(weights.get(i)));
            }
        }

        ImmutableList.Builder<ImmutableList<Integer>> weightsListBuilder = ImmutableList.<ImmutableList<Integer>>builder();
        for (WeightsBuilder weightsBuilder : weightsBuilders) {
            weightsListBuilder.add(weightsBuilder.build());
        }

        return new HoursDistribution(hoursBuilder.build(), amPmBuilder.build(), shiftsBuilder.build(), subShiftsBuilder.build(), mealsBuilder.build(), weightsListBuilder.build());
    }

    public static int pickRandomHour(Weights weights, RandomNumberStream randomNumberStream)
    {
        return pickRandomValue(HOURS_DISTRIBUTION.hours, HOURS_DISTRIBUTION.weightsLists.get(weights.ordinal()), randomNumberStream);
    }

    public enum Weights
    {
        UNIFORM,
        STORE,
        CATALOG_AND_WEB
    }
}
