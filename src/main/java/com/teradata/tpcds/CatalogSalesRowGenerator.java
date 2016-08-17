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

import com.teradata.tpcds.Parallel.DateNextIndexPair;
import com.teradata.tpcds.type.Decimal;
import com.teradata.tpcds.type.Pricing;

import javax.annotation.concurrent.NotThreadSafe;

import java.util.ArrayList;
import java.util.List;

import static com.teradata.tpcds.CatalogReturnsRowGenerator.RETURN_PERCENT;
import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Parallel.skipDaysUntilFirstRowOfChunk;
import static com.teradata.tpcds.Permutations.getPermutationEntry;
import static com.teradata.tpcds.Permutations.makePermutation;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.matchSurrogateKey;
import static com.teradata.tpcds.Table.CALL_CENTER;
import static com.teradata.tpcds.Table.CATALOG_PAGE;
import static com.teradata.tpcds.Table.CATALOG_RETURNS;
import static com.teradata.tpcds.Table.CATALOG_SALES;
import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;
import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.HOUSEHOLD_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.ITEM;
import static com.teradata.tpcds.Table.PROMOTION;
import static com.teradata.tpcds.Table.SHIP_MODE;
import static com.teradata.tpcds.Table.TIME_DIM;
import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CR_IS_RETURNED;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_ADDR_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_BILL_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_CALL_CENTER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_CATALOG_PAGE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_NULLS;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_ORDER_NUMBER;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PERMUTE;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PRICING;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_PROMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_ADDR_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_CDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_CUSTOMER_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_DATE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_HDEMO_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SHIP_MODE_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SOLD_ITEM_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_SOLD_TIME_SK;
import static com.teradata.tpcds.column.CatalogSalesColumn.CS_WAREHOUSE_SK;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Pricing.generatePricingForSalesTable;

