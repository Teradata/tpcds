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
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.WEB_RETURNS;

public enum WebReturnsGeneratorColumn
        implements GeneratorColumn
{
    WR_RETURNED_DATE_SK(383, 32),
    WR_RETURNED_TIME_SK(384, 32),
    WR_ITEM_SK(385, 16),
    WR_REFUNDED_CUSTOMER_SK(386, 16),
    WR_REFUNDED_CDEMO_SK(387, 16),
    WR_REFUNDED_HDEMO_SK(388, 16),
    WR_REFUNDED_ADDR_SK(389, 16),
    WR_RETURNING_CUSTOMER_SK(390, 16),
    WR_RETURNING_CDEMO_SK(391, 16),
    WR_RETURNING_HDEMO_SK(392, 16),
    WR_RETURNING_ADDR_SK(393, 16),
    WR_WEB_PAGE_SK(394, 16),
    WR_REASON_SK(395, 16),
    WR_ORDER_NUMBER(396, 0),
    WR_PRICING_QUANTITY(397, 0),
    WR_PRICING_NET_PAID(398, 0),
    WR_PRICING_EXT_TAX(399, 0),
    WR_PRICING_NET_PAID_INC_TAX(400, 0),
    WR_PRICING_FEE(401, 0),
    WR_PRICING_EXT_SHIP_COST(402, 0),
    WR_PRICING_REFUNDED_CASH(403, 0),
    WR_PRICING_REVERSED_CHARGE(404, 0),
    WR_PRICING_STORE_CREDIT(405, 0),
    WR_PRICING_NET_LOSS(406, 0),
    WR_PRICING(407, 80),
    WR_NULLS(408, 32);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    WebReturnsGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return WEB_RETURNS;
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
