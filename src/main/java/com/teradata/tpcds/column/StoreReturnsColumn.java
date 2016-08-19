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

import static com.teradata.tpcds.Table.STORE_RETURNS;

public enum StoreReturnsColumn
        implements Column
{
    SR_RETURNED_DATE_SK(),
    SR_RETURN_TIME_SK(),
    SR_ITEM_SK(),
    SR_CUSTOMER_SK(),
    SR_CDEMO_SK(),
    SR_HDEMO_SK(),
    SR_ADDR_SK(),
    SR_STORE_SK(),
    SR_REASON_SK(),
    SR_TICKET_NUMBER(),
    SR_RETURN_QUANTITY(),
    SR_RETURN_AMT(),
    SR_RETURN_TAX(),
    SR_RETURN_AMT_INC_TAX(),
    SR_FEE(),
    SR_RETURN_SHIP_COST(),
    SR_REFUNDED_CASH(),
    SR_REVERSED_CHARGE(),
    SR_STORE_CREDIT(),
    SR_NET_LOSS();

    @Override
    public Table getTable()
    {
        return STORE_RETURNS;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
