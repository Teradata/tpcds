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

public final class CallCenterDistributions
{
    private static final StringValuesDistribution CALL_CENTERS_DISTRIBUTION = buildStringValuesDistribution("call_centers.dst", 1, 2);
    private static final StringValuesDistribution CALL_CENTER_CLASSES_DISTRIBUTION = buildStringValuesDistribution("call_center_classes.dst", 1, 1);
    private static final StringValuesDistribution CALL_CENTER_HOURS_DISTRIBUTION = buildStringValuesDistribution("call_center_hours.dst", 1, 1);

    private CallCenterDistributions() {}

    public static String getCallCenterAtIndex(int i)
    {
        return CALL_CENTERS_DISTRIBUTION.getValueAtIndex(0, i);
    }

    public static int getNumberOfCallCenters()
    {
        return CALL_CENTERS_DISTRIBUTION.getSize();
    }

    public static String pickRandomCallCenterClass(RandomNumberStream stream)
    {
        return CALL_CENTER_CLASSES_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }

    public static String pickRandomCallCenterHours(RandomNumberStream stream)
    {
        return CALL_CENTER_HOURS_DISTRIBUTION.pickRandomValue(0, 0, stream);
    }
}
