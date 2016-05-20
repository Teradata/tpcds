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

import com.teradata.tpcds.distribution.RowCountsDistribution;

import java.util.EnumMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.Table.INVENTORY;
import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.Table.S_INVENTORY;
import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MAXIMUM;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MINIMUM;

public class Scaling
{
    private static final int[] DEFINED_SCALES = {1, 10, 100, 300, 1000, 3000, 10000, 30000, 100000};

    private final int scale;
    private final Map<Table, ScalingInfo> scalingInfosMap = new EnumMap<>(Table.class);

    public Scaling(int scale)
    {
        checkArgument(scale <= 100000, "scale must be less than 10000");
        this.scale = scale;

        for (Table table : Table.values()) {
            long baseRowCount;
            int tableNumber = table.ordinal();
            int tableWeightIndex = tableNumber + 1;
            switch (scale) {
                case 100000:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 9);
                    break;
                case 30000:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 8);
                    break;
                case 10000:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 7);
                    break;
                case 3000:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 6);
                    break;
                case 1000:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 5);
                    break;
                case 300:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 4);
                    break;
                case 100:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 3);
                    break;
                case 10:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 2);
                    break;
                case 1:
                    baseRowCount = RowCountsDistribution.getWeight(tableWeightIndex, 1);
                    break;
                default:
                    // get the scaling model for the table
                    switch (RowCountsDistribution.getMemberInt(tableWeightIndex, 3)) {
                        case 1:
                            baseRowCount = computeCountUsingStaticScale(tableNumber);
                            break;
                        case 2:
                            baseRowCount = computeCountUsingLinearScale(tableNumber);
                            break;
                        case 3:
                            baseRowCount = computeCountUsingLogScale(tableNumber);
                            break;
                        default:
                            throw new TpcdsException("unexpected value for scaling model, expected 1, 2, or 3");
                    }
                    break;
            }

            // now adjust for the multiplier
            int multiplier = table.keepsHistory() ? 2 : 1;

            for (int i = 1; i <= RowCountsDistribution.getMemberInt(tableWeightIndex, 2); i++) {
                multiplier *= 10;
            }
            scalingInfosMap.put(table, new ScalingInfo(baseRowCount * multiplier));
        }
    }

    private long computeCountUsingLogScale(int tableNumber)
    {
        int scaleSlot = getScaleSlot();
        int delta = RowCountsDistribution.getWeight(tableNumber + 1, scaleSlot + 1) - RowCountsDistribution.getWeight(tableNumber + 1, scaleSlot);
        float floatOffset = (float) (scale - DEFINED_SCALES[scaleSlot - 1]) / (float) (DEFINED_SCALES[scaleSlot] - DEFINED_SCALES[scaleSlot - 1]);

        return (int) (floatOffset * (float) delta) + RowCountsDistribution.getWeight(tableNumber + 1, 1);
    }

    private int getScaleSlot()
    {
        for (int i = 0; i < DEFINED_SCALES.length; i++) {
            if (scale <= DEFINED_SCALES[i]) {
                return i;
            }
        }

        // shouldn't be able to get here because we checked the scale argument;
        throw new TpcdsException("scale was greater than max scale");
    }

    private long computeCountUsingStaticScale(int tableNumber)
    {
        return RowCountsDistribution.getWeight(tableNumber + 1, 1);
    }

    private long computeCountUsingLinearScale(int tableNumber)
    {
        long rowCount = 0;
        int targetGB = scale;

        for (int i = DEFINED_SCALES.length - 1; i >= 0; i--) {  // work from large scales down
            // use the defined rowcounts to build up the target GB volume
            while (targetGB >= DEFINED_SCALES[i]) {
                rowCount += RowCountsDistribution.getWeight(tableNumber + 1, i + 1);
                targetGB -= DEFINED_SCALES[i];
            }
        }
        return rowCount;
    }

    public long getRowCount(Table table)
    {
        if (table == INVENTORY) {
            return scaleInventory();
        }

        if (table == S_INVENTORY) {
            return getIdCount(ITEM) * getRowCount(WAREHOUSE) * 6;
        }
        return scalingInfosMap.get(table).baseRowCount;
    }

    public long getIdCount(Table table)
    {
        long rowCount = getRowCount(table);
        if (table.keepsHistory()) {
            long uniqueCount = (rowCount / 6) * 3;
            switch ((int) (rowCount % 6)) {
                case 1:
                    uniqueCount += 1;
                    break;
                case 2:
                case 3:
                    uniqueCount += 2;
                    break;
                case 4:
                case 5:
                    uniqueCount += 3;
                    break;
                default:
                    break;
            }
            return uniqueCount;
        }
        return rowCount;
    }

    private long scaleInventory()
    {
        int nDays;
        nDays = JULIAN_DATE_MAXIMUM - JULIAN_DATE_MINIMUM;
        nDays += 7;  // ndays + 1 + 6.  I'm not sure what this addition is about.
        nDays /= 7; // each item's inventory is updated weekly

        return getIdCount(ITEM) * getRowCount(WAREHOUSE) * nDays;
    }

    public int getScale()
    {
        return scale;
    }

    private static final class ScalingInfo
    {
        private final long baseRowCount;
        private final long nextInsertValue = 0; // dummy value
        private final int updatePercentage = 0; // dummy value
        private final long[] dayRowCount = {}; // dummy value

        ScalingInfo(long baseRowCount)
        {
            checkArgument(baseRowCount > 0, "baseRowCount is not greater than 0");
            this.baseRowCount = baseRowCount;
        }
    }
}
