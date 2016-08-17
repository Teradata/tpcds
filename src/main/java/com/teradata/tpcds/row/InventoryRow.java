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
import static com.teradata.tpcds.column.InventoryColumn.INV_DATE_SK;
import static com.teradata.tpcds.column.InventoryColumn.INV_ITEM_SK;
import static com.teradata.tpcds.column.InventoryColumn.INV_QUANTITY_ON_HAND;
import static com.teradata.tpcds.column.InventoryColumn.INV_WAREHOUSE_SK;

public class InventoryRow
        extends TableRowWithNulls
{
    private final long invDateSk;
    private final long invItemSk;
    private final long invWarehouseSk;
    private final int invQuantityOnHand;

    public InventoryRow(long nullBitMap, long invDateSk, long invItemSk, long invWarehouseSk, int invQuantityOnHand)
    {
        super(nullBitMap, INV_DATE_SK);
        this.invDateSk = invDateSk;
        this.invItemSk = invItemSk;
        this.invWarehouseSk = invWarehouseSk;
        this.invQuantityOnHand = invQuantityOnHand;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(invDateSk, INV_DATE_SK),
                getStringOrNullForKey(invItemSk, INV_ITEM_SK),
                getStringOrNullForKey(invWarehouseSk, INV_WAREHOUSE_SK),
                getStringOrNull(invQuantityOnHand, INV_QUANTITY_ON_HAND));
    }
}
