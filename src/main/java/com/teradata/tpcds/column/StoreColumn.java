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

import static com.teradata.tpcds.Table.STORE;

public enum StoreColumn
        implements Column
{
    S_STORE_SK(),
    S_STORE_ID(),
    S_REC_START_DATE(),
    S_REC_END_DATE(),
    S_CLOSED_DATE_SK(),
    S_STORE_NAME(),
    S_NUMBER_EMPLOYEES(),
    S_FLOOR_SPACE(),
    S_HOURS(),
    S_MANAGER(),
    S_MARKET_ID(),
    S_GEOGRAPHY_CLASS(),
    S_MARKET_DESC(),
    S_MARKET_MANAGER(),
    S_DIVISION_ID(),
    S_DIVISION_NAME(),
    S_COMPANY_ID(),
    S_COMPANY_NAME(),
    S_STREET_NUMBER(),
    S_STREET_NAME(),
    S_STREET_TYPE(),
    S_SUITE_NUMBER(),
    S_CITY(),
    S_COUNTY(),
    S_STATE(),
    S_ZIP(),
    S_COUNTRY(),
    S_GMT_OFFSET(),
    S_TAX_PRECENTAGE();

    @Override
    public Table getTable()
    {
        return STORE;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
