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

package com.teradata.tpcds.generator;

import com.teradata.tpcds.Table;

import static com.teradata.tpcds.Table.WEB_SALES;

public enum WebSalesGeneratorColumn
        implements GeneratorColumn
{
    WS_SOLD_DATE_SK(409, 2),
    WS_SOLD_TIME_SK(410, 2),
    WS_SHIP_DATE_SK(411, 16),
    WS_ITEM_SK(412, 1),
    WS_BILL_CUSTOMER_SK(413, 1),
    WS_BILL_CDEMO_SK(414, 1),
    WS_BILL_HDEMO_SK(415, 1),
    WS_BILL_ADDR_SK(416, 1),
    WS_SHIP_CUSTOMER_SK(417, 2),
    WS_SHIP_CDEMO_SK(418, 2),
    WS_SHIP_HDEMO_SK(419, 1),
    WS_SHIP_ADDR_SK(420, 1),
    WS_WEB_PAGE_SK(421, 16),
    WS_WEB_SITE_SK(422, 16),
    WS_SHIP_MODE_SK(423, 16),
    WS_WAREHOUSE_SK(424, 16),
    WS_PROMO_SK(425, 16),
    WS_ORDER_NUMBER(426, 1),
    WS_PRICING_QUANTITY(427, 1),
    WS_PRICING_WHOLESALE_COST(428, 1),
    WS_PRICING_LIST_PRICE(429, 0),
    WS_PRICING_SALES_PRICE(430, 0),
    WS_PRICING_EXT_DISCOUNT_AMT(431, 0),
    WS_PRICING_EXT_SALES_PRICE(432, 0),
    WS_PRICING_EXT_WHOLESALE_COST(433, 0),
    WS_PRICING_EXT_LIST_PRICE(434, 0),
    WS_PRICING_EXT_TAX(435, 0),
    WS_PRICING_COUPON_AMT(436, 0),
    WS_PRICING_EXT_SHIP_COST(437, 0),
    WS_PRICING_NET_PAID(438, 0),
    WS_PRICING_NET_PAID_INC_TAX(439, 0),
    WS_PRICING_NET_PAID_INC_SHIP(440, 0),
    WS_PRICING_NET_PAID_INC_SHIP_TAX(441, 0),
    WS_PRICING_NET_PROFIT(442, 0),
    WS_PRICING(443, 128),
    WS_NULLS(444, 32),
    WR_IS_RETURNED(445, 16),
    WS_PERMUTATION(446, 0);

    private final int globalColumnNumber;
    private final int seedsPerRow;

    WebSalesGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return WEB_SALES;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }

    @Override
    public int getSeedsPerRow()
    {
        return seedsPerRow;
    }
}
