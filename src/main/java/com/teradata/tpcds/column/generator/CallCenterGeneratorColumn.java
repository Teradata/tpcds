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

package com.teradata.tpcds.column.generator;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.CALL_CENTER;

public enum CallCenterGeneratorColumn
        implements GeneratorColumn
{
    CC_CALL_CENTER_SK(1, 0),
    CC_CALL_CENTER_ID(2, 15),
    CC_REC_START_DATE_ID(3, 10),
    CC_REC_END_DATE_ID(4, 1),
    CC_CLOSED_DATE_ID(5, 4),
    CC_OPEN_DATE_ID(6, 10),
    CC_NAME(7, 0),
    CC_CLASS(8, 2),
    CC_EMPLOYEES(9, 1),
    CC_SQ_FT(10, 1),
    CC_HOURS(11, 1),
    CC_MANAGER(12, 2),
    CC_MARKET_ID(13, 1),
    CC_MARKET_CLASS(14, 50),
    CC_MARKET_DESC(15, 50),
    CC_MARKET_MANAGER(16, 2),
    CC_DIVISION(17, 2),
    CC_DIVISION_NAME(18, 2),
    CC_COMPANY(19, 2),
    CC_COMPANY_NAME(20, 2),
    CC_STREET_NUMBER(21, 0),
    CC_STREET_NAME(22, 0),
    CC_STREET_TYPE(23, 0),
    CC_SUITE_NUMBER(24, 0),
    CC_CITY(25, 0),
    CC_COUNTY(26, 0),
    CC_STATE(27, 0),
    CC_ZIP(28, 0),
    CC_COUNTRY(29, 0),
    CC_GMT_OFFSET(30, 0),
    CC_ADDRESS(31, 15),
    CC_TAX_PERCENTAGE(32, 1),
    CC_SCD(33, 1),
    CC_NULLS(34, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    CallCenterGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return CALL_CENTER;
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
