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

import com.teradata.tpcds.random.RandomNumberStream;

import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public final class NamesDistributions
{
    private static final StringValuesDistribution FIRST_NAMES_DISTRIBUTION = buildStringValuesDistribution("first_names.dst", 1, 3);
    private static final StringValuesDistribution LAST_NAMES_DISTRIBUTION = buildStringValuesDistribution("last_names.dst", 1, 1);
    private static final StringValuesDistribution SALUTATIONS_DISTRIBUTION = buildStringValuesDistribution("salutations.dst", 1, 3);

    private NamesDistributions() {}

    public static String pickRandomFirstName(FirstNamesWeights firstNamesWeights, RandomNumberStream stream)
    {
        return FIRST_NAMES_DISTRIBUTION.pickRandomValue(0, firstNamesWeights.ordinal(), stream);
    }

    public static int getWeightForFirstName(String firstName, FirstNamesWeights firstNamesWeights)
    {
        return FIRST_NAMES_DISTRIBUTION.getWeightForIndex(FIRST_NAMES_DISTRIBUTION.getIndexForValue(firstName, 0), firstNamesWeights.ordinal());
    }

    public static String pickRandomLastName(RandomNumberStream stream)
    {
        return LAST_NAMES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomSalutation(SalutationsWeights salutationsWeights, RandomNumberStream stream)
    {
        return SALUTATIONS_DISTRIBUTION.pickRandomValue(0, salutationsWeights.ordinal(), stream);
    }

    public enum FirstNamesWeights
    {
        MALE_FREQUENCY,
        FEMALE_FREQUENCY,
        GENERAL_FREQUENCY
    }

    public enum SalutationsWeights
    {
        GENDER_NEUTRAL,
        MALE,
        FEMALE
    }
}
