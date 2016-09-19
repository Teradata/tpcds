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

package com.teradata.tpcds.generator;

import com.teradata.tpcds.Table;

import static com.teradata.tpcds.Table.SHIP_MODE;

public enum ShipModeGeneratorColumn
        implements GeneratorColumn
{
    SM_SHIP_MODE_SK(252, 1),
    SM_SHIP_MODE_ID(253, 1),
    SM_TYPE(254, 1),
    SM_CODE(255, 1),
    SM_CONTRACT(256, 21),
    SM_CARRIER(257, 1),
    SM_NULLS(258, 2);

    private final int globalColumnNumber;
    private final int seedsPerRow;

    ShipModeGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return SHIP_MODE;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }

    @Override
    public int getSeedsPerRow()
    {
        return seedsPerRow;
    }
}
