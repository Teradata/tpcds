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
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_COUPON_AMT;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_EXT_LIST_PRICE;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_EXT_SALES_PRICE;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_EXT_TAX;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_EXT_WHOLESALE_COST;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_LIST_PRICE;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_NET_PAID;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_NET_PROFIT;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_QUANTITY;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_SALES_PRICE;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_PRICING_WHOLESALE_COST;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_ADDR_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_CDEMO_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_CUSTOMER_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_DATE_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_HDEMO_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_ITEM_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_PROMO_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_STORE_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_SOLD_TIME_SK;
import static com.teradata.tpcds.column.StoreSalesGeneratorColumn.SS_TICKET_NUMBER;

public class StoreSalesRow
        extends TableRowWithNulls
{
    private final long ssSoldDateSk;
    private final long ssSoldTimeSk;
    private final long ssSoldItemSk;
    private final long ssSoldCustomerSk;
    private final long ssSoldCdemoSk;
    private final long ssSoldHdemoSk;
    private final long ssSoldAddrSk;
    private final long ssSoldStoreSk;
    private final long ssSoldPromoSk;
    private final long ssTicketNumber;
    private final Pricing ssPricing;

    public StoreSalesRow(long nullBitMap,
            long ssSoldDateSk,
            long ssSoldTimeSk,
            long ssSoldItemSk,
            long ssSoldCustomerSk,
            long ssSoldCdemoSk,
            long ssSoldHdemoSk,
            long ssSoldAddrSk,
            long ssSoldStoreSk,
            long ssSoldPromoSk,
            long ssTicketNumber,
            Pricing ssPricing)
    {
        super(nullBitMap, SS_SOLD_DATE_SK);
        this.ssSoldDateSk = ssSoldDateSk;
        this.ssSoldTimeSk = ssSoldTimeSk;
        this.ssSoldItemSk = ssSoldItemSk;
        this.ssSoldCustomerSk = ssSoldCustomerSk;
        this.ssSoldCdemoSk = ssSoldCdemoSk;
        this.ssSoldHdemoSk = ssSoldHdemoSk;
        this.ssSoldAddrSk = ssSoldAddrSk;
        this.ssSoldStoreSk = ssSoldStoreSk;
        this.ssSoldPromoSk = ssSoldPromoSk;
        this.ssTicketNumber = ssTicketNumber;
        this.ssPricing = ssPricing;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(ssSoldDateSk, SS_SOLD_DATE_SK),
                getStringOrNullForKey(ssSoldTimeSk, SS_SOLD_TIME_SK),
                getStringOrNullForKey(ssSoldItemSk, SS_SOLD_ITEM_SK),
                getStringOrNullForKey(ssSoldCustomerSk, SS_SOLD_CUSTOMER_SK),
                getStringOrNullForKey(ssSoldCdemoSk, SS_SOLD_CDEMO_SK),
                getStringOrNullForKey(ssSoldHdemoSk, SS_SOLD_HDEMO_SK),
                getStringOrNullForKey(ssSoldAddrSk, SS_SOLD_ADDR_SK),
                getStringOrNullForKey(ssSoldStoreSk, SS_SOLD_STORE_SK),
                getStringOrNullForKey(ssSoldPromoSk, SS_SOLD_PROMO_SK),
                getStringOrNullForKey(ssTicketNumber, SS_TICKET_NUMBER),
                getStringOrNull(ssPricing.getQuantity(), SS_PRICING_QUANTITY),
                getStringOrNull(ssPricing.getWholesaleCost(), SS_PRICING_WHOLESALE_COST),
                getStringOrNull(ssPricing.getListPrice(), SS_PRICING_LIST_PRICE),
                getStringOrNull(ssPricing.getSalesPrice(), SS_PRICING_SALES_PRICE),
                getStringOrNull(ssPricing.getCouponAmount(), SS_PRICING_COUPON_AMT),
                getStringOrNull(ssPricing.getExtSalesPrice(), SS_PRICING_EXT_SALES_PRICE),
                getStringOrNull(ssPricing.getExtWholesaleCost(), SS_PRICING_EXT_WHOLESALE_COST),
                getStringOrNull(ssPricing.getExtListPrice(), SS_PRICING_EXT_LIST_PRICE),
                getStringOrNull(ssPricing.getExtTax(), SS_PRICING_EXT_TAX),
                getStringOrNull(ssPricing.getCouponAmount(), SS_PRICING_COUPON_AMT),
                getStringOrNull(ssPricing.getNetPaid(), SS_PRICING_NET_PAID),
                getStringOrNull(ssPricing.getNetPaidIncludingTax(), SS_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(ssPricing.getNetProfit(), SS_PRICING_NET_PROFIT));
    }

    public long getSsTicketNumber()
    {
        return ssTicketNumber;
    }

    public long getSsSoldItemSk()
    {
        return ssSoldItemSk;
    }

    public long getSsSoldCustomerSk()
    {
        return ssSoldCustomerSk;
    }

    public long getSsSoldDateSk()
    {
        return ssSoldDateSk;
    }

    public Pricing getSsPricing()
    {
        return ssPricing;
    }
}
