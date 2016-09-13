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

import static com.teradata.tpcds.Table.CALL_CENTER;
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum CallCenterColumn
        implements Column
{
    CC_CALL_CENTER_SK(IDENTIFIER),
    CC_CALL_CENTER_ID(character(16)),
    CC_REC_START_DATE(DATE),
    CC_REC_END_DATE(DATE),
    CC_CLOSED_DATE_SK(INTEGER),
    CC_OPEN_DATE_SK(INTEGER),
    CC_NAME(varchar(50)),
    CC_CLASS(varchar(50)),
    CC_EMPLOYEES(INTEGER),
    CC_SQ_FT(INTEGER),
    CC_HOURS(character(20)),
    CC_MANAGER(varchar(40)),
    CC_MKT_ID(INTEGER),
    CC_MKT_CLASS(character(50)),
    CC_MKT_DESC(varchar(100)),
    CC_MARKET_MANAGER(varchar(40)),
    CC_DIVISION(INTEGER),
    CC_DIVISION_NAME(varchar(50)),
    CC_COMPANY(INTEGER),
    CC_COMPANY_NAME(character(50)),
    CC_STREET_NUMBER(character(10)),
    CC_STREET_NAME(varchar(60)),
    CC_STREET_TYPE(character(15)),
    CC_SUITE_NUMBER(character(10)),
    CC_CITY(varchar(60)),
    CC_COUNTY(varchar(30)),
    CC_STATE(character(2)),
    CC_ZIP(character(10)),
    CC_COUNTRY(varchar(20)),
    CC_GMT_OFFSET(decimal(5, 2)),
    CC_TAX_PERCENTAGE(decimal(5, 2));

    private final ColumnType type;

    CallCenterColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return CALL_CENTER;
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

    @Override
    public int getPosition()
    {
        return ordinal();
    }
}
