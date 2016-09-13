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

import static com.teradata.tpcds.Table.CATALOG_RETURNS;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.decimal;

public enum CatalogReturnsColumn
        implements Column
{
    CR_RETURNED_DATE_SK(IDENTIFIER),
    CR_RETURNED_TIME_SK(IDENTIFIER),
    CR_ITEM_SK(IDENTIFIER),
    CR_REFUNDED_CUSTOMER_SK(IDENTIFIER),
    CR_REFUNDED_CDEMO_SK(IDENTIFIER),
    CR_REFUNDED_HDEMO_SK(IDENTIFIER),
    CR_REFUNDED_ADDR_SK(IDENTIFIER),
    CR_RETURNING_CUSTOMER_SK(IDENTIFIER),
    CR_RETURNING_CDEMO_SK(IDENTIFIER),
    CR_RETURNING_HDEMO_SK(IDENTIFIER),
    CR_RETURNING_ADDR_SK(IDENTIFIER),
    CR_CALL_CENTER_SK(IDENTIFIER),
    CR_CATALOG_PAGE_SK(IDENTIFIER),
    CR_SHIP_MODE_SK(IDENTIFIER),
    CR_WAREHOUSE_SK(IDENTIFIER),
    CR_REASON_SK(IDENTIFIER),
    CR_ORDER_NUMBER(IDENTIFIER),
    CR_RETURN_QUANTITY(INTEGER),
    CR_RETURN_AMOUNT(decimal(7, 2)),
    CR_RETURN_TAX(decimal(7, 2)),
    CR_RETURN_AMT_INC_TAX(decimal(7, 2)),
    CR_FEE(decimal(7, 2)),
    CR_RETURN_SHIP_COST(decimal(7, 2)),
    CR_REFUNDED_CASH(decimal(7, 2)),
    CR_REVERSED_CHARGE(decimal(7, 2)),
    CR_STORE_CREDIT(decimal(7, 2)),
    CR_NET_LOSS(decimal(7, 2));

    private final ColumnType type;

    CatalogReturnsColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return CATALOG_RETURNS;
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

    @Override
    public int getPosition()
    {
        return ordinal();
    }
}
