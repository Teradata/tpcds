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

import com.teradata.tpcds.type.Pricing;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.WebReturnsColumn.WR_ITEM_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_ORDER_NUMBER;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_EXT_SHIP_COST;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_EXT_TAX;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_FEE;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_NET_LOSS;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_NET_PAID;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_QUANTITY;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_REFUNDED_CASH;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_REVERSED_CHARGE;
import static com.teradata.tpcds.WebReturnsColumn.WR_PRICING_STORE_CREDIT;
import static com.teradata.tpcds.WebReturnsColumn.WR_REASON_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_REFUNDED_ADDR_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_REFUNDED_CDEMO_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_REFUNDED_CUSTOMER_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_REFUNDED_HDEMO_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNED_DATE_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNED_TIME_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNING_ADDR_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNING_CDEMO_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNING_CUSTOMER_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_RETURNING_HDEMO_SK;
import static com.teradata.tpcds.WebReturnsColumn.WR_WEB_PAGE_SK;

public class WebReturnsRow
        extends TableRowWithNulls
{
    private final long wrReturnedDateSk;
    private final long wrReturnedTimeSk;
    private final long wrItemSk;
    private final long wrRefundedCustomerSk;
    private final long wrRefundedCdemoSk;
    private final long wrRefundedHdemoSk;
    private final long wrRefundedAddrSk;
    private final long wrReturningCustomerSk;
    private final long wrReturningCdemoSk;
    private final long wrReturningHdemoSk;
    private final long wrReturningAddrSk;
    private final long wrWebPageSk;
    private final long wrReasonSk;
    private final long wrOrderNumber;
    private final Pricing wrPricing;

    public WebReturnsRow(long nullBitMap,
            long wrReturnedDateSk,
            long wrReturnedTimeSk,
            long wrItemSk,
            long wrRefundedCustomerSk,
            long wrRefundedCdemoSk,
            long wrRefundedHdemoSk,
            long wrRefundedAddrSk,
            long wrReturningCustomerSk,
            long wrReturningCgdemoSk,
            long wrReturningHdemoSk,
            long wrReturningAddrSk,
            long wrWebPageSk,
            long wrReasonSk,
            long wrOrderNumber,
            Pricing wrPricing)
    {
        super(nullBitMap, WR_RETURNED_DATE_SK);
        this.wrReturnedDateSk = wrReturnedDateSk;
        this.wrReturnedTimeSk = wrReturnedTimeSk;
        this.wrItemSk = wrItemSk;
        this.wrRefundedCustomerSk = wrRefundedCustomerSk;
        this.wrRefundedCdemoSk = wrRefundedCdemoSk;
        this.wrRefundedHdemoSk = wrRefundedHdemoSk;
        this.wrRefundedAddrSk = wrRefundedAddrSk;
        this.wrReturningCustomerSk = wrReturningCustomerSk;
        this.wrReturningCdemoSk = wrReturningCgdemoSk;
        this.wrReturningHdemoSk = wrReturningHdemoSk;
        this.wrReturningAddrSk = wrReturningAddrSk;
        this.wrWebPageSk = wrWebPageSk;
        this.wrReasonSk = wrReasonSk;
        this.wrOrderNumber = wrOrderNumber;
        this.wrPricing = wrPricing;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(wrReturnedDateSk, WR_RETURNED_DATE_SK),
                getStringOrNullForKey(wrReturnedTimeSk, WR_RETURNED_TIME_SK),
                getStringOrNullForKey(wrItemSk, WR_ITEM_SK),
                getStringOrNullForKey(wrRefundedCustomerSk, WR_REFUNDED_CUSTOMER_SK),
                getStringOrNullForKey(wrRefundedCdemoSk, WR_REFUNDED_CDEMO_SK),
                getStringOrNullForKey(wrRefundedHdemoSk, WR_REFUNDED_HDEMO_SK),
                getStringOrNullForKey(wrRefundedAddrSk, WR_REFUNDED_ADDR_SK),
                getStringOrNullForKey(wrReturningCustomerSk, WR_RETURNING_CUSTOMER_SK),
                getStringOrNullForKey(wrReturningCdemoSk, WR_RETURNING_CDEMO_SK),
                getStringOrNullForKey(wrReturningHdemoSk, WR_RETURNING_HDEMO_SK),
                getStringOrNullForKey(wrReturningAddrSk, WR_RETURNING_ADDR_SK),
                getStringOrNullForKey(wrWebPageSk, WR_WEB_PAGE_SK),
                getStringOrNullForKey(wrReasonSk, WR_REASON_SK),
                getStringOrNullForKey(wrOrderNumber, WR_ORDER_NUMBER),
                getStringOrNull(wrPricing.getQuantity(), WR_PRICING_QUANTITY),
                getStringOrNull(wrPricing.getNetPaid(), WR_PRICING_NET_PAID),
                getStringOrNull(wrPricing.getExtTax(), WR_PRICING_EXT_TAX),
                getStringOrNull(wrPricing.getNetPaidIncludingTax(), WR_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(wrPricing.getFee(), WR_PRICING_FEE),
                getStringOrNull(wrPricing.getExtShipCost(), WR_PRICING_EXT_SHIP_COST),
                getStringOrNull(wrPricing.getRefundedCash(), WR_PRICING_REFUNDED_CASH),
                getStringOrNull(wrPricing.getReversedCharge(), WR_PRICING_REVERSED_CHARGE),
                getStringOrNull(wrPricing.getStoreCredit(), WR_PRICING_STORE_CREDIT),
                getStringOrNull(wrPricing.getNetLoss(), WR_PRICING_NET_LOSS));
    }
}
