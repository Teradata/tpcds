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

import java.util.EnumMap;
import java.util.Map;

import static com.teradata.tpcds.Table.INVENTORY;
import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.Table.S_INVENTORY;
import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MAXIMUM;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MINIMUM;

public class Scaling
{
    private final int scale;
    private final Map<Table, Long> tableToRowCountMap = new EnumMap<>(Table.class);

    public Scaling(int scale)
    {
        this.scale = scale;

        for (Table table : Table.values()) {
            ScalingInfo scalingInfo = table.getScalingInfo();
            long baseRowCount = scalingInfo.getRowCountForScale(scale);

            // now adjust for the multiplier
            int multiplier = table.keepsHistory() ? 2 : 1;
            for (int i = 1; i <= scalingInfo.getMultiplier(); i++) {
                multiplier *= 10;
            }
            tableToRowCountMap.put(table, baseRowCount * multiplier);
        }
    }

    public long getRowCount(Table table)
    {
        if (table == INVENTORY) {
            return scaleInventory();
        }

        if (table == S_INVENTORY) {
            return getIdCount(ITEM) * getRowCount(WAREHOUSE) * 6;
        }
        return tableToRowCountMap.get(table);
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
}
