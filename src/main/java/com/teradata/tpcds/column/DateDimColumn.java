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

import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.column.ColumnTypes.DATE;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.character;

public enum DateDimColumn
        implements Column
{
    D_DATE_SK(IDENTIFIER),
    D_DATE_ID(character(16)),
    D_DATE(DATE),
    D_MONTH_SEQ(INTEGER),
    D_WEEK_SEQ(INTEGER),
    D_QUARTER_SEQ(INTEGER),
    D_YEAR(INTEGER),
    D_DOW(INTEGER),
    D_MOY(INTEGER),
    D_DOM(INTEGER),
    D_QOY(INTEGER),
    D_FY_YEAR(INTEGER),
    D_FY_QUARTER_SEQ(INTEGER),
    D_FY_WEEK_SEQ(INTEGER),
    D_DAY_NAME(character(9)),
    D_QUARTER_NAME(character(6)),
    D_HOLIDAY(character(1)),
    D_WEEKEND(character(1)),
    D_FOLLOWING_HOLIDAY(character(1)),
    D_FIRST_DOM(INTEGER),
    D_LAST_DOM(INTEGER),
    D_SAME_DAY_LY(INTEGER),
    D_SAME_DAY_LQ(INTEGER),
    D_CURRENT_DAY(character(1)),
    D_CURRENT_WEEK(character(1)),
    D_CURRENT_MONTH(character(1)),
    D_CURRENT_QUARTER(character(1)),
    D_CURRENT_YEAR(character(1));

    private final ColumnType type;

    DateDimColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return DATE_DIM;
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
