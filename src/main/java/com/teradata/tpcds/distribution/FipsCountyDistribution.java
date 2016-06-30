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

public class FipsCountyDistribution
{
    private static final FipsCountyDistribution FIPS_COUNTY_DISTRIBUTION = buildFipsCountyDistribution();
    private static final String VALUES_AND_WEIGHTS_FILENAME = "fips.dst";
    private static final int NUM_WEIGHT_FIELDS = 6;

    private final ImmutableList<Integer> fipsCodes;
    private final ImmutableList<String> counties;
    private final ImmutableList<String> stateAbbreviations;
    private final ImmutableList<String> stateNames;
    private final ImmutableList<Integer> zipPrefixes;
    private final ImmutableList<Integer> gmtOffsets;
    private final ImmutableList<ImmutableList<Integer>> weightsLists;

    public FipsCountyDistribution(ImmutableList<Integer> fipsCodes,
            ImmutableList<String> counties,
            ImmutableList<String> stateAbbreviations,
            ImmutableList<String> stateNames,
            ImmutableList<Integer> zipPrefixes,
            ImmutableList<Integer> gmtOffsets,
            ImmutableList<ImmutableList<Integer>> weightsLists)
    {
        this.fipsCodes = fipsCodes;
        this.counties = counties;
        this.stateAbbreviations = stateAbbreviations;
        this.stateNames = stateNames;
        this.zipPrefixes = zipPrefixes;
        this.gmtOffsets = gmtOffsets;
        this.weightsLists = weightsLists;
    }

    public static FipsCountyDistribution buildFipsCountyDistribution()
    {
        ImmutableList.Builder<Integer> fipsBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> countiesBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> stateAbbreviationsBuilder = ImmutableList.builder();
        ImmutableList.Builder<String> stateNamesBuilder = ImmutableList.builder();
        ImmutableList.Builder<Integer> zipPrefixesBuilder = ImmutableList.builder();
        ImmutableList.Builder<Integer> gmtOffsetsBuilder = ImmutableList.builder();

        List<WeightsBuilder> weightsBuilders = new ArrayList<>(NUM_WEIGHT_FIELDS);
        for (int i = 0; i < NUM_WEIGHT_FIELDS; i++) {
            weightsBuilders.add(new WeightsBuilder());
        }

        Iterator<List<String>> iterator = getDistributionIterator(VALUES_AND_WEIGHTS_FILENAME);
        while (iterator.hasNext()) {
            List<String> fields = iterator.next();
            checkState(fields.size() == 2, "Expected line to contain 2 parts but it contains %d: %s", fields.size(), fields);

            List<String> values = getListFromCommaSeparatedValues(fields.get(0));
            checkState(values.size() == 6, "Expected line to contain 6 values, but it contained %d, %s", values.size(), values);

            fipsBuilder.add(Integer.parseInt(values.get(0)));
            countiesBuilder.add(values.get(1));
            stateAbbreviationsBuilder.add(values.get(2));
            stateNamesBuilder.add(values.get(3));
            zipPrefixesBuilder.add(Integer.parseInt(values.get(4)));
            gmtOffsetsBuilder.add(Integer.parseInt(values.get(5)));

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

        return new FipsCountyDistribution(fipsBuilder.build(),
                countiesBuilder.build(),
                stateAbbreviationsBuilder.build(),
                stateNamesBuilder.build(),
                zipPrefixesBuilder.build(),
                gmtOffsetsBuilder.build(),
                weightsListBuilder.build());
    }

    public static int pickRandomIndex(FipsWeights weights, RandomNumberStream stream)
    {
        return DistributionUtils.pickRandomIndex(FIPS_COUNTY_DISTRIBUTION.weightsLists.get(weights.ordinal()), stream);
    }

    public static String getCountyAtIndex(int index)
    {
        return FIPS_COUNTY_DISTRIBUTION.counties.get(index);
    }

    public static String getStateAbbreviationAtIndex(int index)
    {
        return FIPS_COUNTY_DISTRIBUTION.stateAbbreviations.get(index);
    }

    public static int getZipPrefixAtIndex(int index)
    {
        return FIPS_COUNTY_DISTRIBUTION.zipPrefixes.get(index);
    }

    public static int getGmtOffsetAtIndex(int index)
    {
        return FIPS_COUNTY_DISTRIBUTION.gmtOffsets.get(index);
    }

    public enum FipsWeights
    {
        UNIFORM,
        POPULATION,
        TIMEZONE,
        IN_ZONE_1,
        IN_ZONE_2,
        IN_ZONE_3
    }
}
