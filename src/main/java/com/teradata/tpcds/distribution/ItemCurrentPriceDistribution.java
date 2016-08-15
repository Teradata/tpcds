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
import com.teradata.tpcds.type.Decimal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.getListFromCommaSeparatedValues;
import static com.teradata.tpcds.type.Decimal.parseDecimal;
import static java.lang.Integer.parseInt;

public class ItemCurrentPriceDistribution
{
    private static final int NUM_WEIGHT_FIELDS = 4;
    private static final int NUM_VALUE_FIELDS = 3;
    private static final String VALUES_AND_WEIGHTS_FILENAME = "item_current_price.dst";
    private static final ItemCurrentPriceDistribution I_CURRENT_PRICE_DISTRIBUTION = buildICurrentPriceDistribution();

    private final ImmutableList<Decimal> mins;
    private final ImmutableList<Decimal> maxes;
    private final ImmutableList<ImmutableList<Integer>> weightLists;

    private ItemCurrentPriceDistribution(ImmutableList<Decimal> mins, ImmutableList<Decimal> maxes, ImmutableList<ImmutableList<Integer>> weightLists)
    {
        this.mins = mins;
        this.maxes = maxes;
        this.weightLists = weightLists;
    }

    private static ItemCurrentPriceDistribution buildICurrentPriceDistribution()
    {
        ImmutableList.Builder<Decimal> minsBuilder = ImmutableList.builder();
        ImmutableList.Builder<Decimal> maxesBuilder = ImmutableList.builder();

        List<WeightsBuilder> weightsBuilders = new ArrayList<>(NUM_WEIGHT_FIELDS);
        for (int i = 0; i < NUM_WEIGHT_FIELDS; i++) {
            weightsBuilders.add(new WeightsBuilder());
        }

        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %s: %s", fields.size(), fields);

            List<String> values = getListFromCommaSeparatedValues(fields.get(0));
            checkState(values.size() == NUM_VALUE_FIELDS, "Expected line to contain %s values, but it contained %s, %s", NUM_VALUE_FIELDS, values.size(), values);

            // indices are never used
            minsBuilder.add(parseDecimal(values.get(1)));
            maxesBuilder.add(parseDecimal(values.get(2)));

            List<String> weights = getListFromCommaSeparatedValues(fields.get(1));
            checkState(weights.size() == NUM_WEIGHT_FIELDS, "Expected line to contain %s weights, but it contained %s, %s", NUM_WEIGHT_FIELDS, weights.size(), weights);
            for (int i = 0; i < weights.size(); i++) {
                weightsBuilders.get(i).computeAndAddNextWeight(parseInt(weights.get(i)));
            }
        }

        ImmutableList.Builder<ImmutableList<Integer>> weightsListBuilder = ImmutableList.<ImmutableList<Integer>>builder();
        for (WeightsBuilder weightsBuilder : weightsBuilders) {
            weightsListBuilder.add(weightsBuilder.build());
        }

        return new ItemCurrentPriceDistribution(minsBuilder.build(),
                maxesBuilder.build(),
                weightsListBuilder.build());
    }

    public static List<Decimal> pickRandomCurrentPriceRange(RandomNumberStream randomNumberStream)
    {
        int index = DistributionUtils.pickRandomIndex(I_CURRENT_PRICE_DISTRIBUTION.weightLists.get(0), randomNumberStream);
        return ImmutableList.of(I_CURRENT_PRICE_DISTRIBUTION.mins.get(index), I_CURRENT_PRICE_DISTRIBUTION.maxes.get(index));
    }
}
