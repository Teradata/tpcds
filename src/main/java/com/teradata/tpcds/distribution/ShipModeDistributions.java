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

import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public class ShipModeDistributions
{
    public static final StringValuesDistribution SHIP_MODE_CARRIER_DISTRIBUTION = buildStringValuesDistribution("ship_mode_carrier.dst", 1, 1);
    public static final StringValuesDistribution SHIP_MODE_CODE_DISTRIBUTION = buildStringValuesDistribution("ship_mode_code.dst", 1, 1);
    public static final StringValuesDistribution SHIP_MODE_TYPE_DISTRIBUTION = buildStringValuesDistribution("ship_mode_type.dst", 1, 1);

    private ShipModeDistributions() {}

    public static String getShipModeCarrierAtIndex(int index)
    {
        return SHIP_MODE_CARRIER_DISTRIBUTION.getValueAtIndex(0, index);
    }

    public static String getShipModeCodeForIndexModSize(long index)
    {
        return SHIP_MODE_CODE_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static String getShipModeTypeForIndexModSize(long index)
    {
        return SHIP_MODE_TYPE_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }
}
