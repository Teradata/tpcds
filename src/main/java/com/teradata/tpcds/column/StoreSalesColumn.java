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

import static com.teradata.tpcds.Table.STORE_SALES;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.decimal;

public enum StoreSalesColumn
        implements Column
{
    SS_SOLD_DATE_SK(IDENTIFIER),
    SS_SOLD_TIME_SK(IDENTIFIER),
    SS_ITEM_SK(IDENTIFIER),
    SS_CUSTOMER_SK(IDENTIFIER),
    SS_CDEMO_SK(IDENTIFIER),
    SS_HDEMO_SK(IDENTIFIER),
    SS_ADDR_SK(IDENTIFIER),
    SS_STORE_SK(IDENTIFIER),
    SS_PROMO_SK(IDENTIFIER),
    SS_TICKET_NUMBER(IDENTIFIER),
    SS_QUANTITY(INTEGER),
    SS_WHOLESALE_COST(decimal(7, 2)),
    SS_LIST_PRICE(decimal(7, 2)),
    SS_SALES_PRICE(decimal(7, 2)),
    SS_EXT_DISCOUNT_AMT(decimal(7, 2)),
    SS_EXT_SALES_PRICE(decimal(7, 2)),
    SS_EXT_WHOLESALE_COST(decimal(7, 2)),
    SS_EXT_LIST_PRICE(decimal(7, 2)),
    SS_EXT_TAX(decimal(7, 2)),
    SS_COUPON_AMT(decimal(7, 2)),
    SS_NET_PAID(decimal(7, 2)),
    SS_NET_PAID_INC_TAX(decimal(7, 2)),
    SS_NET_PROFIT(decimal(7, 2));

    private final ColumnType type;

    StoreSalesColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return STORE_SALES;
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