@NotThreadSafe
public class CatalogSalesRowGenerator
        implements RowGenerator
{
    public static final int CS_QUANTITY_MAX = 100;
    public static final Decimal CS_MARKUP_MAX = new Decimal(200, 2);
    public static final Decimal CS_DISCOUNT_MAX = new Decimal(100, 2);
    public static final Decimal CS_WHOLESALE_MAX = new Decimal(10000, 2);
    public static final int CS_MIN_SHIP_DELAY = 2;  // minimum days from order to ship
    public static final int CS_MAX_SHIP_DELAY = 90;  // maximum days from order to ship
    public static final int GIFT_PERCENTAGE = 10;

    // These do not get set in the constructor because it needs session information,
    // which isn't available at construction time.
    private int[] itemPermutation; // would be final if initialized in constructor
    private long julianDate;
    private long nextDateIndex;

    private int remainingLineItems = 0;
    private OrderInfo orderInfo = new OrderInfo(); // initialize with all zeros because one of the fields is used in generation of new orderInfos.
    private int ticketItemBase;

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        int itemCount = (int) session.getScaling().getIdCount(ITEM);
        if (itemPermutation == null) {
            itemPermutation = makePermutation(itemCount, CS_PERMUTE.getRandomNumberStream());
            DateNextIndexPair pair = skipDaysUntilFirstRowOfChunk(CATALOG_SALES, session);
            julianDate = pair.getJulianDate();
            nextDateIndex = pair.getNextDateIndex();
        }

        if (remainingLineItems == 0) {
            orderInfo = generateOrderInfo(rowNumber, session);
            ticketItemBase = generateUniformRandomInt(1, itemCount, CS_SOLD_ITEM_SK.getRandomNumberStream());
            remainingLineItems = generateUniformRandomInt(4, 14, CS_ORDER_NUMBER.getRandomNumberStream());
        }

        long nullBitMap = Nulls.createNullBitMap(CS_NULLS);

        // orders are shipped some number of days after they are ordered
        int shippingLag = generateUniformRandomInt(CS_MIN_SHIP_DELAY, CS_MAX_SHIP_DELAY, CS_SHIP_DATE_SK.getRandomNumberStream());
        long csShipDateSk = orderInfo.getCsSoldDateSk() == -1 ? -1 : orderInfo.getCsSoldDateSk() + shippingLag;

        // items need to be unique within an order
        // use a sequence within the permutation
        // NB: Permutations are 1-based
        if (++ticketItemBase > itemCount) {
            ticketItemBase = 1;
        }

        Scaling scaling = session.getScaling();
        long item = getPermutationEntry(itemPermutation, ticketItemBase);
        long csSoldItemSk = matchSurrogateKey(item, orderInfo.getCsSoldDateSk(), ITEM, scaling);

        // catalog page needs to be from a catalog active at the time of the sale
        long csCatalogPageSk = (orderInfo.getCsSoldDateSk() == -1) ? -1 : generateJoinKey(CS_CATALOG_PAGE_SK, CATALOG_PAGE, orderInfo.getCsSoldDateSk(), scaling);

        long csShipModeSk = generateJoinKey(CS_SHIP_MODE_SK, SHIP_MODE, 1, scaling);
        long csWarehouseSk = generateJoinKey(CS_WAREHOUSE_SK, WAREHOUSE, 1, scaling);
        long csPromoSk = generateJoinKey(CS_PROMO_SK, PROMOTION, 1, scaling);
        Pricing csPricing = generatePricingForSalesTable(CS_PRICING);

        CatalogSalesRow catalogSalesRow = new CatalogSalesRow(orderInfo.getCsSoldDateSk(),
                orderInfo.getCsSoldTimeSk(),
                csShipDateSk,
                orderInfo.getCsBillCustomerSk(),
                orderInfo.getCsBillCdemoSk(),
                orderInfo.getCsBillHdemoSk(),
                orderInfo.getCsBillAddrSk(),
                orderInfo.getCsShipCustomerSk(),
                orderInfo.getCsShipCdemoSk(),
                orderInfo.getCsShipHdemoSk(),
                orderInfo.getCsShipAddrSk(),
                orderInfo.getCsCallCenterSk(),
                csCatalogPageSk,
                csShipModeSk,
                csWarehouseSk,
                csSoldItemSk,
                csPromoSk,
                orderInfo.getCsOrderNumber(),
                csPricing,
                nullBitMap);

        List<TableRow> generatedRows = new ArrayList<>(2);
        generatedRows.add(catalogSalesRow);

        // if the sale gets returned, generate a return row
        int randomInt = generateUniformRandomInt(0, 99, CR_IS_RETURNED.getRandomNumberStream());
        if (randomInt < RETURN_PERCENT && (!session.generateOnlyOneTable() || session.getOnlyTableToGenerate() != CATALOG_SALES)) {
            TableRow catalogReturnsRow = ((CatalogReturnsRowGenerator) CATALOG_RETURNS.getRowGenerator()).generateRow(session, catalogSalesRow);
            generatedRows.add(catalogReturnsRow);
        }

        remainingLineItems--;
        return new RowGeneratorResult(generatedRows, isLastRowInOrder());
    }

    @Override
    public void reset()
    {
        itemPermutation = null;
        julianDate = 0;
        nextDateIndex = 0;

        remainingLineItems = 0;
        orderInfo = new OrderInfo(); // initialize with all zeros because one of the fields is used in generation of new orderInfos.
        ticketItemBase = 0;
    }

    private boolean isLastRowInOrder()
    {
        return remainingLineItems == 0;
    }

    private OrderInfo generateOrderInfo(long rowNumber, Session session)
    {
        Scaling scaling = session.getScaling();

        // move to a new date if the row number is ahead of the nextDateIndex
        while (rowNumber > nextDateIndex) {
            julianDate += 1;
            nextDateIndex += scaling.getRowCountForDate(CATALOG_SALES, julianDate);
        }

        // Some attributes remain the same for each lineitem in an order; others are different
        // for each lineitem.
        //
        // Parallel generation causes another problem, since the values that get seeded may come from a prior row.
        // If we are seeding at the start of a parallel chunk, hunt backwards in the RNG stream to find the most
        // recent values that were used to set the values of the orderline-invariant columns

        long csSoldDateSk = julianDate;
        long csSoldTimeSk = generateJoinKey(CS_SOLD_TIME_SK, TIME_DIM, orderInfo.getCsCallCenterSk(), scaling);
        long csCallCenterSk = (csSoldDateSk == -1) ? -1 : generateJoinKey(CS_CALL_CENTER_SK, CALL_CENTER, csSoldDateSk, scaling);
        long csBillCustomerSk = generateJoinKey(CS_BILL_CUSTOMER_SK, CUSTOMER, 1, scaling);
        long csBillCdemoSk = generateJoinKey(CS_BILL_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long csBillHdemoSk = generateJoinKey(CS_BILL_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long csBillAddrSk = generateJoinKey(CS_BILL_ADDR_SK, CUSTOMER_ADDRESS, 1, scaling);

        // most orders are for the ordering customers, some are not
        int giftPercentage = generateUniformRandomInt(0, 99, CS_SHIP_CUSTOMER_SK.getRandomNumberStream());
        long csShipCustomerSk = csBillCustomerSk;
        long csShipCdemoSk = csBillCdemoSk;
        long csShipHdemoSk = csBillHdemoSk;
        long csShipAddrSk = csBillAddrSk;
        if (giftPercentage <= GIFT_PERCENTAGE) {
            csShipCustomerSk = generateJoinKey(CS_SHIP_CUSTOMER_SK, CUSTOMER, 2, scaling);
            csShipCdemoSk = generateJoinKey(CS_SHIP_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 2, scaling);
            csShipHdemoSk = generateJoinKey(CS_SHIP_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 2, scaling);
            csShipAddrSk = generateJoinKey(CS_SHIP_ADDR_SK, CUSTOMER_ADDRESS, 2, scaling);
        }

        long csOrderNumber = rowNumber;

        return new OrderInfo(csSoldDateSk,
                csSoldTimeSk,
                csCallCenterSk,
                csBillCustomerSk,
                csBillCdemoSk,
                csBillHdemoSk,
                csBillAddrSk,
                csShipCustomerSk,
                csShipCdemoSk,
                csShipHdemoSk,
                csShipAddrSk,
                csOrderNumber);
    }

    private class OrderInfo
    {
        private final long csSoldDateSk;
        private final long csSoldTimeSk;
        private final long csCallCenterSk;
        private final long csBillCustomerSk;
        private final long csBillCdemoSk;
        private final long csBillHdemoSk;
        private final long csBillAddrSk;
        private final long csShipCustomerSk;
        private final long csShipCdemoSk;
        private final long csShipHdemoSk;
        private final long csShipAddrSk;
        private final long csOrderNumber;

        public OrderInfo(long csSoldDateSk,
                long csSoldTimeSk,
                long csCallCenterSk,
                long csBillCustomerSk,
                long csBillCdemoSk,
                long csBillHdemoSk,
                long csBillAddrSk,
                long csShipCustomerSk,
                long csShipCdemoSk,
                long csShipHdemoSk,
                long csShipAddrSk,
                long csOrderNumber)
        {
            this.csSoldDateSk = csSoldDateSk;
            this.csSoldTimeSk = csSoldTimeSk;
            this.csCallCenterSk = csCallCenterSk;
            this.csBillCustomerSk = csBillCustomerSk;
            this.csBillCdemoSk = csBillCdemoSk;
            this.csBillHdemoSk = csBillHdemoSk;
            this.csBillAddrSk = csBillAddrSk;
            this.csShipCustomerSk = csShipCustomerSk;
            this.csShipCdemoSk = csShipCdemoSk;
            this.csShipHdemoSk = csShipHdemoSk;
            this.csShipAddrSk = csShipAddrSk;
            this.csOrderNumber = csOrderNumber;
        }

        public OrderInfo()
        {
            this(0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L);
        }

        public long getCsSoldDateSk()
        {
            return csSoldDateSk;
        }

        public long getCsSoldTimeSk()
        {
            return csSoldTimeSk;
        }

        public long getCsCallCenterSk()
        {
            return csCallCenterSk;
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

        public long getCsShipCustomerSk()
        {
            return csShipCustomerSk;
        }

        public long getCsShipCdemoSk()
        {
            return csShipCdemoSk;
        }

        public long getCsShipHdemoSk()
        {
            return csShipHdemoSk;
        }

        public long getCsShipAddrSk()
        {
            return csShipAddrSk;
        }

        public long getCsOrderNumber()
        {
            return csOrderNumber;
        }
    }
}
