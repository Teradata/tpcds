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

public enum DateDimColumn
        implements Column
{
    D_DATE_SK(),
    D_DATE_ID(),
    D_DATE(),
    D_MONTH_SEQ(),
    D_WEEK_SEQ(),
    D_QUARTER_SEQ(),
    D_YEAR(),
    D_DOW(),
    D_MOY(),
    D_DOM(),
    D_QOY(),
    D_FY_YEAR(),
    D_FY_QUARTER_SEQ(),
    D_FY_WEEK_SEQ(),
    D_DAY_NAME(),
    D_QUARTER_NAME(),
    D_HOLIDAY(),
    D_WEEKEND(),
    D_FOLLOWING_HOLIDAY(),
    D_FIRST_DOM(),
    D_LAST_DOM(),
    D_SAME_DAY_LY(),
    D_SAME_DAY_LQ(),
    D_CURRENT_DAY(),
    D_CURRENT_WEEK(),
    D_CURRENT_MONTH(),
    D_CURRENT_QUARTER(),
    D_CURRENT_YEAR();

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
}
