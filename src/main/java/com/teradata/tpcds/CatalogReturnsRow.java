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
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_CALL_CENTER_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_CATALOG_PAGE_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_ITEM_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_ORDER_NUMBER;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_EXT_SHIP_COST;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_EXT_TAX;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_FEE;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_NET_LOSS;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_NET_PAID;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_QUANTITY;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_REFUNDED_CASH;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_REVERSED_CHARGE;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_PRICING_STORE_CREDIT;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_REASON_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_REFUNDED_ADDR_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_REFUNDED_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_REFUNDED_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_REFUNDED_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNED_DATE_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNED_TIME_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNING_ADDR_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNING_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNING_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_RETURNING_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_SHIP_MODE_SK;
import static com.teradata.tpcds.column.CatalogReturnsColumn.CR_WAREHOUSE_SK;

public class CatalogReturnsRow
        extends TableRowWithNulls
{
    private final long crReturnedDateSk;
    private final long crReturnedTimeSk;
    private final long crItemSk;
    private final long crRefundedCustomerSk;
    private final long crRefundedCdemoSk;
    private final long crRefundedHdemoSk;
    private final long crRefundedAddrSk;
    private final long crReturningCustomerSk;
    private final long crReturningCdemoSk;
    private final long crReturningHdemoSk;
    private final long crReturningAddrSk;
    private final long crCallCenterSk;
    private final long crCatalogPageSk;
    private final long crShipModeSk;
    private final long crWarehouseSk;
    private final long crReasonSk;
    private final long crOrderNumber;
    private final Pricing crPricing;

    public CatalogReturnsRow(long crReturnedDateSk,
            long crReturnedTimeSk,
            long crItemSk,
            long crRefundedCustomerSk,
            long crRefundedCdemoSk,
            long crRefundedHdemoSk,
            long crRefundedAddrSk,
            long crReturningCustomerSk,
            long crReturningCdemoSk,
            long crReturningHdemoSk,
            long crReturningAddrSk,
            long crCallCenterSk,
            long crCatalogPageSk,
            long crShipModeSk,
            long crWarehouseSk,
            long crReasonSk,
            long crOrderNumber,
            Pricing crPricing,
            long nullBitMap)
    {
        super(nullBitMap, CR_RETURNED_DATE_SK);
        this.crReturnedDateSk = crReturnedDateSk;
        this.crReturnedTimeSk = crReturnedTimeSk;
        this.crItemSk = crItemSk;
        this.crRefundedCustomerSk = crRefundedCustomerSk;
        this.crRefundedCdemoSk = crRefundedCdemoSk;
        this.crRefundedHdemoSk = crRefundedHdemoSk;
        this.crRefundedAddrSk = crRefundedAddrSk;
        this.crReturningCustomerSk = crReturningCustomerSk;
        this.crReturningCdemoSk = crReturningCdemoSk;
        this.crReturningHdemoSk = crReturningHdemoSk;
        this.crReturningAddrSk = crReturningAddrSk;
        this.crCallCenterSk = crCallCenterSk;
        this.crCatalogPageSk = crCatalogPageSk;
        this.crShipModeSk = crShipModeSk;
        this.crWarehouseSk = crWarehouseSk;
        this.crReasonSk = crReasonSk;
        this.crOrderNumber = crOrderNumber;
        this.crPricing = crPricing;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(crReturnedDateSk, CR_RETURNED_DATE_SK),
                getStringOrNullForKey(crReturnedTimeSk, CR_RETURNED_TIME_SK),
                getStringOrNullForKey(crItemSk, CR_ITEM_SK),
                getStringOrNullForKey(crRefundedCustomerSk, CR_REFUNDED_CUSTOMER_SK),
                getStringOrNullForKey(crRefundedCdemoSk, CR_REFUNDED_CDEMO_SK),
                getStringOrNullForKey(crRefundedHdemoSk, CR_REFUNDED_HDEMO_SK),
                getStringOrNullForKey(crRefundedAddrSk, CR_REFUNDED_ADDR_SK),
                getStringOrNullForKey(crReturningCustomerSk, CR_RETURNING_CUSTOMER_SK),
                getStringOrNullForKey(crReturningCdemoSk, CR_RETURNING_CDEMO_SK),
                getStringOrNullForKey(crReturningHdemoSk, CR_RETURNING_HDEMO_SK),
                getStringOrNullForKey(crReturningAddrSk, CR_RETURNING_ADDR_SK),
                getStringOrNullForKey(crCallCenterSk, CR_CALL_CENTER_SK),
                getStringOrNullForKey(crCatalogPageSk, CR_CATALOG_PAGE_SK),
                getStringOrNullForKey(crShipModeSk, CR_SHIP_MODE_SK),
                getStringOrNullForKey(crWarehouseSk, CR_WAREHOUSE_SK),
                getStringOrNullForKey(crReasonSk, CR_REASON_SK),
                getStringOrNull(crOrderNumber, CR_ORDER_NUMBER),
                getStringOrNull(crPricing.getQuantity(), CR_PRICING_QUANTITY),
                getStringOrNull(crPricing.getNetPaid(), CR_PRICING_NET_PAID),
                getStringOrNull(crPricing.getExtTax(), CR_PRICING_EXT_TAX),
                getStringOrNull(crPricing.getNetPaidIncludingTax(), CR_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(crPricing.getFee(), CR_PRICING_FEE),
                getStringOrNull(crPricing.getExtShipCost(), CR_PRICING_EXT_SHIP_COST),
                getStringOrNull(crPricing.getRefundedCash(), CR_PRICING_REFUNDED_CASH),
                getStringOrNull(crPricing.getReversedCharge(), CR_PRICING_REVERSED_CHARGE),
                getStringOrNull(crPricing.getStoreCredit(), CR_PRICING_STORE_CREDIT),
                getStringOrNull(crPricing.getNetLoss(), CR_PRICING_NET_LOSS));
    }
}
