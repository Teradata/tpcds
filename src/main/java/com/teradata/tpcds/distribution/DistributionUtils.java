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

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import com.teradata.tpcds.TpcdsException;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterators.filter;
import static com.google.common.collect.Iterators.transform;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;

public final class DistributionUtils
{
    private DistributionUtils(){}

    protected final static class WeightsBuilder {
        ImmutableList.Builder<Integer> weightsBuilder = ImmutableList.<Integer> builder();
        int previousWeight = 0;

        public WeightsBuilder computeAndAddNextWeight(int weight){
            checkArgument(weight >= 0, "Weight cannot be negative.");
            int newWeight = previousWeight + weight;
            weightsBuilder.add(newWeight);
            previousWeight = newWeight;
            return this;
        }

        public ImmutableList<Integer> build() {
            return weightsBuilder.build();
        }
    }

    protected static Iterator<List<String>> getDistributionIterator(String filename)
    {
        URL resource = Resources.getResource(DistributionUtils.class, filename);
        checkState(resource != null, "Distribution file '%s' not found", filename);
        try {
            // get an iterator that iterates over lists of the comma separated values from the distribution files
            return transform(
                    filter(Resources.asCharSource(resource, Charsets.UTF_8).readLines().iterator(), line -> {
                        line = line.trim();
                        return !line.isEmpty() && !line.startsWith("--");
                    }), line -> ImmutableList.copyOf(Splitter.on(',').trimResults().split(line)));
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    protected static <T> T pickRandomValue(List<T> values, List<Integer> weights, RandomNumberStream randomNumberStream)
    {
        int weight = generateUniformRandomInt(1, weights.get(weights.size() - 1), randomNumberStream);
        return getSpecificValue(weight, values, weights);
    }

    protected static <T> T getSpecificValue(int weight, List<T> values, List<Integer> weights)
    {
        checkArgument(values.size() == weights.size());
        for (int index = 0; index < weights.size(); index++) {
            if (weight <= weights.get(index)) {
                return values.get(index);
            }
        }

        throw new TpcdsException("random weight was greater than max weight");
    }

    protected static int getWeightForIndex(int index, List<Integer> weights) {
        checkArgument(index < weights.size(), "index larger than distribution");
        return weights.get(index) - weights.get(index -1);  // reverse the accumulation of weights.
    }

    protected static <T> int getIndexForValue(T value, List<T> values) {
        int index = values.indexOf(value);
        if (index < 0) {
            throw new TpcdsException("value not found in distribution:" + value);
        }
        return index;
    }
}
