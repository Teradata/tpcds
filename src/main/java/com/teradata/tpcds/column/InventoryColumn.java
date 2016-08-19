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
package com.teradata.tpcds.column;

import com.teradata.tpcds.Table;

import static com.teradata.tpcds.Table.INVENTORY;

public enum InventoryColumn
        implements Column
{
    INV_DATE_SK(),
    INV_ITEM_SK(),
    INV_WAREHOUSE_SK(),
    INV_QUANTITY_ON_HAND();

    @Override
    public Table getTable()
    {
        return INVENTORY;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
