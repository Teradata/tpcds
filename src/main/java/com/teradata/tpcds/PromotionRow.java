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

import com.teradata.tpcds.type.Decimal;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_CATALOG;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_DEMO;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_DETAILS;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_DMAIL;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_EMAIL;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_EVENT;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_PRESS;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_RADIO;
import static com.teradata.tpcds.PromotionColumn.P_CHANNEL_TV;
import static com.teradata.tpcds.PromotionColumn.P_COST;
import static com.teradata.tpcds.PromotionColumn.P_DISCOUNT_ACTIVE;
import static com.teradata.tpcds.PromotionColumn.P_END_DATE_ID;
import static com.teradata.tpcds.PromotionColumn.P_ITEM_SK;
import static com.teradata.tpcds.PromotionColumn.P_PROMO_ID;
import static com.teradata.tpcds.PromotionColumn.P_PROMO_NAME;
import static com.teradata.tpcds.PromotionColumn.P_PROMO_SK;
import static com.teradata.tpcds.PromotionColumn.P_PURPOSE;
import static com.teradata.tpcds.PromotionColumn.P_RESPONSE_TARGET;
import static com.teradata.tpcds.PromotionColumn.P_START_DATE_ID;

public class PromotionRow
        extends TableRowWithNulls
{
    private final long pPromoSk;
    private final String pPromoId;
    private final long pStartDateId;
    private final long pEndDateId;
    private final long pItemSk;
    private final Decimal pCost;
    private final int pResponseTarget;
    private final String pPromoName;
    private final boolean pChannelDmail;
    private final boolean pChannelEmail;
    private final boolean pChannelCatalog;
    private final boolean pChannelTv;
    private final boolean pChannelRadio;
    private final boolean pChannelPress;
    private final boolean pChannelEvent;
    private final boolean pChannelDemo;
    private final String pChannelDetails;
    private final String pPurpose;
    private final boolean pDiscountActive;

    public PromotionRow(long nullBitMap,
            long pPromoSk,
            String pPromoId,
            long pStartDateId,
            long pEndDateId,
            long pItemSk,
            Decimal pCost,
            int pResponseTarget,
            String pPromoName,
            boolean pChannelDmail,
            boolean pChannelEmail,
            boolean pChannelCatalog,
            boolean pChannelTv,
            boolean pChannelRadio,
            boolean pChannelPress,
            boolean pChannelEvent,
            boolean pChannelDemo,
            String pChannelDetails,
            String pPurpose,
            boolean pDiscountActive)
    {
        super(nullBitMap, P_PROMO_SK);
        this.pPromoSk = pPromoSk;
        this.pPromoId = pPromoId;
        this.pStartDateId = pStartDateId;
        this.pEndDateId = pEndDateId;
        this.pItemSk = pItemSk;
        this.pCost = pCost;
        this.pResponseTarget = pResponseTarget;
        this.pPromoName = pPromoName;
        this.pChannelDmail = pChannelDmail;
        this.pChannelEmail = pChannelEmail;
        this.pChannelCatalog = pChannelCatalog;
        this.pChannelTv = pChannelTv;
        this.pChannelRadio = pChannelRadio;
        this.pChannelPress = pChannelPress;
        this.pChannelEvent = pChannelEvent;
        this.pChannelDemo = pChannelDemo;
        this.pChannelDetails = pChannelDetails;
        this.pPurpose = pPurpose;
        this.pDiscountActive = pDiscountActive;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(pPromoSk, P_PROMO_SK),
                getStringOrNull(pPromoId, P_PROMO_ID),
                getStringOrNullForKey(pStartDateId, P_START_DATE_ID),
                getStringOrNullForKey(pEndDateId, P_END_DATE_ID),
                getStringOrNullForKey(pItemSk, P_ITEM_SK),
                getStringOrNull(pCost, P_COST),
                getStringOrNull(pResponseTarget, P_RESPONSE_TARGET),
                getStringOrNull(pPromoName, P_PROMO_NAME),
                getStringOrNullForBoolean(pChannelDmail, P_CHANNEL_DMAIL),
                getStringOrNullForBoolean(pChannelEmail, P_CHANNEL_EMAIL),
                getStringOrNullForBoolean(pChannelCatalog, P_CHANNEL_CATALOG),
                getStringOrNullForBoolean(pChannelTv, P_CHANNEL_TV),
                getStringOrNullForBoolean(pChannelRadio, P_CHANNEL_RADIO),
                getStringOrNullForBoolean(pChannelPress, P_CHANNEL_PRESS),
                getStringOrNullForBoolean(pChannelEvent, P_CHANNEL_EVENT),
                getStringOrNullForBoolean(pChannelDemo, P_CHANNEL_DEMO),
                getStringOrNull(pChannelDetails, P_CHANNEL_DETAILS),
                getStringOrNull(pPurpose, P_PURPOSE),
                getStringOrNullForBoolean(pDiscountActive, P_DISCOUNT_ACTIVE));
    }
}
