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

import static com.teradata.tpcds.Table.PROMOTION;

public enum PromotionColumn
        implements Column
{
    P_PROMO_SK(),
    P_PROMO_ID(),
    P_START_DATE_SK(),
    P_END_DATE_SK(),
    P_ITEM_SK(),
    P_COST(),
    P_RESPONSE_TARGE(),
    P_PROMO_NAME(),
    P_CHANNEL_DMAIL(),
    P_CHANNEL_EMAIL(),
    P_CHANNEL_CATALOG(),
    P_CHANNEL_TV(),
    P_CHANNEL_RADIO(),
    P_CHANNEL_PRESS(),
    P_CHANNEL_EVENT(),
    P_CHANNEL_DEMO(),
    P_CHANNEL_DETAILS(),
    P_PURPOSE(),
    P_DISCOUNT_ACTIVE();

    @Override
    public Table getTable()
    {
        return PROMOTION;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
