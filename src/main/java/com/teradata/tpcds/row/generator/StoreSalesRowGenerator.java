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

import com.teradata.tpcds.Parallel;
import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Session;
import com.teradata.tpcds.row.StoreSalesRow;
import com.teradata.tpcds.row.TableRow;
import com.teradata.tpcds.type.Pricing;

import java.util.ArrayList;
import java.util.List;

import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Parallel.skipDaysUntilFirstRowOfChunk;
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
import static com.teradata.tpcds.Table.STORE;
import static com.teradata.tpcds.Table.STORE_RETURNS;
import static com.teradata.tpcds.Table.STORE_SALES;
import static com.teradata.tpcds.Table.TIME_DIM;
import static com.teradata.tpcds.column.StoreSalesColumn.SR_IS_RETURNED;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_NULLS;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_PERMUTATION;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_PRICING;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_ADDR_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_CDEMO_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_CUSTOMER_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_DATE_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_HDEMO_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_ITEM_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_PROMO_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_STORE_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_SOLD_TIME_SK;
import static com.teradata.tpcds.column.StoreSalesColumn.SS_TICKET_NUMBER;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Pricing.generatePricingForSalesTable;

public class StoreSalesRowGenerator
        implements RowGenerator
{
    private static final int SR_RETURN_PCT = 10;

    private int[] itemPermutation;

    private long nextDateIndex;
    private long julianDate;
    private int remainingLineItems = 0;
    private OrderInfo orderInfo = new OrderInfo();
    private int itemIndex;

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        int itemCount = (int) session.getScaling().getIdCount(ITEM);
        if (itemPermutation == null) {
            itemPermutation = makePermutation(itemCount, SS_PERMUTATION.getRandomNumberStream());
            Parallel.DateNextIndexPair pair = skipDaysUntilFirstRowOfChunk(STORE_SALES, session);
            julianDate = pair.getJulianDate();
            nextDateIndex = pair.getNextDateIndex();
        }

        Scaling scaling = session.getScaling();
        if (remainingLineItems == 0) {
            orderInfo = generateOrderInfo(rowNumber, session);
            remainingLineItems = generateUniformRandomInt(8, 16, SS_TICKET_NUMBER.getRandomNumberStream());
            itemIndex = generateUniformRandomInt(1, (int) scaling.getIdCount(ITEM), SS_SOLD_ITEM_SK.getRandomNumberStream());
        }

        long nullBitMap = createNullBitMap(SS_NULLS);

        //items need to be unique within an order
        // use a sequence within the permutation
        if (++itemIndex > itemCount) {
            itemIndex = 1;
        }

        long ssSoldItemSk = matchSurrogateKey(getPermutationEntry(itemPermutation, itemIndex), orderInfo.getSsSoldDateSk(), ITEM, scaling);
        long ssSoldPromoSk = generateJoinKey(SS_SOLD_PROMO_SK, PROMOTION, 1, scaling);
        Pricing ssPricing = generatePricingForSalesTable(SS_PRICING);

        StoreSalesRow storeSalesRow = new StoreSalesRow(nullBitMap,
                orderInfo.getSsSoldDateSk(),
                orderInfo.getSsSoldTimeSk(),
                ssSoldItemSk,
                orderInfo.getSsSoldCustomerSk(),
                orderInfo.getSsSoldCdemoSk(),
                orderInfo.getSsSoldHdemoSk(),
                orderInfo.getSsSoldAddrSk(),
                orderInfo.getSsSoldStoreSk(),
                ssSoldPromoSk,
                orderInfo.getSsTicketNumber(),
                ssPricing);
        List<TableRow> generatedRows = new ArrayList<>(2);
        generatedRows.add(storeSalesRow);

        // if the sale gets returned, generate a return row
        int randomInt = generateUniformRandomInt(0, 99, SR_IS_RETURNED.getRandomNumberStream());
        if (randomInt < SR_RETURN_PCT && (!session.generateOnlyOneTable() || session.getOnlyTableToGenerate() != STORE_SALES)) {
            generatedRows.add(((StoreReturnsRowGenerator) STORE_RETURNS.getRowGenerator()).generateRow(session, storeSalesRow));
        }

        remainingLineItems--;
        return new RowGeneratorResult(generatedRows, isLastRowInOrder());
    }

    public OrderInfo generateOrderInfo(long rowNumber, Session session)
    {
        // move to a new date if the row number is ahead of the nextDateIndex
        Scaling scaling = session.getScaling();
        while (rowNumber > nextDateIndex) {
            julianDate += 1;
            nextDateIndex += scaling.getRowCountForDate(STORE_SALES, julianDate);
        }

        long ssSoldStoreSk = generateJoinKey(SS_SOLD_STORE_SK, STORE, 1, scaling);
        long ssSoldTimeSk = generateJoinKey(SS_SOLD_TIME_SK, TIME_DIM, 1, scaling);
        long ssSoldDateSk = generateJoinKey(SS_SOLD_DATE_SK, DATE_DIM, 1, scaling);
        long ssSoldCustomerSk = generateJoinKey(SS_SOLD_CUSTOMER_SK, CUSTOMER, 1, scaling);
        long ssSoldCdemoSk = generateJoinKey(SS_SOLD_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long ssSoldHdemoSk = generateJoinKey(SS_SOLD_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long ssSoldAddrSk = generateJoinKey(SS_SOLD_ADDR_SK, CUSTOMER_ADDRESS, 1, scaling);
        long ssTicketNumber = rowNumber;

        return new OrderInfo(ssSoldStoreSk,
                ssSoldTimeSk,
                ssSoldDateSk,
                ssSoldCustomerSk,
                ssSoldCdemoSk,
                ssSoldHdemoSk,
                ssSoldAddrSk,
                ssTicketNumber);
    }

    private boolean isLastRowInOrder()
    {
        return remainingLineItems == 0;
    }

    @Override
    public void reset()
    {
        itemPermutation = null;
        julianDate = 0;
        nextDateIndex = 0;
        orderInfo = new OrderInfo();
        remainingLineItems = 0;
        itemIndex = 0;
    }

    private class OrderInfo
    {
        private final long ssSoldStoreSk;
        private final long ssSoldTimeSk;
        private final long ssSoldDateSk;
        private final long ssSoldCustomerSk;
        private final long ssSoldCdemoSk;
        private final long ssSoldHdemoSk;
        private final long ssSoldAddrSk;
        private final long ssTicketNumber;

        public OrderInfo(long ssSoldStoreSk,
                long ssSoldTimeSk,
                long ssSoldDateSk,
                long ssSoldCustomerSk,
                long ssSoldCdemoSk,
                long ssSoldHdemoSk,
                long ssSoldAddrSk,
                long ssTicketNumber)
        {
            this.ssSoldStoreSk = ssSoldStoreSk;
            this.ssSoldTimeSk = ssSoldTimeSk;
            this.ssSoldDateSk = ssSoldDateSk;
            this.ssSoldCustomerSk = ssSoldCustomerSk;
            this.ssSoldCdemoSk = ssSoldCdemoSk;
            this.ssSoldHdemoSk = ssSoldHdemoSk;
            this.ssSoldAddrSk = ssSoldAddrSk;
            this.ssTicketNumber = ssTicketNumber;
        }

        public OrderInfo()
        {
            this(0, 0, 0, 0, 0, 0, 0, 0);
        }

        public long getSsTicketNumber()
        {
            return ssTicketNumber;
        }

        public long getSsSoldStoreSk()
        {
            return ssSoldStoreSk;
        }

        public long getSsSoldTimeSk()
        {
            return ssSoldTimeSk;
        }

        public long getSsSoldDateSk()
        {
            return ssSoldDateSk;
        }

        public long getSsSoldCustomerSk()
        {
            return ssSoldCustomerSk;
        }

        public long getSsSoldCdemoSk()
        {
            return ssSoldCdemoSk;
        }

        public long getSsSoldHdemoSk()
        {
            return ssSoldHdemoSk;
        }

        public long getSsSoldAddrSk()
        {
            return ssSoldAddrSk;
        }
    }
}
