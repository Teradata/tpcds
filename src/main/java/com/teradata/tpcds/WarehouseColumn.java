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

import static com.teradata.tpcds.Table.WAREHOUSE;

public enum WarehouseColumn
        implements Column
{
    W_WAREHOUSE_SK(351, 1),
    W_WAREHOUSE_ID(352, 1),
    W_WAREHOUSE_NAME(353, 80),
    W_WAREHOUSE_SQ_FT(354, 1),
    W_ADDRESS_STREET_NUM(355, 1),
    W_ADDRESS_STREET_NAME1(356, 1),
    W_ADDRESS_STREET_TYPE(357, 1),
    W_ADDRESS_SUITE_NUM(358, 1),
    W_ADDRESS_CITY(359, 1),
    W_ADDRESS_COUNTY(360, 1),
    W_ADDRESS_STATE(361, 1),
    W_ADDRESS_ZIP(362, 1),
    W_ADDRESS_COUNTRY(363, 1),
    W_ADDRESS_GMT_OFFSET(364, 1),
    W_NULLS(365, 2),
    W_WAREHOUSE_ADDRESS(366, 7);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    WarehouseColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return WAREHOUSE;
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
