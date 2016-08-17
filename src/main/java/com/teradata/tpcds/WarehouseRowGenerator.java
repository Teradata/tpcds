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

import com.teradata.tpcds.type.Address;

import static com.teradata.tpcds.BusinessKeyGenerator.makeBusinessKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.column.WarehouseColumn.W_NULLS;
import static com.teradata.tpcds.column.WarehouseColumn.W_WAREHOUSE_ADDRESS;
import static com.teradata.tpcds.column.WarehouseColumn.W_WAREHOUSE_NAME;
import static com.teradata.tpcds.column.WarehouseColumn.W_WAREHOUSE_SQ_FT;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomText;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Address.makeAddressForColumn;

public class WarehouseRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(W_NULLS);
        long wWarehouseSk = rowNumber;
        String wWarehouseId = makeBusinessKey(rowNumber);
        String wWarehouseName = generateRandomText(10, 20, W_WAREHOUSE_NAME.getRandomNumberStream());
        int wWarehouseSqFt = generateUniformRandomInt(50000, 1000000, W_WAREHOUSE_SQ_FT.getRandomNumberStream());
        Address wAddress = makeAddressForColumn(W_WAREHOUSE_ADDRESS, session.getScaling());

        return new RowGeneratorResult(new WarehouseRow(nullBitMap, wWarehouseSk, wWarehouseId, wWarehouseName, wWarehouseSqFt, wAddress));
    }

    @Override
    public void reset() {}
}
