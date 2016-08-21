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

import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.decimal;

public enum WebReturnsColumn
        implements Column
{
    WR_RETURNED_DATE_SK(IDENTIFIER),
    WR_RETURNED_TIME_SK(IDENTIFIER),
    WR_ITEM_SK(IDENTIFIER),
    WR_REFUNDED_CUSTOMER_SK(IDENTIFIER),
    WR_REFUNDED_CDEMO_SK(IDENTIFIER),
    WR_REFUNDED_HDEMO_SK(IDENTIFIER),
    WR_REFUNDED_ADDR_SK(IDENTIFIER),
    WR_RETURNING_CUSTOMER_SK(IDENTIFIER),
    WR_RETURNING_CDEMO_SK(IDENTIFIER),
    WR_RETURNING_HDEMO_SK(IDENTIFIER),
    WR_RETURNING_ADDR_SK(IDENTIFIER),
    WR_WEB_PAGE_SK(IDENTIFIER),
    WR_REASON_SK(IDENTIFIER),
    WR_ORDER_NUMBER(IDENTIFIER),
    WR_RETURN_QUANTITY(INTEGER),
    WR_RETURN_AMT(decimal(7, 2)),
    WR_RETURN_TAX(decimal(7, 2)),
    WR_RETURN_AMT_INC_TAX(decimal(7, 2)),
    WR_FEE(decimal(7, 2)),
    WR_RETURN_SHIP_COST(decimal(7, 2)),
    WR_REFUNDED_CASH(decimal(7, 2)),
    WR_REVERSED_CHARGE(decimal(7, 2)),
    WR_ACCOUNT_CREDIT(decimal(7, 2)),
    WR_NET_LOSS(decimal(7, 2));

    private final ColumnType type;

    WebReturnsColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return Table.WEB_RETURNS;
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
