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

import static com.teradata.tpcds.Table.CATALOG_SALES;

public enum CatalogSalesColumn
        implements Column
{
    CS_SOLD_DATE_SK(),
    CS_SOLD_TIME_SK(),
    CS_SHIP_DATE_SK(),
    CS_BILL_CUSTOMER_SK(),
    CS_BILL_CDEMO_SK(),
    CS_BILL_HDEMO_SK(),
    CS_BILL_ADDR_SK(),
    CS_SHIP_CUSTOMER_SK(),
    CS_SHIP_CDEMO_SK(),
    CS_SHIP_HDEMO_SK(),
    CS_SHIP_ADDR_SK(),
    CS_CALL_CENTER_SK(),
    CS_CATALOG_PAGE_SK(),
    CS_SHIP_MODE_SK(),
    CS_WAREHOUSE_SK(),
    CS_ITEM_SK(),
    CS_PROMO_SK(),
    CS_ORDER_NUMBER(),
    CS_QUANTITY(),
    CS_WHOLESALE_COST(),
    CS_LIST_PRICE(),
    CS_SALES_PRICE(),
    CS_EXT_DISCOUNT_AMT(),
    CS_EXT_SALES_PRICE(),
    CS_EXT_WHOLESALE_COST(),
    CS_EXT_LIST_PRICE(),
    CS_EXT_TAX(),
    CS_COUPON_AMT(),
    CS_EXT_SHIP_COST(),
    CS_NET_PAID(),
    CS_NET_PAID_INC_TAX(),
    CS_NET_PAID_INC_SHIP(),
    CS_NET_PAID_INC_SHIP_TAX(),
    CS_NET_PROFIT();

    @Override
    public Table getTable()
    {
        return CATALOG_SALES;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
