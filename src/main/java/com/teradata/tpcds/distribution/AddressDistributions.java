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

import static com.teradata.tpcds.distribution.AddressDistributions.StreetNamesWeights.DEFAULT;
import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public final class AddressDistributions
{
    private static final StringValuesDistribution STREET_NAMES_DISTRIBUTION = buildStringValuesDistribution("street_names.dst", 1, 2);
    private static final StringValuesDistribution STREET_TYPES_DISTRIBUTION = buildStringValuesDistribution("street_types.dst", 1, 1);
    private static final StringValuesDistribution CITIES_DISTRIBUTION = buildStringValuesDistribution("cities.dst", 1, 6);

    private AddressDistributions() {}

    public static String pickRandomStreetName(StreetNamesWeights streetNamesWeights, RandomNumberStream stream)
    {
        return STREET_NAMES_DISTRIBUTION.pickRandomValue(0, streetNamesWeights.ordinal(), stream);
    }

    public static String pickRandomStreetType(RandomNumberStream stream)
    {
        return STREET_TYPES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomCity(CitiesWeights citiesWeights, RandomNumberStream stream)
    {
        return CITIES_DISTRIBUTION.pickRandomValue(0, citiesWeights.ordinal(), stream);
    }

    public static String getCityAtIndex(int index)
    {
        return CITIES_DISTRIBUTION.getValueAtIndex(0, index);
    }

    public enum StreetNamesWeights
    {
        DEFAULT,
        HALF_EMPTY
    }

    public enum CitiesWeights
    {
        USGS_SKEWED,
        UNIFORM,
        LARGE,
        MEDIUM,
        SMALL,
        UNIFIED_STEP_FUNCTION
    }
}
