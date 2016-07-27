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

import static com.teradata.tpcds.Table.STORE_RETURNS;

public enum StoreReturnsColumn
        implements Column
{
    SR_RETURNED_DATE_SK(292, 32),
    SR_RETURNED_TIME_SK(293, 32),
    SR_ITEM_SK(294, 16),
    SR_CUSTOMER_SK(295, 16),
    SR_CDEMO_SK(296, 16),
    SR_HDEMO_SK(297, 16),
    SR_ADDR_SK(298, 16),
    SR_STORE_SK(299, 16),
    SR_REASON_SK(300, 16),
    SR_TICKET_NUMBER(301, 16),
    SR_PRICING_QUANTITY(302, 0),
    SR_PRICING_NET_PAID(303, 0),
    SR_PRICING_EXT_TAX(304, 0),
    SR_PRICING_NET_PAID_INC_TAX(305, 0),
    SR_PRICING_FEE(306, 0),
    SR_PRICING_EXT_SHIP_COST(307, 0),
    SR_PRICING_REFUNDED_CASH(308, 0),
    SR_PRICING_REVERSED_CHARGE(309, 0),
    SR_PRICING_STORE_CREDIT(310, 0),
    SR_PRICING_NET_LOSS(311, 0),
    SR_PRICING(312, 80),
    SR_NULLS(313, 32);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    StoreReturnsColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return STORE_RETURNS;
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
