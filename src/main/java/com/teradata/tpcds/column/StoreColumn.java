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
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.charr;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum StoreColumn
        implements Column
{
    S_STORE_SK(IDENTIFIER),
    S_STORE_ID(charr(16)),
    S_REC_START_DATE(DATE),
    S_REC_END_DATE(DATE),
    S_CLOSED_DATE_SK(IDENTIFIER),
    S_STORE_NAME(varchar(50)),
    S_NUMBER_EMPLOYEES(INTEGER),
    S_FLOOR_SPACE(INTEGER),
    S_HOURS(charr(20)),
    S_MANAGER(varchar(40)),
    S_MARKET_ID(INTEGER),
    S_GEOGRAPHY_CLASS(varchar(100)),
    S_MARKET_DESC(varchar(100)),
    S_MARKET_MANAGER(varchar(40)),
    S_DIVISION_ID(INTEGER),
    S_DIVISION_NAME(varchar(50)),
    S_COMPANY_ID(INTEGER),
    S_COMPANY_NAME(varchar(50)),
    S_STREET_NUMBER(varchar(10)),
    S_STREET_NAME(varchar(60)),
    S_STREET_TYPE(charr(15)),
    S_SUITE_NUMBER(charr(10)),
    S_CITY(varchar(60)),
    S_COUNTY(varchar(30)),
    S_STATE(charr(2)),
    S_ZIP(charr(10)),
    S_COUNTRY(varchar(20)),
    S_GMT_OFFSET(decimal(5, 2)),
    S_TAX_PRECENTAGE(decimal(5, 2));

    private final ColumnType type;

    StoreColumn(ColumnType type)
    {
        this.type = type;
    }

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

    @Override
    public ColumnType getType()
    {
        return type;
    }
}
