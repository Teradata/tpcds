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
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_ADDR_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_CDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_CUSTOMER_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_HDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_ITEM_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_ORDER_NUMBER;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_COUPON_AMT;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_DISCOUNT_AMT;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_LIST_PRICE;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_SALES_PRICE;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_SHIP_COST;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_TAX;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_EXT_WHOLESALE_COST;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_LIST_PRICE;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_NET_PAID;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_NET_PAID_INC_SHIP;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_NET_PAID_INC_SHIP_TAX;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_NET_PAID_INC_TAX;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_NET_PROFIT;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_QUANTITY;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_SALES_PRICE;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING_WHOLESALE_COST;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PROMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_ADDR_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_CDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_CUSTOMER_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_DATE_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_HDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SHIP_MODE_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SOLD_DATE_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_SOLD_TIME_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_WAREHOUSE_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_WEB_PAGE_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_WEB_SITE_SK;

public class WebSalesRow
        extends TableRowWithNulls
{
    private final long wsSoldDateSk;
    private final long wsSoldTimeSk;
    private final long wsShipDateSk;
    private final long wsItemSk;
    private final long wsBillCustomerSk;
    private final long wsBillCdemoSk;
    private final long wsBillHdemoSk;
    private final long wsBillAddrSk;
    private final long wsShipCustomerSk;
    private final long wsShipCdemoSk;
    private final long wsShipHdemoSk;
    private final long wsShipAddrSk;
    private final long wsWebPageSk;
    private final long wsWebSiteSk;
    private final long wsShipModeSk;
    private final long wsWarehouseSk;
    private final long wsPromoSk;
    private final long wsOrderNumber;
    private final Pricing wsPricing;
    public WebSalesRow(long nullBitMap,
            long wsSoldDateSk,
            long wsSoldTimeSk,
            long wsShipDateSk,
            long wsItemSk,
            long wsBillCustomerSk,
            long wsBillCdemoSk,
            long wsBillHdemoSk,
            long wsBillAddrSk,
            long wsShipCustomerSk,
            long wsShipCdemoSk,
            long wsShipHdemoSk,
            long wsShipAddrSk,
            long wsWebPageSk,
            long wsWebSiteSk,
            long wsShipModeSk,
            long wsWarehouseSk,
            long wsPromoSk,
            long wsOrderNumber,
            Pricing wsPricing)
    {
        super(nullBitMap, WS_SOLD_DATE_SK);
        this.wsSoldDateSk = wsSoldDateSk;
        this.wsSoldTimeSk = wsSoldTimeSk;
        this.wsShipDateSk = wsShipDateSk;
        this.wsItemSk = wsItemSk;
        this.wsBillCustomerSk = wsBillCustomerSk;
        this.wsBillCdemoSk = wsBillCdemoSk;
        this.wsBillHdemoSk = wsBillHdemoSk;
        this.wsBillAddrSk = wsBillAddrSk;
        this.wsShipCustomerSk = wsShipCustomerSk;
        this.wsShipCdemoSk = wsShipCdemoSk;
        this.wsShipHdemoSk = wsShipHdemoSk;
        this.wsShipAddrSk = wsShipAddrSk;
        this.wsWebPageSk = wsWebPageSk;
        this.wsWebSiteSk = wsWebSiteSk;
        this.wsShipModeSk = wsShipModeSk;
        this.wsWarehouseSk = wsWarehouseSk;
        this.wsPromoSk = wsPromoSk;
        this.wsOrderNumber = wsOrderNumber;
        this.wsPricing = wsPricing;
    }

    public long getWsShipCdemoSk()
    {
        return wsShipCdemoSk;
    }

    public long getWsShipHdemoSk()
    {
        return wsShipHdemoSk;
    }

    public long getWsShipAddrSk()
    {
        return wsShipAddrSk;
    }

    public Pricing getWsPricing()
    {
        return wsPricing;
    }

    public long getWsItemSk()
    {
        return wsItemSk;
    }

    public long getWsOrderNumber()
    {
        return wsOrderNumber;
    }

    public long getWsWebPageSk()
    {
        return wsWebPageSk;
    }

    public long getWsShipDateSk()
    {
        return wsShipDateSk;
    }

    public long getWsShipCustomerSk()
    {
        return wsShipCustomerSk;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(wsSoldDateSk, WS_SOLD_DATE_SK),
                getStringOrNullForKey(wsSoldTimeSk, WS_SOLD_TIME_SK),
                getStringOrNullForKey(wsShipDateSk, WS_SHIP_DATE_SK),
                getStringOrNullForKey(wsItemSk, WS_ITEM_SK),
                getStringOrNullForKey(wsBillCustomerSk, WS_BILL_CUSTOMER_SK),
                getStringOrNullForKey(wsBillCdemoSk, WS_BILL_CDEMO_SK),
                getStringOrNullForKey(wsBillHdemoSk, WS_BILL_HDEMO_SK),
                getStringOrNullForKey(wsBillAddrSk, WS_BILL_ADDR_SK),
                getStringOrNullForKey(wsShipCustomerSk, WS_SHIP_CUSTOMER_SK),
                getStringOrNullForKey(wsShipCdemoSk, WS_SHIP_CDEMO_SK),
                getStringOrNullForKey(wsShipHdemoSk, WS_SHIP_HDEMO_SK),
                getStringOrNullForKey(wsShipAddrSk, WS_SHIP_ADDR_SK),
                getStringOrNullForKey(wsWebPageSk, WS_WEB_PAGE_SK),
                getStringOrNullForKey(wsWebSiteSk, WS_WEB_SITE_SK),
                getStringOrNullForKey(wsShipModeSk, WS_SHIP_MODE_SK),
                getStringOrNullForKey(wsWarehouseSk, WS_WAREHOUSE_SK),
                getStringOrNullForKey(wsPromoSk, WS_PROMO_SK),
                getStringOrNullForKey(wsOrderNumber, WS_ORDER_NUMBER),
                getStringOrNull(wsPricing.getQuantity(), WS_PRICING_QUANTITY),
                getStringOrNull(wsPricing.getWholesaleCost(), WS_PRICING_WHOLESALE_COST),
                getStringOrNull(wsPricing.getListPrice(), WS_PRICING_LIST_PRICE),
                getStringOrNull(wsPricing.getSalesPrice(), WS_PRICING_SALES_PRICE),
                getStringOrNull(wsPricing.getExtDiscountAmount(), WS_PRICING_EXT_DISCOUNT_AMT),
                getStringOrNull(wsPricing.getExtSalesPrice(), WS_PRICING_EXT_SALES_PRICE),
                getStringOrNull(wsPricing.getExtWholesaleCost(), WS_PRICING_EXT_WHOLESALE_COST),
                getStringOrNull(wsPricing.getExtListPrice(), WS_PRICING_EXT_LIST_PRICE),
                getStringOrNull(wsPricing.getExtTax(), WS_PRICING_EXT_TAX),
                getStringOrNull(wsPricing.getCouponAmount(), WS_PRICING_COUPON_AMT),
                getStringOrNull(wsPricing.getExtShipCost(), WS_PRICING_EXT_SHIP_COST),
                getStringOrNull(wsPricing.getNetPaid(), WS_PRICING_NET_PAID),
                getStringOrNull(wsPricing.getNetPaidIncludingTax(), WS_PRICING_NET_PAID_INC_TAX),
                getStringOrNull(wsPricing.getNetPaidIncludingShipping(), WS_PRICING_NET_PAID_INC_SHIP),
                getStringOrNull(wsPricing.getNetPaidIncludingShippingAndTax(), WS_PRICING_NET_PAID_INC_SHIP_TAX),
                getStringOrNull(wsPricing.getNetProfit(), WS_PRICING_NET_PROFIT));
    }
}
