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

import static com.teradata.tpcds.Table.WEB_SALES;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.decimal;

public enum WebSalesColumn
        implements Column
{
    WS_SOLD_DATE_SK(IDENTIFIER),
    WS_SOLD_TIME_SK(IDENTIFIER),
    WS_SHIP_DATE_SK(IDENTIFIER),
    WS_ITEM_SK(IDENTIFIER),
    WS_BILL_CUSTOMER_SK(IDENTIFIER),
    WS_BILL_CDEMO_SK(IDENTIFIER),
    WS_BILL_HDEMO_SK(IDENTIFIER),
    WS_BILL_ADDR_SK(IDENTIFIER),
    WS_SHIP_CUSTOMER_SK(IDENTIFIER),
    WS_SHIP_CDEMO_SK(IDENTIFIER),
    WS_SHIP_HDEMO_SK(IDENTIFIER),
    WS_SHIP_ADDR_SK(IDENTIFIER),
    WS_WEB_PAGE_SK(IDENTIFIER),
    WS_WEB_SITE_SK(IDENTIFIER),
    WS_SHIP_MODE_SK(IDENTIFIER),
    WS_WAREHOUSE_SK(IDENTIFIER),
    WS_PROMO_SK(IDENTIFIER),
    WS_ORDER_NUMBER(IDENTIFIER),
    WS_QUANTITY(INTEGER),
    WS_WHOLESALE_COST(decimal(7, 2)),
    WS_LIST_PRICE(decimal(7, 2)),
    WS_SALES_PRICE(decimal(7, 2)),
    WS_EXT_DISCOUNT_AMT(decimal(7, 2)),
    WS_EXT_SALES_PRICE(decimal(7, 2)),
    WS_EXT_WHOLESALE_COST(decimal(7, 2)),
    WS_EXT_LIST_PRICE(decimal(7, 2)),
    WS_EXT_TAX(decimal(7, 2)),
    WS_COUPON_AMT(decimal(7, 2)),
    WS_EXT_SHIP_COST(decimal(7, 2)),
    WS_NET_PAID(decimal(7, 2)),
    WS_NET_PAID_INC_TAX(decimal(7, 2)),
    WS_NET_PAID_INC_SHIP(decimal(7, 2)),
    WS_NET_PAID_INC_SHIP_TAX(decimal(7, 2)),
    WS_NET_PROFIT(decimal(7, 2));

    private final ColumnType type;

    WebSalesColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return WEB_SALES;
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
