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
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.STORE_SALES;

public enum StoreSalesGeneratorColumn
        implements GeneratorColumn
{
    SS_SOLD_DATE_SK(314, 2),
    SS_SOLD_TIME_SK(315, 2),
    SS_SOLD_ITEM_SK(316, 1),
    SS_SOLD_CUSTOMER_SK(317, 1),
    SS_SOLD_CDEMO_SK(318, 1),
    SS_SOLD_HDEMO_SK(319, 1),
    SS_SOLD_ADDR_SK(320, 1),
    SS_SOLD_STORE_SK(321, 1),
    SS_SOLD_PROMO_SK(322, 16),
    SS_TICKET_NUMBER(323, 1),
    SS_PRICING_QUANTITY(324, 1),
    SS_PRICING_WHOLESALE_COST(325, 0),
    SS_PRICING_LIST_PRICE(326, 0),
    SS_PRICING_SALES_PRICE(327, 0),
    SS_PRICING_COUPON_AMT(328, 0),
    SS_PRICING_EXT_SALES_PRICE(329, 0),
    SS_PRICING_EXT_WHOLESALE_COST(330, 0),
    SS_PRICING_EXT_LIST_PRICE(331, 0),
    SS_PRICING_EXT_TAX(332, 0),
    SS_PRICING_NET_PAID(333, 0),
    SS_PRICING_NET_PAID_INC_TAX(334, 0),
    SS_PRICING_NET_PROFIT(335, 0),
    SR_IS_RETURNED(336, 16),
    SS_PRICING(337, 128),
    SS_NULLS(338, 32),
    SS_PERMUTATION(339, 0);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    StoreSalesGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return STORE_SALES;
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

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
