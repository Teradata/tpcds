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

package com.teradata.tpcds.row.generator;

import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Session;
import com.teradata.tpcds.SlowlyChangingDimensionUtils;
import com.teradata.tpcds.row.InventoryRow;

import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.column.InventoryGeneratorColumn.INV_NULLS;
import static com.teradata.tpcds.column.InventoryGeneratorColumn.INV_QUANTITY_ON_HAND;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MINIMUM;

public class InventoryRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(INV_NULLS);
        int index = (int) rowNumber - 1;
        Scaling scaling = session.getScaling();
        long itemCount = scaling.getIdCount(ITEM);

        long invItemSk = (index % itemCount) + 1;
        index /= (int) itemCount;

        long warehouseCount = scaling.getIdCount(WAREHOUSE);

        long invWarehouseSk = (index % warehouseCount) + 1;
        index /= (int) warehouseCount;

        long invDateSk = JULIAN_DATE_MINIMUM + (index * 7);   // inventory is updated weekly

        // The join between item and inventory is tricky. The item_id selected above identifies a unique part num
        // but item is a slowly changing dimension, so we need to account for that in selecting the surrogate key to join with
        invItemSk = SlowlyChangingDimensionUtils.matchSurrogateKey(invItemSk, invDateSk, ITEM, scaling);

        int invQuantityOnHand = generateUniformRandomInt(0, 1000, INV_QUANTITY_ON_HAND.getRandomNumberStream());

        return new RowGeneratorResult(new InventoryRow(nullBitMap, invDateSk, invItemSk, invWarehouseSk, invQuantityOnHand));
    }

    @Override
    public void reset() {}
}
