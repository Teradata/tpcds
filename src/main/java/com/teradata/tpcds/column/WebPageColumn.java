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

import static com.teradata.tpcds.Table.WEB_PAGE;
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum WebPageColumn
        implements Column
{
    WP_WEB_PAGE_SK(IDENTIFIER),
    WP_WEB_PAGE_ID(character(16)),
    WP_REC_START_DATE(DATE),
    WP_REC_END_DATE(DATE),
    WP_CREATION_DATE_SK(IDENTIFIER),
    WP_ACCESS_DATE_SK(IDENTIFIER),
    WP_AUTOGEN_FLAG(character(1)),
    WP_CUSTOMER_SK(IDENTIFIER),
    WP_URL(varchar(100)),
    WP_TYPE(character(50)),
    WP_CHAR_COUNT(INTEGER),
    WP_LINK_COUNT(INTEGER),
    WP_IMAGE_COUNT(INTEGER),
    WP_MAX_AD_COUNT(INTEGER);

    private final ColumnType type;

    WebPageColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return WEB_PAGE;
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
