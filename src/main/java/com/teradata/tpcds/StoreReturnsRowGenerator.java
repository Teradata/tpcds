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

import com.google.common.collect.ImmutableList;
import com.teradata.tpcds.row.StoreReturnsRow;
import com.teradata.tpcds.row.StoreSalesRow;
import com.teradata.tpcds.row.TableRow;
import com.teradata.tpcds.type.Pricing;

import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;
import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.Table.HOUSEHOLD_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.REASON;
import static com.teradata.tpcds.Table.STORE;
import static com.teradata.tpcds.Table.STORE_SALES;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_ADDR_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_CDEMO_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_CUSTOMER_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_HDEMO_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_NULLS;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_PRICING;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_REASON_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_RETURNED_DATE_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_RETURNED_TIME_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_STORE_SK;
import static com.teradata.tpcds.column.StoreReturnsColumn.SR_TICKET_NUMBER;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Pricing.generatePricingForReturnsTable;
import static java.util.Collections.emptyList;

public class StoreReturnsRowGenerator
        implements RowGenerator
{
    private static final int SR_SAME_CUSTOMER = 80;

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        // The store_returns table is a child of the store_sales table because you can only return things that have
        // already been purchased.  This method should only get called if we are generating the store_returns table
        // in isolation. Otherwise store_returns is generated during the generation of the store_sales table
        RowGeneratorResult salesAndReturnsResult = STORE_SALES.getRowGenerator().generateRowAndChildRows(rowNumber, session);
        if (salesAndReturnsResult.getRowAndChildRows().size() == 2) {
            return new RowGeneratorResult(ImmutableList.of(salesAndReturnsResult.getRowAndChildRows().get(1)), salesAndReturnsResult.shouldEndRow());
        }
        else {
            return new RowGeneratorResult(emptyList(), salesAndReturnsResult.shouldEndRow());  // no return occurred for given sale
        }
    }

    public TableRow generateRow(Session session, StoreSalesRow salesRow)
    {
        long nullBitMap = createNullBitMap(SR_NULLS);

        // some of the information in the return is taken from the original sale
        long srTicketNumber = salesRow.getSsTicketNumber();
        long srItemSk = salesRow.getSsSoldItemSk();

        // some of the fields are conditionally taken from the sale
        Scaling scaling = session.getScaling();
        long srCustomerSk = generateJoinKey(SR_CUSTOMER_SK, CUSTOMER, 1, scaling);
        int randomInt = generateUniformRandomInt(1, 100, SR_TICKET_NUMBER.getRandomNumberStream());
        if (randomInt < SR_SAME_CUSTOMER) {
            srCustomerSk = salesRow.getSsSoldCustomerSk();
        }

        // the rest of the columns are generated for this specific return
        long srReturnedDateSk = generateJoinKey(SR_RETURNED_DATE_SK, DATE_DIM, salesRow.getSsSoldDateSk(), scaling);
        long srReturnedTimeSk = generateUniformRandomInt(8 * 3600 - 1, 17 * 3600 - 1, SR_RETURNED_TIME_SK.getRandomNumberStream());
        long srCdemoSk = generateJoinKey(SR_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long srHdemoSk = generateJoinKey(SR_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long srAddrSk = generateJoinKey(SR_ADDR_SK, CUSTOMER_ADDRESS, 1, scaling);
        long srStoreSk = generateJoinKey(SR_STORE_SK, STORE, 1, scaling);
        long srReasonSk = generateJoinKey(SR_REASON_SK, REASON, 1, scaling);

        Pricing salesPricing = salesRow.getSsPricing();
        int quantity = generateUniformRandomInt(1, salesPricing.getQuantity(), SR_PRICING.getRandomNumberStream());
        Pricing srPricing = generatePricingForReturnsTable(SR_PRICING, quantity, salesPricing);

        return new StoreReturnsRow(nullBitMap,
                srReturnedDateSk,
                srReturnedTimeSk,
                srItemSk,
                srCustomerSk,
                srCdemoSk,
                srHdemoSk,
                srAddrSk,
                srStoreSk,
                srReasonSk,
                srTicketNumber,
                srPricing);
    }

    @Override
    public void reset() {}
}
