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

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class ScalingInfo
{
    private static final int[] DEFINED_SCALES = {1, 10, 100, 300, 1000, 3000, 10000, 30000, 100000};
    private int multiplier;
    private ScalingModel scalingModel;
    private Map<Integer, Integer> scalesToRowCountsMap;
    private int updatePercentage;

    public ScalingInfo(int multiplier, ScalingModel scalingModel, int[] rowCountsPerScale, int updatePercentage)
    {
        checkArgument(multiplier >= 0, "multiplier is not greater than or equal to 0");
        checkArgument(updatePercentage >= 0, "updatePrecentage is not greater than or equal to zero");
        this.multiplier = multiplier;
        this.scalingModel = requireNonNull(scalingModel);
        this.updatePercentage = updatePercentage;

        checkArgument(rowCountsPerScale.length == DEFINED_SCALES.length);
        scalesToRowCountsMap = new HashMap<>(DEFINED_SCALES.length);
        for (int i = 0; i < rowCountsPerScale.length; i++) {
            checkArgument(rowCountsPerScale[i] >= 0, "row counts cannot be negative");
            scalesToRowCountsMap.put(DEFINED_SCALES[i], rowCountsPerScale[i]);
        }
    }

    public int getMultiplier()
    {
        return multiplier;
    }

    public long getRowCountForScale(int scale)
    {
        checkArgument(scale <= 100000, "scale must be less than 100000");
        if (scalesToRowCountsMap.containsKey(scale)) {
            return scalesToRowCountsMap.get(scale);
        }

        // get the scaling model for the table
        switch (scalingModel) {
            case STATIC:
                return computeCountUsingStaticScale();
            case LINEAR:
                return computeCountUsingLinearScale(scale);
            case LOGARITHMIC:
                return computeCountUsingLogScale(scale);
            default:
                throw new TpcdsException("unexpected value for scaling model: " + scalingModel);
        }
    }

    private long computeCountUsingStaticScale()
    {
        return getRowCountForScale(1);
    }

    private long computeCountUsingLogScale(int scale)
    {
        int scaleSlot = getScaleSlot(scale);
        long delta = getRowCountForScale(DEFINED_SCALES[scaleSlot]) - getRowCountForScale(DEFINED_SCALES[scaleSlot - 1]);
        float floatOffset = (float) (scale - DEFINED_SCALES[scaleSlot - 1]) / (float) (DEFINED_SCALES[scaleSlot] - DEFINED_SCALES[scaleSlot - 1]);

        return (int) (floatOffset * (float) delta) + getRowCountForScale(DEFINED_SCALES[0]);
    }

    private static int getScaleSlot(int scale)
    {
        for (int i = 0; i < DEFINED_SCALES.length; i++) {
            if (scale <= DEFINED_SCALES[i]) {
                return i;
            }
        }

        // shouldn't be able to get here because we checked the scale argument;
        throw new TpcdsException("scale was greater than max scale");
    }

    private long computeCountUsingLinearScale(int scale)
    {
        long rowCount = 0;
        int targetGB = scale;

        for (int i = DEFINED_SCALES.length - 1; i >= 0; i--) {  // work from large scales down
            // use the defined rowcounts to build up the target GB volume
            while (targetGB >= DEFINED_SCALES[i]) {
                rowCount += getRowCountForScale(DEFINED_SCALES[i]);
                targetGB -= DEFINED_SCALES[i];
            }
        }
        return rowCount;
    }

    public enum ScalingModel
    {
        STATIC,
        LINEAR,
        LOGARITHMIC
    }
}
