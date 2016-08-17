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

import static com.teradata.tpcds.Table.WEB_SITE;

public enum WebSiteColumn
        implements Column
{
    WEB_SITE_SK(447, 1),
    WEB_SITE_ID(448, 1),
    WEB_REC_START_DATE_ID(449, 1),
    WEB_REC_END_DATE_ID(450, 1),
    WEB_NAME(451, 1),
    WEB_OPEN_DATE(452, 1),
    WEB_CLOSE_DATE(453, 1),
    WEB_CLASS(454, 1),
    WEB_MANAGER(455, 2),
    WEB_MARKET_ID(456, 1),
    WEB_MARKET_CLASS(457, 20),
    WEB_MARKET_DESC(458, 100),
    WEB_MARKET_MANAGER(459, 2),
    WEB_COMPANY_ID(460, 1),
    WEB_COMPANY_NAME(461, 1),
    WEB_ADDRESS_STREET_NUM(462, 1),
    WEB_ADDRESS_STREET_NAME1(463, 1),
    WEB_ADDRESS_STREET_TYPE(464, 1),
    WEB_ADDRESS_SUITE_NUM(465, 1),
    WEB_ADDRESS_CITY(466, 1),
    WEB_ADDRESS_COUNTY(467, 1),
    WEB_ADDRESS_STATE(468, 1),
    WEB_ADDRESS_ZIP(469, 1),
    WEB_ADDRESS_COUNTRY(470, 1),
    WEB_ADDRESS_GMT_OFFSET(471, 1),
    WEB_TAX_PERCENTAGE(472, 1),
    WEB_NULLS(473, 2),
    WEB_ADDRESS(474, 7),
    WEB_SCD(475, 70);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    WebSiteColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return WEB_SITE;
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
