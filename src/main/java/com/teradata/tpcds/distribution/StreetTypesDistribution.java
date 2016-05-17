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

public class StreetTypesDistribution
{
    private static final String VALUES_AND_WEIGHTS_FILENAME = "street_types.dst";
    private static final ImmutableList<String> STREET_TYPES;
    private static final ImmutableList<Integer> WEIGHTS;

    static {
        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        ImmutableList.Builder<String> streetTypesBuilder = ImmutableList.<String> builder();
        WeightsBuilder weightsBuilder = new WeightsBuilder();

        while(iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);
            streetTypesBuilder.add(fields.get(0));
            weightsBuilder.add(Integer.valueOf(fields.get(1)));
        }

        STREET_TYPES = streetTypesBuilder.build();
        WEIGHTS = weightsBuilder.build();
    }

    private StreetTypesDistribution() {}

    public static String pickRandomStreetType(RandomNumberStream stream)
    {
        return pickRandomValue(STREET_TYPES, WEIGHTS, stream);
    }
}
