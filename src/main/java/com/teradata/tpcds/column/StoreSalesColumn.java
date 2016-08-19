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

public enum StoreSalesColumn
        implements Column
{
    SS_SOLD_DATE_SK(),
    SS_SOLD_TIME_SK(),
    SS_ITEM_SK(),
    SS_CUSTOMER_SK(),
    SS_CDEMO_SK(),
    SS_HDEMO_SK(),
    SS_ADDR_SK(),
    SS_STORE_SK(),
    SS_PROMO_SK(),
    SS_TICKET_NUMBER(),
    SS_QUANTITY(),
    SS_WHOLESALE_COST(),
    SS_LIST_PRICE(),
    SS_SALES_PRICE(),
    SS_EXT_DISCOUNT_AMT(),
    SS_EXT_SALES_PRICE(),
    SS_EXT_WHOLESALE_COST(),
    SS_EXT_LIST_PRICE(),
    SS_EXT_TAX(),
    SS_COUPON_AMT(),
    SS_NET_PAID(),
    SS_NET_PAID_INC_TAX(),
    SS_NET_PROFIT();

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
}
