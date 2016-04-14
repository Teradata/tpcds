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

import com.teradata.tpcds.random.RandomNumberStream;

import static com.teradata.tpcds.Table.CALL_CENTER;

public enum CallCenterColumn implements Column
{
    CC_CALL_CENTER_SK(0),
    CC_CALL_CENTER_ID(15),
    CC_REC_START_DATE_ID(10),
    CC_REC_END_DATE_ID(1),
    CC_CLOSED_DATE_ID(4),
    CC_OPEN_DATE_ID(10),
    CC_NAME(0),
    CC_CLASS(2),
    CC_EMPLOYEES(1),
    CC_SQ_FT(1),
    CC_HOURS(1),
    CC_MANAGER(2),
    CC_MARKET_ID(1),
    CC_MARKET_CLASS(50),
    CC_MARKET_DESC(50),
    CC_MARKET_MANAGER(2),
    CC_DIVISION(2),
    CC_DIVISION_NAME(2),
    CC_COMPANY(2),
    CC_COMPANY_NAME(2),
    CC_STREET_NUMBER(0),
    CC_STREET_NAME(0),
    CC_STREET_TYPE(0),
    CC_SUITE_NUMBER(0),
    CC_CITY(0),
    CC_COUNTY(0),
    CC_STATE(0),
    CC_ZIP(0),
    CC_COUNTRY(0),
    CC_GMT_OFFSET(0),
    CC_ADDRESS(15),
    CC_TAX_PERCENTAGE(1),
    CC_SCD(1),
    CC_NULLS(2);

    private final RandomNumberStream randomNumberStream;

    CallCenterColumn(int seedsPerRow)
    {
        this.randomNumberStream = new RandomNumberStream(seedsPerRow);
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
}
