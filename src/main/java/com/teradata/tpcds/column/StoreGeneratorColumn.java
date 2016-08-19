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

package com.teradata.tpcds.column;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.STORE;

public enum StoreGeneratorColumn
        implements GeneratorColumn
{
    W_STORE_SK(259, 1),
    W_STORE_ID(260, 1),
    W_STORE_REC_START_DATE_ID(261, 1),
    W_STORE_REC_END_DATE_ID(262, 2),
    W_STORE_CLOSED_DATE_ID(263, 2),
    W_STORE_NAME(264, 0),
    W_STORE_EMPLOYEES(265, 1),
    W_STORE_FLOOR_SPACE(266, 1),
    W_STORE_HOURS(267, 1),
    W_STORE_MANAGER(268, 2),
    W_STORE_MARKET_ID(269, 1),
    W_STORE_TAX_PERCENTAGE(270, 1),
    W_STORE_GEOGRAPHY_CLASS(271, 1),
    W_STORE_MARKET_DESC(272, 100),
    W_STORE_MARKET_MANAGER(273, 2),
    W_STORE_DIVISION_ID(274, 1),
    W_STORE_DIVISION_NAME(275, 1),
    W_STORE_COMPANY_ID(276, 1),
    W_STORE_COMPANY_NAME(277, 1),
    W_STORE_ADDRESS_STREET_NUM(278, 1),
    W_STORE_ADDRESS_STREET_NAME1(279, 1),
    W_STORE_ADDRESS_STREET_TYPE(280, 1),
    W_STORE_ADDRESS_SUITE_NUM(281, 1),
    W_STORE_ADDRESS_CITY(282, 1),
    W_STORE_ADDRESS_COUNTY(283, 1),
    W_STORE_ADDRESS_STATE(284, 1),
    W_STORE_ADDRESS_ZIP(285, 1),
    W_STORE_ADDRESS_COUNTRY(286, 1),
    W_STORE_ADDRESS_GMT_OFFSET(287, 1),
    W_STORE_NULLS(288, 2),
    W_STORE_TYPE(289, 1),
    W_STORE_SCD(290, 1),
    W_STORE_ADDRESS(291, 7);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    StoreGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return STORE;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
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
}
