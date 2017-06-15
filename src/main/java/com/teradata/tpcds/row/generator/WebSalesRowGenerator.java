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

package com.teradata.tpcds.row.generator;

import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Session;
import com.teradata.tpcds.row.TableRow;
import com.teradata.tpcds.row.WebSalesRow;
import com.teradata.tpcds.type.Pricing;

import javax.annotation.concurrent.NotThreadSafe;

import java.util.ArrayList;
import java.util.List;

import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Permutations.getPermutationEntry;
import static com.teradata.tpcds.Permutations.makePermutation;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.matchSurrogateKey;
import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;
import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.Table.HOUSEHOLD_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.Table.PROMOTION;
import static com.teradata.tpcds.Table.SHIP_MODE;
import static com.teradata.tpcds.Table.TIME_DIM;
import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.Table.WEB_PAGE;
import static com.teradata.tpcds.Table.WEB_SALES;
import static com.teradata.tpcds.Table.WEB_SITE;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WR_IS_RETURNED;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_ADDR_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_CDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_CUSTOMER_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_BILL_HDEMO_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_ITEM_SK;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_NULLS;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_ORDER_NUMBER;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PERMUTATION;
import static com.teradata.tpcds.generator.WebSalesGeneratorColumn.WS_PRICING;
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
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Pricing.generatePricingForSalesTable;

