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

public enum CatalogReturnsColumn
        implements Column
{
    CR_RETURNED_DATE_SK(),
    CR_RETURNED_TIME_SK(),
    CR_ITEM_SK(),
    CR_REFUNDED_CUSTOMER_SK(),
    CR_REFUNDED_CDEMO_SK(),
    CR_REFUNDED_HDEMO_SK(),
    CR_REFUNDED_ADDR_SK(),
    CR_RETURNING_CUSTOMER_SK(),
    CR_RETURNING_CDEMO_SK(),
    CR_RETURNING_HDEMO_SK(),
    CR_RETURNING_ADDR_SK(),
    CR_CALL_CENTER_SK(),
    CR_CATALOG_PAGE_SK(),
    CR_SHIP_MODE_SK(),
    CR_WAREHOUSE_SK(),
    CR_REASON_SK(),
    CR_ORDER_NUMBER(),
    CR_RETURN_QUANTITY(),
    CR_RETURN_AMOUNT(),
    CR_RETURN_TAX(),
    CR_RETURN_AMT_INC_TAX(),
    CR_FEE(),
    CR_RETURN_SHIP_COST(),
    CR_REFUNDED_CASH(),
    CR_REVERSED_CHARGE(),
    CR_STORE_CREDIT(),
    CR_NET_LOSS();

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
}
