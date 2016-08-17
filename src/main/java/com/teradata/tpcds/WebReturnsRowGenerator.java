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
import com.teradata.tpcds.type.Pricing;

import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;
import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.Table.HOUSEHOLD_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.REASON;
import static com.teradata.tpcds.Table.TIME_DIM;
import static com.teradata.tpcds.Table.WEB_SALES;
import static com.teradata.tpcds.WebSalesRowGenerator.GIFT_PERCENTAGE;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_NULLS;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_PRICING;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_REASON_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_REFUNDED_ADDR_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_REFUNDED_CDEMO_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_REFUNDED_CUSTOMER_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_REFUNDED_HDEMO_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_RETURNED_DATE_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_RETURNED_TIME_SK;
import static com.teradata.tpcds.column.WebReturnsColumn.WR_RETURNING_CUSTOMER_SK;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Pricing.generatePricingForReturnsTable;
import static java.util.Collections.emptyList;

public class WebReturnsRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        RowGeneratorResult salesAndReturnsResult = WEB_SALES.getRowGenerator().generateRowAndChildRows(rowNumber, session);
        if (salesAndReturnsResult.getRowAndChildRows().size() == 2) {
            return new RowGeneratorResult(ImmutableList.of(salesAndReturnsResult.getRowAndChildRows().get(1)), salesAndReturnsResult.shouldEndRow());
        }
        else {
            return new RowGeneratorResult(emptyList(), salesAndReturnsResult.shouldEndRow());  // no return occurred for given sale
        }
    }

    public WebReturnsRow generateRow(Session session, WebSalesRow salesRow)
    {
        long nullBitMap = createNullBitMap(WR_NULLS);

        // fields taken from the original sale
        long wrItemSk = salesRow.getWsItemSk();
        long wrOrderNumber = salesRow.getWsOrderNumber();
        long wrWebPageSk = salesRow.getWsWebPageSk();

        // remaining fields are specific to this return
        Scaling scaling = session.getScaling();
        long wrReturnedDateSk = generateJoinKey(WR_RETURNED_DATE_SK, DATE_DIM, salesRow.getWsShipDateSk(), scaling);
        long wrReturnedTimeSk = generateJoinKey(WR_RETURNED_TIME_SK, TIME_DIM, 1, scaling);

        // items are usually returned to the people they were shipped to, but sometimes not
        long wrRefundedCustomerSk = generateJoinKey(WR_REFUNDED_CUSTOMER_SK, CUSTOMER, 1, scaling);
        long wrRefundedCdemoSk = generateJoinKey(WR_REFUNDED_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long wrRefundedHdemoSk = generateJoinKey(WR_REFUNDED_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long wrRefundedAddrSk = generateJoinKey(WR_REFUNDED_ADDR_SK, CUSTOMER_ADDRESS, 1, scaling);
        if (generateUniformRandomInt(0, 99, WR_RETURNING_CUSTOMER_SK.getRandomNumberStream()) < GIFT_PERCENTAGE) {
            wrRefundedCustomerSk = salesRow.getWsShipCustomerSk();
            wrRefundedCdemoSk = salesRow.getWsShipCdemoSk();
            wrRefundedHdemoSk = salesRow.getWsShipHdemoSk();
            wrRefundedAddrSk = salesRow.getWsShipAddrSk();
        }

        long wrReturningCustomerSk = wrRefundedCustomerSk;
        long wrReturningCdemoSk = wrRefundedCdemoSk;
        long wrReturningHdemoSk = wrRefundedHdemoSk;
        long wrReturningAddrSk = wrRefundedAddrSk;

        long wrReasonSk = generateJoinKey(WR_REASON_SK, REASON, 1, scaling);
        int quantity = generateUniformRandomInt(1, salesRow.getWsPricing().getQuantity(), WR_PRICING.getRandomNumberStream());
        Pricing wrPricing = generatePricingForReturnsTable(WR_PRICING, quantity, salesRow.getWsPricing());

        return new WebReturnsRow(nullBitMap,
                wrReturnedDateSk,
                wrReturnedTimeSk,
                wrItemSk,
                wrRefundedCustomerSk,
                wrRefundedCdemoSk,
                wrRefundedHdemoSk,
                wrRefundedAddrSk,
                wrReturningCustomerSk,
                wrReturningCdemoSk,
                wrReturningHdemoSk,
                wrReturningAddrSk,
                wrWebPageSk,
                wrReasonSk,
                wrOrderNumber,
                wrPricing);
    }

    @Override
    public void reset() {}
}
