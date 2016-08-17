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
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_ADDR_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_CALL_CENTER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_CATALOG_PAGE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_ORDER_NUMBER;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_COUPON_AMT;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_DISCOUNT_AMOUNT;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_LIST_PRICE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_SALES_PRICE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_SHIP_COST;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_TAX;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_EXT_WHOLESALE_COST;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_LIST_PRICE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_NET_PAID;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_NET_PAID_INC_SHIP;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_NET_PAID_INC_SHIP_TAX;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_NET_PROFIT;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_QUANTITY;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_SALES_PRICE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING_WHOLESALE_COST;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PROMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_ADDR_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_DATE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_MODE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SOLD_DATE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SOLD_ITEM_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SOLD_TIME_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_WAREHOUSE_SK;

public class CatalogSalesRow
        extends TableRowWithNulls
{
    private final long csSoldDateSk;
    private final long csSoldTimeSk;
    private final long csShipDateSk;
    private final long csBillCustomerSk;
    private final long csBillCdemoSk;
    private final long csBillHdemoSk;
    private final long csBillAddrSk;
    private final long csShipCustomerSk;
    private final long csShipCdemoSk;
    private final long csShipHdemoSk;
    private final long csShipAddrSk;
    private final long csCallCenterSk;
    private final long csCatalogPageSk;
    private final long csShipModeSk;
    private final long csWarehouseSk;
    private final long csSoldItemSk;
    private final long csPromoSk;
    private final long csOrderNumber;
    private final Pricing csPricing;

    public CatalogSalesRow(long csSoldDateSk,
            long csSoldTimeSk,
            long csShipDateSk,
            long csBillCustomerSk,
            long csBillCdemoSk,
            long csBillHdemoSk,
            long csBillAddrSk,
            long csShipCustomerSk,
            long csShipCdemoSk,
            long csShipHdemoSk,
            long csShipAddrSk,
            long csCallCenterSk,
            long csCatalogPageSk,
            long csShipModeSk,
            long csWarehouseSk,
            long csSoldItemSk,
            long csPromoSk,
            long csOrderNumber,
            Pricing csPricing,
            long nullBitMap)
    {
        super(nullBitMap, CS_SOLD_DATE_SK);
        this.csSoldDateSk = csSoldDateSk;
        this.csSoldTimeSk = csSoldTimeSk;
        this.csShipDateSk = csShipDateSk;
        this.csBillCustomerSk = csBillCustomerSk;
        this.csBillCdemoSk = csBillCdemoSk;
        this.csBillHdemoSk = csBillHdemoSk;
        this.csBillAddrSk = csBillAddrSk;
        this.csShipCustomerSk = csShipCustomerSk;
        this.csShipCdemoSk = csShipCdemoSk;
        this.csShipHdemoSk = csShipHdemoSk;
        this.csShipAddrSk = csShipAddrSk;
        this.csCallCenterSk = csCallCenterSk;
        this.csCatalogPageSk = csCatalogPageSk;
        this.csShipModeSk = csShipModeSk;
        this.csWarehouseSk = csWarehouseSk;
        this.csSoldItemSk = csSoldItemSk;
        this.csPromoSk = csPromoSk;
        this.csOrderNumber = csOrderNumber;
        this.csPricing = csPricing;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(csSoldDateSk, CS_SOLD_DATE_SK),
                getStringOrNullForKey(csSoldTimeSk, CS_SOLD_TIME_SK),
                getStringOrNullForKey(csShipDateSk, CS_SHIP_DATE_SK),
                getStringOrNullForKey(csBillCustomerSk, CS_BILL_CUSTOMER_SK),
                getStringOrNullForKey(csBillCdemoSk, CS_BILL_CDEMO_SK),
                getStringOrNullForKey(csBillHdemoSk, CS_BILL_HDEMO_SK),
                getStringOrNullForKey(csBillAddrSk, CS_BILL_ADDR_SK),
                getStringOrNullForKey(csShipCustomerSk, CS_SHIP_CUSTOMER_SK),
                getStringOrNullForKey(csShipCdemoSk, CS_SHIP_CDEMO_SK),
                getStringOrNullForKey(csShipHdemoSk, CS_SHIP_HDEMO_SK),
                getStringOrNullForKey(csShipAddrSk, CS_SHIP_ADDR_SK),
                getStringOrNullForKey(csCallCenterSk, CS_CALL_CENTER_SK),
                getStringOrNullForKey(csCatalogPageSk, CS_CATALOG_PAGE_SK),
                getStringOrNullForKey(csShipModeSk, CS_SHIP_MODE_SK),
                getStringOrNull(csWarehouseSk, CS_WAREHOUSE_SK),
                getStringOrNullForKey(csSoldItemSk, CS_SOLD_ITEM_SK),
                getStringOrNullForKey(csPromoSk, CS_PROMO_SK),
                getStringOrNull(csOrderNumber, CS_ORDER_NUMBER),
                getStringOrNull(csPricing.getQuantity(), CS_PRICING_QUANTITY),
                getStringOrNull(csPricing.getWholesaleCost(), CS_PRICING_WHOLESALE_COST),
                getStringOrNull(csPricing.getListPrice(), CS_PRICING_LIST_PRICE),
                getStringOrNull(csPricing.getSalesPrice(), CS_PRICING_SALES_PRICE),
                getStringOrNull(csPricing.getExtDiscountAmount(), CS_PRICING_EXT_DISCOUNT_AMOUNT),
                getStringOrNull(csPricing.getExtSalesPrice(), CS_PRICING_EXT_SALES_PRICE),
                getStringOrNull(csPricing.getExtWholesaleCost(), CS_PRICING_EXT_WHOLESALE_COST),
                getStringOrNull(csPricing.getExtListPrice(), CS_PRICING_EXT_LIST_PRICE),
                getStringOrNull(csPricing.getExtTax(), CS_PRICING_EXT_TAX),
                getStringOrNull(csPricing.getCouponAmount(), CS_PRICING_COUPON_AMT),
                getStringOrNull(csPricing.getExtShipCost(), CS_PRICING_EXT_SHIP_COST),
                getStringOrNull(csPricing.getNetPaid(), CS_PRICING_NET_PAID),
                getStringOrNull(csPricing.getNetPaidIncludingTax(), CS_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(csPricing.getNetPaidIncludingShipping(), CS_PRICING_NET_PAID_INC_SHIP),
                getStringOrNull(csPricing.getNetPaidIncludingShippingAndTax(), CS_PRICING_NET_PAID_INC_SHIP_TAX),
                getStringOrNull(csPricing.getNetProfit(), CS_PRICING_NET_PROFIT));
    }

    public Pricing getCsPricing()
    {
        return csPricing;
    }

    public long getCsSoldItemSk()
    {
        return csSoldItemSk;
    }

    public long getCsCatalogPageSk()
    {
        return csCatalogPageSk;
    }

    public long getCsOrderNumber()
    {
        return csOrderNumber;
    }

    public long getCsBillCustomerSk()
    {
        return csBillCustomerSk;
    }

    public long getCsBillCdemoSk()
    {
        return csBillCdemoSk;
    }

    public long getCsBillHdemoSk()
    {
        return csBillHdemoSk;
    }

    public long getCsBillAddrSk()
    {
        return csBillAddrSk;
    }

    public long getCsCallCenterSk()
    {
        return csCallCenterSk;
    }

    public long getCsShipCustomerSk()
    {
        return csShipCustomerSk;
    }

    public long getCsShipCdemoSk()
    {
        return csShipCdemoSk;
    }

    public long getCsShipAddrSk()
    {
        return csShipAddrSk;
    }

    public long getCsShipDateSk()
    {
        return csShipDateSk;
    }
}
