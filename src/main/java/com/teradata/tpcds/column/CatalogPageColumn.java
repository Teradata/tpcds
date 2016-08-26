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

import static com.teradata.tpcds.Table.CATALOG_PAGE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum CatalogPageColumn
        implements Column
{
    CP_CATALOG_PAGE_SK(IDENTIFIER),
    CP_CATALOG_PAGE_ID(character(16)),
    CP_START_DATE_SK(INTEGER),
    CP_END_DATE_SK(INTEGER),
    CP_DEPARTMENT(varchar(50)),
    CP_CATALOG_NUMBER(INTEGER),
    CP_CATALOG_PAGE_NUMBER(INTEGER),
    CP_DESCRIPTION(varchar(100)),
    CP_TYPE(varchar(100));

    private final ColumnType type;

    CatalogPageColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return CATALOG_PAGE;
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
