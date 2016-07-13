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

import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.INVENTORY;

public enum InventoryColumn
        implements Column
{
    INV_DATE_SK(198, 1),
    INV_ITEM_SK(199, 1),
    INV_WAREHOUSE_SK(200, 1),
    INV_QUANTITY_ON_HAND(201, 1),
    INV_NULLS(202, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    InventoryColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return INVENTORY;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }
}
