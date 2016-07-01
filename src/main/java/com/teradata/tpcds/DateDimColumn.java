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
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.DATE_DIM;

public enum DateDimColumn
        implements Column
{
    D_DATE_SK(159, 0),
    D_DATE_ID(160, 0),
    D_DATE(161, 0),
    D_MONTH_SEQ(162, 0),
    D_WEEK_SEQ(163, 0),
    D_QUARTER_SEQ(164, 0),
    D_YEAR(165, 0),
    D_DOW(166, 0),
    D_MOY(167, 0),
    D_DOM(168, 0),
    D_QOY(169, 0),
    D_FY_YEAR(170, 0),
    D_FY_QUARTER_SEQ(171, 0),
    D_FY_WEEK_SEQ(172, 0),
    D_DAY_NAME(173, 0),
    D_QUARTER_NAME(174, 0),
    D_HOLIDAY(175, 0),
    D_WEEKEND(176, 0),
    D_FOLLOWING_HOLIDAY(177, 0),
    D_FIRST_DOM(178, 0),
    D_LAST_DOM(179, 0),
    D_SAME_DAY_LY(180, 0),
    D_SAME_DAY_LQ(181, 0),
    D_CURRENT_DAY(182, 0),
    D_CURRENT_WEEK(183, 0),
    D_CURRENT_MONTH(184, 0),
    D_CURRENT_QUARTER(185, 0),
    D_CURRENT_YEAR(186, 0),
    D_NULLS(187, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    DateDimColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return DATE_DIM;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }
}