@NotThreadSafe
public class WebSalesRowGenerator
        extends AbstractRowGenerator
{
    public static final int GIFT_PERCENTAGE = 7;
    public static final int RETURN_PERCENTAGE = 10;

    // Note: the following two variables are present in the C generator but unused in
    // a meaningful way. We include them for completeness not to confuse
    // future readers.
    // private long nextDateIndex;
    // private long julianDate;
    private int[] itemPermutation = null;
    private int remainingLineItems = 0;
    private OrderInfo orderInfo;
    private int itemIndex;

    public WebSalesRowGenerator()
    {
        super(WEB_SALES);
    }

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session, RowGenerator parentRowGenerator, RowGenerator childRowGenerator)
    {
        Scaling scaling = session.getScaling();
        int itemCount = (int) scaling.getIdCount(ITEM);
        if (itemPermutation == null) {
            itemPermutation = makePermutation(itemCount, getRandomNumberStream(WS_PERMUTATION));
        }

        if (remainingLineItems == 0) {
            orderInfo = generateOrderInfo(rowNumber, session);
            itemIndex = generateUniformRandomInt(1, itemCount, getRandomNumberStream(WS_ITEM_SK));
            remainingLineItems = generateUniformRandomInt(8, 16, getRandomNumberStream(WS_ORDER_NUMBER));
        }

        long nullBitMap = createNullBitMap(WEB_SALES, getRandomNumberStream(WS_NULLS));

        int shipLag = generateUniformRandomInt(1, 120, getRandomNumberStream(WS_SHIP_DATE_SK));
        long wsShipDateSk = orderInfo.wsSoldDateSk + shipLag;

        if (++itemIndex > itemCount) {
            itemIndex = 1;
        }

        long wsItemSk = matchSurrogateKey(getPermutationEntry(itemPermutation, itemIndex), orderInfo.wsSoldDateSk, ITEM, scaling);

        // the web page needs to be valid for the sale date
        long wsWebPageSk = generateJoinKey(WS_WEB_PAGE_SK, getRandomNumberStream(WS_WEB_PAGE_SK), WEB_PAGE, orderInfo.wsSoldDateSk, scaling);
        long wsWebSiteSk = generateJoinKey(WS_WEB_SITE_SK, getRandomNumberStream(WS_WEB_SITE_SK), WEB_SITE, orderInfo.wsSoldDateSk, scaling);

        long wsShipModeSk = generateJoinKey(WS_SHIP_MODE_SK, getRandomNumberStream(WS_SHIP_MODE_SK), SHIP_MODE, 1, scaling);
        long wsWarehouseSk = generateJoinKey(WS_WAREHOUSE_SK, getRandomNumberStream(WS_WAREHOUSE_SK), WAREHOUSE, 1, scaling);
        long wsPromoSk = generateJoinKey(WS_PROMO_SK, getRandomNumberStream(WS_PROMO_SK), PROMOTION, 1, scaling);
        Pricing wsPricing = generatePricingForSalesTable(WS_PRICING, getRandomNumberStream(WS_PRICING));

        WebSalesRow salesRow = new WebSalesRow(nullBitMap,
                orderInfo.wsSoldDateSk,
                orderInfo.wsSoldTimeSk,
                wsShipDateSk,
                wsItemSk,
                orderInfo.wsBillCustomerSk,
                orderInfo.wsBillCdemoSk,
                orderInfo.wsBillHdemoSk,
                orderInfo.wsBillAddrSk,
                orderInfo.wsShipCustomerSk,
                orderInfo.wsShipCdemoSk,
                orderInfo.wsShipHdemoSk,
                orderInfo.wsShipAddrSk,
                wsWebPageSk,
                wsWebSiteSk,
                wsShipModeSk,
                wsWarehouseSk,
                wsPromoSk,
                orderInfo.wsOrderNumber,
                wsPricing);

        List<TableRow> generatedRows = new ArrayList<>(2);
        generatedRows.add(salesRow);

        // if the item gets returned, generate a returns row
        int randomInt = generateUniformRandomInt(0, 99, getRandomNumberStream(WR_IS_RETURNED));
        if (randomInt < RETURN_PERCENTAGE && (!session.generateOnlyOneTable() || !(session.getOnlyTableToGenerate() == WEB_SALES))) {
            TableRow returnsRow = ((WebReturnsRowGenerator) childRowGenerator).generateRow(session, salesRow);
            generatedRows.add(returnsRow);
        }

        remainingLineItems--;
        return new RowGeneratorResult(generatedRows, remainingLineItems == 0);
    }

    private OrderInfo generateOrderInfo(long rowNumber, Session session)
    {
        Scaling scaling = session.getScaling();

        long wsSoldDateSk = generateJoinKey(WS_SOLD_DATE_SK, getRandomNumberStream(WS_SOLD_DATE_SK), DATE_DIM, 1, scaling);
        long wsSoldTimeSk = generateJoinKey(WS_SOLD_TIME_SK, getRandomNumberStream(WS_SOLD_TIME_SK), TIME_DIM, 1, scaling);
        long wsBillCustomerSk = generateJoinKey(WS_BILL_CUSTOMER_SK, getRandomNumberStream(WS_BILL_CUSTOMER_SK), CUSTOMER, 1, scaling);
        long wsBillCdemoSk = generateJoinKey(WS_BILL_CDEMO_SK, getRandomNumberStream(WS_BILL_CDEMO_SK), CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long wsBillHdemoSk = generateJoinKey(WS_BILL_HDEMO_SK, getRandomNumberStream(WS_BILL_HDEMO_SK), HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long wsBillAddrSk = generateJoinKey(WS_BILL_ADDR_SK, getRandomNumberStream(WS_BILL_ADDR_SK), CUSTOMER_ADDRESS, 1, scaling);

        // Usually the billing info and shipping info are the same.  If it's a gift, they'll be different.
        long wsShipCustomerSk = wsBillCustomerSk;
        long wsShipCdemoSk = wsBillCdemoSk;
        long wsShipHdemoSK = wsBillHdemoSk;
        long wsShipAddrSk = wsBillAddrSk;
        int randomInt = generateUniformRandomInt(0, 99, getRandomNumberStream(WS_SHIP_CUSTOMER_SK));
        if (randomInt > GIFT_PERCENTAGE) {
            wsShipCustomerSk = generateJoinKey(WS_SHIP_CUSTOMER_SK, getRandomNumberStream(WS_SHIP_CUSTOMER_SK), CUSTOMER, 2, scaling);
            wsShipCdemoSk = generateJoinKey(WS_SHIP_CDEMO_SK, getRandomNumberStream(WS_SHIP_CDEMO_SK), CUSTOMER_DEMOGRAPHICS, 2, scaling);
            wsShipHdemoSK = generateJoinKey(WS_SHIP_HDEMO_SK, getRandomNumberStream(WS_SHIP_HDEMO_SK), HOUSEHOLD_DEMOGRAPHICS, 2, scaling);
            wsShipAddrSk = generateJoinKey(WS_SHIP_ADDR_SK, getRandomNumberStream(WS_SHIP_ADDR_SK), CUSTOMER_ADDRESS, 2, scaling);
        }

        long wsOrderNumber = rowNumber;

        return new OrderInfo(wsSoldDateSk,
                wsSoldTimeSk,
                wsBillCustomerSk,
                wsBillCdemoSk,
                wsBillHdemoSk,
                wsBillAddrSk,
                wsShipCustomerSk,
                wsShipCdemoSk,
                wsShipHdemoSK,
                wsShipAddrSk,
                wsOrderNumber);
    }

    private static class OrderInfo
    {
        private final long wsSoldDateSk;
        private final long wsSoldTimeSk;
        private final long wsBillCustomerSk;
        private final long wsBillCdemoSk;
        private final long wsBillHdemoSk;
        private final long wsBillAddrSk;
        private final long wsShipCustomerSk;
        private final long wsShipCdemoSk;
        private final long wsShipHdemoSk;
        private final long wsShipAddrSk;
        private final long wsOrderNumber;

        public OrderInfo(long wsSoldDateSk,
                long wsSoldTimeSk,
                long wsBillCustomerSk,
                long wsBillCdemoSk,
                long wsBillHdemoSk,
                long wsBillAddrSk,
                long wsShipCustomerSk,
                long wsShipCdemoSk,
                long wsShipHdemoSk,
                long wsShipAddrSk,
                long wsOrderNumber)
        {
            this.wsSoldDateSk = wsSoldDateSk;
            this.wsSoldTimeSk = wsSoldTimeSk;
            this.wsBillCustomerSk = wsBillCustomerSk;
            this.wsBillCdemoSk = wsBillCdemoSk;
            this.wsBillHdemoSk = wsBillHdemoSk;
            this.wsBillAddrSk = wsBillAddrSk;
            this.wsShipCustomerSk = wsShipCustomerSk;
            this.wsShipCdemoSk = wsShipCdemoSk;
            this.wsShipHdemoSk = wsShipHdemoSk;
            this.wsShipAddrSk = wsShipAddrSk;
            this.wsOrderNumber = wsOrderNumber;
        }
    }
}
