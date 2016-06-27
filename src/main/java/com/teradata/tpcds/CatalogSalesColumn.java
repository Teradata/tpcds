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

package com.teradata.tpcds;

import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

public enum CatalogSalesColumn
        implements Column
{
    CS_SOLD_DATE_SK(75, 1),
    CS_SOLD_TIME_SK(76, 2),
    CS_SHIP_DATE_SK(77, 14),
    CS_BILL_CUSTOMER_SK(78, 1),
    CS_BILL_CDEMO_SK(79, 1),
    CS_BILL_HDEMO_SK(80, 1),
    CS_BILL_ADDR_SK(81, 1),
    CS_SHIP_CUSTOMER_SK(82, 2),
    CS_SHIP_CDEMO_SK(83, 1),
    CS_SHIP_HDEMO_SK(84, 1),
    CS_SHIP_ADDR_SK(85, 1),
    CS_CALL_CENTER_SK(86, 1),
    CS_CATALOG_PAGE_SK(87, 42),
    CS_SHIP_MODE_SK(88, 14),
    CS_WAREHOUSE_SK(89, 14),
    CS_SOLD_ITEM_SK(90, 1),
    CS_PROMO_SK(91, 14),
    CS_ORDER_NUMBER(92, 1),
    CS_PRICING_QUANTITY(93, 0),
    CS_PRICING_WHOLESALE_COST(94, 0),
    CS_PRICING_LIST_PRICE(95, 0),
    CS_PRICING_SALES_PRICE(96, 0),
    CS_PRICING_COUPON_AMT(97, 0),
    CS_PRICING_EXT_SALES_PRICE(98, 0),
    CS_PRICING_EXT_DISCOUNT_AMOUNT(99, 0),
    CS_PRICING_EXT_WHOLESALE_COST(100, 0),
    CS_PRICING_EXT_LIST_PRICE(101, 0),
    CS_PRICING_EXT_TAX(102, 0),
    CS_PRICING_EXT_SHIP_COST(103, 0),
    CS_PRICING_NET_PAID(104, 0),
    CS_PRICING_NET_PAID_INC_TAX(105, 0),
    CS_PRICING_NET_PAID_INC_SHIP(106, 0),
    CS_PRICING_NET_PAID_INC_SHIP_TAX(107, 0),
    CS_PRICING_NET_PROFIT(108, 0),
    CS_PRICING(109, 112),
    CS_PERMUTE(110, 0),
    CS_NULLS(111, 28),
    CR_IS_RETURNED(112, 14),
    CS_PERMUTATION(113, 0);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    CatalogSalesColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return Table.CATALOG_SALES;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }
}
