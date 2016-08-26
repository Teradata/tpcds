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

import static com.teradata.tpcds.Table.WEB_SITE;
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum WebSiteColumn
        implements Column
{
    WEB_SITE_SK(IDENTIFIER),
    WEB_SITE_ID(character(16)),
    WEB_REC_START_DATE(DATE),
    WEB_REC_END_DATE(DATE),
    WEB_NAME(varchar(50)),
    WEB_OPEN_DATE_SK(IDENTIFIER),
    WEB_CLOSE_DATE_SK(IDENTIFIER),
    WEB_CLASS(varchar(50)),
    WEB_MANAGER(varchar(40)),
    WEB_MKT_ID(INTEGER),
    WEB_MKT_CLASS(varchar(50)),
    WEB_MKT_DESC(varchar(100)),
    WEB_MARKET_MANAGER(varchar(40)),
    WEB_COMPANY_ID(INTEGER),
    WEB_COMPANY_NAME(character(50)),
    WEB_STREET_NUMBER(character(10)),
    WEB_STREET_NAME(varchar(60)),
    WEB_STREET_TYPE(character(15)),
    WEB_SUITE_NUMBER(character(10)),
    WEB_CITY(varchar(60)),
    WEB_COUNTY(varchar(30)),
    WEB_STATE(character(2)),
    WEB_ZIP(character(10)),
    WEB_COUNTRY(varchar(20)),
    WEB_GMT_OFFSET(decimal(5, 2)),
    WEB_TAX_PERCENTAGE(decimal(5, 2));

    private final ColumnType type;

    WebSiteColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return WEB_SITE;
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
