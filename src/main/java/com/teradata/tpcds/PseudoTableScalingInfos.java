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

package com.teradata.tpcds;

import static com.teradata.tpcds.ScalingInfo.ScalingModel.LOGARITHMIC;

public final class PseudoTableScalingInfos
{
    private PseudoTableScalingInfos() {}

    public static final ScalingInfo CONCURRENT_WEB_SITES = new ScalingInfo(0, LOGARITHMIC, new int[]{0, 2, 3, 4, 5, 5, 5, 5, 5, 5}, 0);
    public static final ScalingInfo ACTIVE_CITIES = new ScalingInfo(0, LOGARITHMIC, new int[]{0, 2, 6, 18, 30, 54, 90, 165, 270, 495}, 0);
    public static final ScalingInfo ACTIVE_COUNTIES = new ScalingInfo(0, LOGARITHMIC, new int[]{0, 1, 3, 9, 15, 27, 45, 81, 135, 245}, 0);
}
