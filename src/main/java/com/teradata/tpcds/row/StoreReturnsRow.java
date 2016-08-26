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

package com.teradata.tpcds.row;

import com.teradata.tpcds.type.Pricing;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_ADDR_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_CDEMO_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_CUSTOMER_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_HDEMO_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_ITEM_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_EXT_SHIP_COST;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_EXT_TAX;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_FEE;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_NET_LOSS;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_NET_PAID;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_QUANTITY;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_REFUNDED_CASH;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_REVERSED_CHARGE;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_PRICING_STORE_CREDIT;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_REASON_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_RETURNED_DATE_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_RETURNED_TIME_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_STORE_SK;
import static com.teradata.tpcds.generator.StoreReturnsGeneratorColumn.SR_TICKET_NUMBER;

public class StoreReturnsRow
        extends TableRowWithNulls
{
    private final long srReturnedDateSk;
    private final long srReturnedTimeSk;
    private final long srItemSk;
    private final long srCustomerSk;
    private final long srCdemoSk;
    private final long srHdemoSk;
    private final long srAddrSk;
    private final long srStoreSk;
    private final long srReasonSk;
    private final long srTicketNumber;
    private final Pricing srPricing;

    public StoreReturnsRow(long nullBitMap,
            long srReturnedDateSk,
            long srReturnedTimeSk,
            long srItemSk,
            long srCustomerSk,
            long srCdemoSk,
            long srHdemoSk,
            long srAddrSk,
            long srStoreSk,
            long srReasonSk,
            long srTicketNumber,
            Pricing srPricing)
    {
        super(nullBitMap, SR_RETURNED_DATE_SK);
        this.srReturnedDateSk = srReturnedDateSk;
        this.srReturnedTimeSk = srReturnedTimeSk;
        this.srItemSk = srItemSk;
        this.srCustomerSk = srCustomerSk;
        this.srCdemoSk = srCdemoSk;
        this.srHdemoSk = srHdemoSk;
        this.srAddrSk = srAddrSk;
        this.srStoreSk = srStoreSk;
        this.srReasonSk = srReasonSk;
        this.srTicketNumber = srTicketNumber;
        this.srPricing = srPricing;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(srReturnedDateSk, SR_RETURNED_DATE_SK),
                getStringOrNullForKey(srReturnedTimeSk, SR_RETURNED_TIME_SK),
                getStringOrNullForKey(srItemSk, SR_ITEM_SK),
                getStringOrNullForKey(srCustomerSk, SR_CUSTOMER_SK),
                getStringOrNullForKey(srCdemoSk, SR_CDEMO_SK),
                getStringOrNullForKey(srHdemoSk, SR_HDEMO_SK),
                getStringOrNullForKey(srAddrSk, SR_ADDR_SK),
                getStringOrNullForKey(srStoreSk, SR_STORE_SK),
                getStringOrNullForKey(srReasonSk, SR_REASON_SK),
                getStringOrNullForKey(srTicketNumber, SR_TICKET_NUMBER),
                getStringOrNull(srPricing.getQuantity(), SR_PRICING_QUANTITY),
                getStringOrNull(srPricing.getNetPaid(), SR_PRICING_NET_PAID),
                getStringOrNull(srPricing.getExtTax(), SR_PRICING_EXT_TAX),
                getStringOrNull(srPricing.getNetPaidIncludingTax(), SR_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(srPricing.getFee(), SR_PRICING_FEE),
                getStringOrNull(srPricing.getExtShipCost(), SR_PRICING_EXT_SHIP_COST),
                getStringOrNull(srPricing.getRefundedCash(), SR_PRICING_REFUNDED_CASH),
                getStringOrNull(srPricing.getReversedCharge(), SR_PRICING_REVERSED_CHARGE),
                getStringOrNull(srPricing.getStoreCredit(), SR_PRICING_STORE_CREDIT),
                getStringOrNull(srPricing.getNetLoss(), SR_PRICING_NET_LOSS));
    }
}
