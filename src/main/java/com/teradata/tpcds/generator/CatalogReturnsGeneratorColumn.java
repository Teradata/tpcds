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

package com.teradata.tpcds.generator;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.CATALOG_RETURNS;

public enum CatalogReturnsGeneratorColumn
        implements GeneratorColumn
{
    CR_RETURNED_DATE_SK(46, 28),
    CR_RETURNED_TIME_SK(47, 28),
    CR_ITEM_SK(48, 14),
    CR_REFUNDED_CUSTOMER_SK(49, 14),
    CR_REFUNDED_CDEMO_SK(50, 14),
    CR_REFUNDED_HDEMO_SK(51, 14),
    CR_REFUNDED_ADDR_SK(52, 14),
    CR_RETURNING_CUSTOMER_SK(53, 28),
    CR_RETURNING_CDEMO_SK(54, 14),
    CR_RETURNING_HDEMO_SK(55, 14),
    CR_RETURNING_ADDR_SK(56, 14),
    CR_CALL_CENTER_SK(57, 0),
    CR_CATALOG_PAGE_SK(58, 14),
    CR_SHIP_MODE_SK(59, 14),
    CR_WAREHOUSE_SK(60, 14),
    CR_REASON_SK(61, 14),
    CR_ORDER_NUMBER(62, 0),
    CR_PRICING_QUANTITY(63, 0),
    CR_PRICING_NET_PAID(64, 0),
    CR_PRICING_EXT_TAX(65, 0),
    CR_PRICING_NET_PAID_INC_TAX(66, 0),
    CR_PRICING_FEE(67, 0),
    CR_PRICING_EXT_SHIP_COST(68, 0),
    CR_PRICING_REFUNDED_CASH(69, 0),
    CR_PRICING_REVERSED_CHARGE(70, 0),
    CR_PRICING_STORE_CREDIT(71, 0),
    CR_PRICING_NET_LOSS(72, 0),
    CR_NULLS(73, 28),
    CR_PRICING(74, 70);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;
    private final int seedsPerRow;

    CatalogReturnsGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return CATALOG_RETURNS;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }

    @Override
    public int getSeedsPerRow()
    {
        return seedsPerRow;
    }
}
