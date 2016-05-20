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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.distribution.DistributionUtils.getDistributionIterator;
import static com.teradata.tpcds.distribution.DistributionUtils.getListFromCommaSeparatedValues;

public class StringValuesDistribution
{
    private final ImmutableList<ImmutableList<String>> valuesLists;
    private final ImmutableList<ImmutableList<Integer>> weightsLists;

    public StringValuesDistribution(ImmutableList<ImmutableList<String>> valuesLists, ImmutableList<ImmutableList<Integer>> weightsLists)
    {
        this.valuesLists = valuesLists;
        this.weightsLists = weightsLists;
    }

    public static StringValuesDistribution buildStringValuesDistribution(String valuesAndWeightsFilename, int numValueFields, int numWeightFields)
    {
        Iterator<List<String>> iterator = getDistributionIterator(valuesAndWeightsFilename);

        List<ImmutableList.Builder<String>> valuesBuilders = new ArrayList<>(numValueFields);
        for (int i = 0; i < numValueFields; i++) {
            valuesBuilders.add(ImmutableList.<String>builder());
        }

        List<WeightsBuilder> weightsBuilders = new ArrayList<>(numWeightFields);
        for (int i = 0; i < numWeightFields; i++) {
            weightsBuilders.add(new WeightsBuilder());
        }

        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);

            List<String> values = getListFromCommaSeparatedValues(fields.get(0));
            checkState(values.size() == numValueFields, "Expected line to contain %d values, but it contained %d, %s", numValueFields, values.size(), values);
            for (int i = 0; i < values.size(); i++) {
                valuesBuilders.get(i).add(values.get(i));
            }

            List<String> weights = getListFromCommaSeparatedValues(fields.get(1));
            checkState(weights.size() == numWeightFields, "Expected line to contain %d weights, but it contained %d, %s", numWeightFields, weights.size(), values);
            for (int i = 0; i < weights.size(); i++) {
                weightsBuilders.get(i).computeAndAddNextWeight(Integer.parseInt(weights.get(i)));
            }
        }

        ImmutableList.Builder<ImmutableList<String>> valuesListsBuilder = ImmutableList.<ImmutableList<String>>builder();
        for (ImmutableList.Builder<String> valuesBuilder : valuesBuilders) {
            valuesListsBuilder.add(valuesBuilder.build());
        }
        ImmutableList<ImmutableList<String>> valuesLists = valuesListsBuilder.build();

        ImmutableList.Builder<ImmutableList<Integer>> weightsListBuilder = ImmutableList.<ImmutableList<Integer>>builder();
        for (WeightsBuilder weightsBuilder : weightsBuilders) {
            weightsListBuilder.add(weightsBuilder.build());
        }
        ImmutableList<ImmutableList<Integer>> weightsLists = weightsListBuilder.build();
        return new StringValuesDistribution(valuesLists, weightsLists);
    }

    public String pickRandomValue(int valueIndex, int weightIndex, RandomNumberStream stream)
    {
        checkArgument(valueIndex < valuesLists.size(), "index out of range, max value index is " + (valuesLists.size() - 1));
        checkArgument(weightIndex < weightsLists.size(), "index out of range, max weight index is " + (weightsLists.size() - 1));
        return DistributionUtils.pickRandomValue(valuesLists.get(valueIndex), weightsLists.get(weightIndex), stream);
    }

    public int getSize()
    {
        return valuesLists.get(0).size();
    }

    public String getValueAtIndex(int valueListIndex, int valueIndex)
    {
        return valuesLists.get(valueListIndex).get(valueIndex);
    }

    public String getValueForWeight(int weight, int valueListIndex, int weightListIndex)
    {
        checkArgument(valueListIndex < valuesLists.size(), "index out of range, max value index is " + (valuesLists.size() - 1));
        checkArgument(weightListIndex < weightsLists.size(), "index out of range, max weight index is " + (weightsLists.size() - 1));
        return DistributionUtils.getValueForWeight(weight, valuesLists.get(valueListIndex), weightsLists.get(weightListIndex));
    }
}
