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

import static com.teradata.tpcds.Table.PROMOTION;

public enum PromotionGeneratorColumn
        implements GeneratorColumn
{
    P_PROMO_SK(228, 1),
    P_PROMO_ID(229, 1),
    P_START_DATE_ID(230, 1),
    P_END_DATE_ID(231, 1),
    P_ITEM_SK(232, 1),
    P_COST(233, 1),
    P_RESPONSE_TARGET(234, 1),
    P_PROMO_NAME(235, 1),
    P_CHANNEL_DMAIL(236, 1),
    P_CHANNEL_EMAIL(237, 1),
    P_CHANNEL_CATALOG(238, 1),
    P_CHANNEL_TV(239, 1),
    P_CHANNEL_RADIO(240, 1),
    P_CHANNEL_PRESS(241, 1),
    P_CHANNEL_EVENT(242, 1),
    P_CHANNEL_DEMO(243, 1),
    P_CHANNEL_DETAILS(244, 100),
    P_PURPOSE(245, 1),
    P_DISCOUNT_ACTIVE(246, 1),
    P_NULLS(247, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    PromotionGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return PROMOTION;
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
