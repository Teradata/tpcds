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

public enum WebReturnsColumn
        implements Column
{
    WR_RETURNED_DATE_SK(),
    WR_RETURNED_TIME_SK(),
    WR_ITEM_SK(),
    WR_REFUNDED_CUSTOMER_SK(),
    WR_REFUNDED_CDEMO_SK(),
    WR_REFUNDED_HDEMO_SK(),
    WR_REFUNDED_ADDR_SK(),
    WR_RETURNING_CUSTOMER_SK(),
    WR_RETURNING_CDEMO_SK(),
    WR_RETURNING_HDEMO_SK(),
    WR_RETURNING_ADDR_SK(),
    WR_WEB_PAGE_SK(),
    WR_REASON_SK(),
    WR_ORDER_NUMBER(),
    WR_RETURN_QUANTITY(),
    WR_RETURN_AMT(),
    WR_RETURN_TAX(),
    WR_RETURN_AMT_INC_TAX(),
    WR_FEE(),
    WR_RETURN_SHIP_COST(),
    WR_REFUNDED_CASH(),
    WR_REVERSED_CHARGE(),
    WR_ACCOUNT_CREDIT(),
    WR_NET_LOSS();

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
}
