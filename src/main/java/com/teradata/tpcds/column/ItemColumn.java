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

import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum ItemColumn
        implements Column
{
    I_ITEM_SK(IDENTIFIER),
    I_ITEM_ID(character(16)),
    I_REC_START_DATE(DATE),
    I_REC_END_DATE(DATE),
    I_ITEM_DESC(varchar(200)),
    I_CURRENT_PRICE(decimal(7, 2)),
    I_WHOLESALE_COST(decimal(7, 2)),
    I_BRAND_ID(INTEGER),
    I_BRAND(character(50)),
    I_CLASS_ID(INTEGER),
    I_CLASS(character(50)),
    I_CATEGORY_ID(INTEGER),
    I_CATEGORY(character(50)),
    I_MANUFACT_ID(INTEGER),
    I_MANUFACT(character(50)),
    I_SIZE(character(20)),
    I_FORMULATION(character(20)),
    I_COLOR(character(20)),
    I_UNITS(character(10)),
    I_CONTAINER(character(10)),
    I_MANAGER_ID(INTEGER),
    I_PRODUCT_NAME(character(50));

    private final ColumnType type;

    ItemColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return ITEM;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }

    @Override
    public ColumnType getType()
    {
        return type;
    }
}
