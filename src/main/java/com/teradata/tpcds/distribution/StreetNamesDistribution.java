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

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.pickRandomValue;
import static com.teradata.tpcds.distribution.StreetNamesDistribution.WeightType.DEFAULT;

public final class StreetNamesDistribution
{
    private static final String VALUES_AND_WEIGHTS_FILENAME = "street_names.dst";

    private final static ImmutableList<String> STREET_NAMES; // from 1990 census
    private final static ImmutableList<Integer> DEFAULT_WEIGHTS;
    private final static ImmutableList<Integer> HALF_EMPTY_WEIGHTS; // returns an empty entry 50% of the time

    static {
        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        ImmutableList.Builder<String> streetNamesBuilder = ImmutableList.<String>builder();
        WeightsBuilder defaultWeightsBuilder = new WeightsBuilder();
        WeightsBuilder halfEmptyWeightsBuilder = new WeightsBuilder();

        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 3, "Expected line to contain 3 parts but it contains %d: %s", fields.size(), fields);
            streetNamesBuilder.add(fields.get(0));
            defaultWeightsBuilder.add(Integer.valueOf(fields.get(1)));
            halfEmptyWeightsBuilder.add(Integer.valueOf(fields.get(2)));
        }

        STREET_NAMES = streetNamesBuilder.build();
        DEFAULT_WEIGHTS = defaultWeightsBuilder.build();
        HALF_EMPTY_WEIGHTS = halfEmptyWeightsBuilder.build();
    }

    private StreetNamesDistribution() {}

    public static String pickRandomStreetName(WeightType weightType, RandomNumberStream stream)
    {
        return pickRandomValue(STREET_NAMES, weightType == DEFAULT ? DEFAULT_WEIGHTS : HALF_EMPTY_WEIGHTS, stream);
    }

    public enum WeightType
    {
        DEFAULT,
        HALF_EMPTY
    }
}
