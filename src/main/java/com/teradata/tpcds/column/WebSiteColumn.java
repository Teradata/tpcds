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

public enum WebSiteColumn
        implements Column
{
    WEB_SITE_SK(),
    WEB_SITE_ID(),
    WEB_REC_START_DATE(),
    WEB_REC_END_DATE(),
    WEB_NAME(),
    WEB_OPEN_DATE_SK(),
    WEB_CLOSE_DATE_SK(),
    WEB_CLASS(),
    WEB_MANAGER(),
    WEB_MKT_ID(),
    WEB_MKT_CLASS(),
    WEB_MKT_DESC(),
    WEB_MARKET_MANAGER(),
    WEB_COMPANY_ID(),
    WEB_COMPANY_NAME(),
    WEB_STREET_NUMBER(),
    WEB_STREET_NAME(),
    WEB_STREET_TYPE(),
    WEB_SUITE_NUMBER(),
    WEB_CITY(),
    WEB_COUNTY(),
    WEB_STAT(),
    WEB_ZIP(),
    WEB_COUNTRY(),
    WEB_GMT_OFFSET(),
    WEB_TAX_PERCENTAGE();

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
}
