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
    private static final double[] DEFINED_SCALES = {0, 1, 10, 100, 300, 1000, 3000, 10000, 30000, 100000};
    private int multiplier;
    private ScalingModel scalingModel;
    private Map<Double, Integer> scalesToRowCountsMap;
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

    public long getRowCountForScale(double scale)
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

    private long computeCountUsingLogScale(double scale)
    {
        int scaleSlot = getScaleSlot(scale);
        long delta = getRowCountForScale(DEFINED_SCALES[scaleSlot]) - getRowCountForScale(DEFINED_SCALES[scaleSlot - 1]);
        double floatOffset = (scale - DEFINED_SCALES[scaleSlot - 1]) / (DEFINED_SCALES[scaleSlot] - DEFINED_SCALES[scaleSlot - 1]);

        long baseRowCount = 0;
        if (scale < 1.0) {
            baseRowCount = getRowCountForScale(DEFINED_SCALES[0]);
        }
        else {
            baseRowCount = getRowCountForScale(DEFINED_SCALES[1]);
        }
        long count = (long) (floatOffset * (float) delta) + baseRowCount;
        return count == 0 ? 1 : count;
    }

    private static int getScaleSlot(double scale)
    {
        for (int i = 0; i < DEFINED_SCALES.length; i++) {
            if (scale <= DEFINED_SCALES[i]) {
                return i;
            }
        }

        // shouldn't be able to get here because we checked the scale argument;
        throw new TpcdsException("scale was greater than max scale");
    }

    private long computeCountUsingLinearScale(double scale)
    {
        long rowCount = 0;
        double targetGB = scale;

        if (scale < 1) {
            rowCount = Math.round(scale * getRowCountForScale(DEFINED_SCALES[1]));
            if (rowCount == 0) {
                return 1;
            }
            return rowCount;
        }

        for (int i = DEFINED_SCALES.length - 1; i > 0; i--) {  // work from large scales down
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
