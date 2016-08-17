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
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.WEB_PAGE;

public enum WebPageColumn
        implements Column
{
    WP_PAGE_SK(367, 1),
    WP_PAGE_ID(368, 1),
    WP_REC_START_DATE_ID(369, 1),
    WP_REC_END_DATE_ID(370, 1),
    WP_CREATION_DATE_SK(371, 2),
    WP_ACCESS_DATE_SK(372, 1),
    WP_AUTOGEN_FLAG(373, 1),
    WP_CUSTOMER_SK(374, 1),
    WP_URL(375, 1),
    WP_TYPE(376, 1),
    WP_CHAR_COUNT(377, 1),
    WP_LINK_COUNT(378, 1),
    WP_IMAGE_COUNT(379, 1),
    WP_MAX_AD_COUNT(380, 1),
    WP_NULLS(381, 2),
    WP_SCD(382, 1);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    WebPageColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return WEB_PAGE;
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
