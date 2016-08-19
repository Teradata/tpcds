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

public enum WebSalesColumn
        implements Column
{
    WS_SOLD_DATE_SK(),
    WS_SOLD_TIME_SK(),
    WS_SHIP_DATE_SK(),
    WS_ITEM_SK(),
    WS_BILL_CUSTOMER_SK(),
    WS_BILL_CDEMO_SK(),
    WS_BILL_HDEMO_SK(),
    WS_BILL_ADDR_SK(),
    WS_SHIP_CUSTOMER_SK(),
    WS_SHIP_CDEMO_SK(),
    WS_SHIP_HDEMO_SK(),
    WS_SHIP_ADDR_SK(),
    WS_WEB_PAGE_SK(),
    WS_WEB_SITE_SK(),
    WS_SHIP_MODE_SK(),
    WS_WAREHOUSE_SK(),
    WS_PROMO_SK(),
    WS_ORDER_NUMBER(),
    WS_QUANTITY(),
    WS_WHOLESALE_COST(),
    WS_LIST_PRICE(),
    WS_SALES_PRICE(),
    WS_EXT_DISCOUNT_AMT(),
    WS_EXT_SALES_PRICE(),
    WS_EXT_WHOLESALE_COST(),
    WS_EXT_LIST_PRICE(),
    WS_EXT_TAX(),
    WS_COUPON_AMT(),
    WS_EXT_SHIP_COST(),
    WS_NET_PAID(),
    WS_NET_PAID_INC_TAX(),
    WS_NET_PAID_INC_SHIP(),
    WS_NET_PAID_INC_SHIP_TAX(),
    WS_NET_PROFIT();

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
}
