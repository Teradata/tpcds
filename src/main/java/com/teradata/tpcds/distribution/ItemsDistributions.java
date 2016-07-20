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
import com.teradata.tpcds.random.RandomNumberStream;

import java.util.List;

import static com.teradata.tpcds.distribution.IntValuesDistribution.buildIntValuesDistribution;
import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public final class ItemsDistributions
{
    public static final IntValuesDistribution ITEM_MANAGER_ID_DISTRIBUTION = buildIntValuesDistribution("item_manager_id.dst", 3, 4);
    public static final IntValuesDistribution ITEM_MANUFACT_ID_DISTRIBUTION = buildIntValuesDistribution("item_manufact_id.dst", 3, 4);
    public static final StringValuesDistribution SIZES_DISTRIBUTION = buildStringValuesDistribution("sizes.dst", 1, 3);
    public static final StringValuesDistribution COLORS_DISTRIBUTION = buildStringValuesDistribution("colors.dst", 1, 5);
    public static final StringValuesDistribution UNITS_DISTRIBUTION = buildStringValuesDistribution("units.dst", 1, 1);
    public static final StringValuesDistribution BRAND_SYLLABLES_DISTRIBUTION = buildStringValuesDistribution("brand_syllables.dst", 1, 1);

    private ItemsDistributions() {}

    public static List<Integer> pickRandomManagerIdRange(IdWeights idWeights, RandomNumberStream stream)
    {
        int index = ITEM_MANAGER_ID_DISTRIBUTION.pickRandomIndex(idWeights.ordinal(), stream);
        return ImmutableList.of(ITEM_MANAGER_ID_DISTRIBUTION.getValueAtIndex(1, index), ITEM_MANAGER_ID_DISTRIBUTION.getValueAtIndex(2, index));
    }

    public static List<Integer> pickRandomManufactIdRange(IdWeights idWeights, RandomNumberStream stream)
    {
        int index = ITEM_MANUFACT_ID_DISTRIBUTION.pickRandomIndex(idWeights.ordinal(), stream);
        return ImmutableList.of(ITEM_MANUFACT_ID_DISTRIBUTION.getValueAtIndex(1, index), ITEM_MANUFACT_ID_DISTRIBUTION.getValueAtIndex(2, index));
    }

    public static String pickRandomSize(SizeWeights sizeWeights, RandomNumberStream stream)
    {
        return SIZES_DISTRIBUTION.pickRandomValue(0, sizeWeights.ordinal(), stream);
    }

    public static String pickRandomColor(ColorsWeights colorsWeights, RandomNumberStream stream)
    {
        return COLORS_DISTRIBUTION.pickRandomValue(0, colorsWeights.ordinal(), stream);
    }

    public static String pickRandomUnit(RandomNumberStream stream)
    {
        return UNITS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public enum IdWeights
    {
        UNIFIED,
        LOW,
        MEDIUM,
        HIGH
    }

    public enum SizeWeights
    {
        UNIFIED,
        NO_SIZE,
        SIZED,
    }

    public enum ColorsWeights
    {
        UNIFORM,
        SKEWED,
        LOW_LIKELIHOOD,
        MEDIUM_LIKELIHOOD,
        HIGH_LIKELIHOOD
    }
}
