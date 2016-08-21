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

import static com.teradata.tpcds.Table.WAREHOUSE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.charr;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum WarehouseColumn
        implements Column
{
    W_WAREHOUSE_SK(IDENTIFIER),
    W_WAREHOUSE_ID(charr(16)),
    W_WAREHOUSE_NAME(varchar(20)),
    W_WAREHOUSE_SQ_FT(INTEGER),
    W_STREET_NUMBER(charr(10)),
    W_STREET_NAME(varchar(60)),
    W_STREET_TYPE(charr(15)),
    W_SUITE_NUMBER(charr(10)),
    W_CITY(varchar(60)),
    W_COUNTY(varchar(30)),
    W_STATE(charr(2)),
    W_ZIP(charr(10)),
    W_COUNTRY(varchar(20)),
    W_GMT_OFFSET(decimal(5, 2));

    private final ColumnType type;

    WarehouseColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return WAREHOUSE;
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
