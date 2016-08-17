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

package com.teradata.tpcds.row;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.ShipModeColumn.SM_CARRIER;
import static com.teradata.tpcds.column.ShipModeColumn.SM_CODE;
import static com.teradata.tpcds.column.ShipModeColumn.SM_CONTRACT;
import static com.teradata.tpcds.column.ShipModeColumn.SM_SHIP_MODE_ID;
import static com.teradata.tpcds.column.ShipModeColumn.SM_SHIP_MODE_SK;
import static com.teradata.tpcds.column.ShipModeColumn.SM_TYPE;

public class ShipModeRow
        extends TableRowWithNulls
{
    private final long smShipModeSk;
    private final String smShipModeId;
    private final String smType;
    private final String smCode;
    private final String smCarrier;
    private final String smContract;

    public ShipModeRow(long nullBitMap, long smShipModeSk, String smShipModeId, String smType, String smCode, String smCarrier, String smContract)
    {
        super(nullBitMap, SM_SHIP_MODE_SK);
        this.smShipModeSk = smShipModeSk;
        this.smShipModeId = smShipModeId;
        this.smType = smType;
        this.smCode = smCode;
        this.smCarrier = smCarrier;
        this.smContract = smContract;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(smShipModeSk, SM_SHIP_MODE_SK),
                getStringOrNull(smShipModeId, SM_SHIP_MODE_ID),
                getStringOrNull(smType, SM_TYPE),
                getStringOrNull(smCode, SM_CODE),
                getStringOrNull(smCarrier, SM_CARRIER),
                getStringOrNull(smContract, SM_CONTRACT));
    }
}
