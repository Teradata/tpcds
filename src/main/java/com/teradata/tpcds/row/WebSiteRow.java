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

package com.teradata.tpcds.row;

import com.teradata.tpcds.type.Address;
import com.teradata.tpcds.type.Decimal;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_CITY;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_COUNTRY;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_COUNTY;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_GMT_OFFSET;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_STATE;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_STREET_NAME1;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_STREET_NUM;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_STREET_TYPE;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_SUITE_NUM;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_ADDRESS_ZIP;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_CLASS;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_CLOSE_DATE;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_COMPANY_ID;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_COMPANY_NAME;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_MANAGER;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_MARKET_CLASS;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_MARKET_DESC;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_MARKET_ID;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_MARKET_MANAGER;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_NAME;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_OPEN_DATE;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_REC_END_DATE_ID;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_REC_START_DATE_ID;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_SITE_ID;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_SITE_SK;
import static com.teradata.tpcds.column.WebSiteColumn.WEB_TAX_PERCENTAGE;
import static java.lang.String.format;

public class WebSiteRow
        extends TableRowWithNulls
{
    private final long webSiteSk;
    private final String webSiteId;
    private final long webRecStartDateId;
    private final long webRecEndDateId;
    private final String webName;
    private final long webOpenDate;
    private final long webCloseDate;
    private final String webClass;
    private final String webManager;
    private final int webMarketId;
    private final String webMarketClass;
    private final String webMarketDesc;
    private final String webMarketManager;
    private final int webCompanyId;
    private final String webCompanyName;
    private final Address webAddress;
    private final Decimal webTaxPercentage;

    public WebSiteRow(long nullBitMap,
            long webSiteSk,
            String webSiteId,
            long webRecStartDateId,
            long webRecEndDateId,
            String webName,
            long webOpenDate,
            long webCloseDate,
            String webClass,
            String webManager,
            int webMarketId,
            String webMarketClass,
            String webMarketDesc,
            String webMarketManager,
            int webCompanyId,
            String webCompanyName,
            Address webAddress,
            Decimal webTaxPercentage)
    {
        super(nullBitMap, WEB_SITE_SK);
        this.webSiteSk = webSiteSk;
        this.webSiteId = webSiteId;
        this.webRecStartDateId = webRecStartDateId;
        this.webRecEndDateId = webRecEndDateId;
        this.webName = webName;
        this.webOpenDate = webOpenDate;
        this.webCloseDate = webCloseDate;
        this.webClass = webClass;
        this.webManager = webManager;
        this.webMarketId = webMarketId;
        this.webMarketClass = webMarketClass;
        this.webMarketDesc = webMarketDesc;
        this.webMarketManager = webMarketManager;
        this.webCompanyId = webCompanyId;
        this.webCompanyName = webCompanyName;
        this.webAddress = webAddress;
        this.webTaxPercentage = webTaxPercentage;
    }

    public String getWebName()
    {
        return webName;
    }

    public long getWebOpenDate()
    {
        return webOpenDate;
    }

    public long getWebCloseDate()
    {
        return webCloseDate;
    }

    public String getWebClass()
    {
        return webClass;
    }

    public String getWebManager()
    {
        return webManager;
    }

    public int getWebMarketId()
    {
        return webMarketId;
    }

    public String getWebMarketClass()
    {
        return webMarketClass;
    }

    public String getWebMarketDesc()
    {
        return webMarketDesc;
    }

    public String getWebMarketManager()
    {
        return webMarketManager;
    }

    public int getWebCompanyId()
    {
        return webCompanyId;
    }

    public String getWebCompanyName()
    {
        return webCompanyName;
    }

    public Address getWebAddress()
    {
        return webAddress;
    }

    public Decimal getWebTaxPercentage()
    {
        return webTaxPercentage;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(webSiteSk, WEB_SITE_SK),
                getStringOrNull(webSiteId, WEB_SITE_ID),
                getDateStringOrNullFromJulianDays(webRecStartDateId, WEB_REC_START_DATE_ID),
                getDateStringOrNullFromJulianDays(webRecEndDateId, WEB_REC_END_DATE_ID),
                getStringOrNull(webName, WEB_NAME),
                getStringOrNullForKey(webOpenDate, WEB_OPEN_DATE),
                getStringOrNullForKey(webCloseDate, WEB_CLOSE_DATE),
                getStringOrNull(webClass, WEB_CLASS),
                getStringOrNull(webManager, WEB_MANAGER),
                getStringOrNull(webMarketId, WEB_MARKET_ID),
                getStringOrNull(webMarketClass, WEB_MARKET_CLASS),
                getStringOrNull(webMarketDesc, WEB_MARKET_DESC),
                getStringOrNull(webMarketManager, WEB_MARKET_MANAGER),
                getStringOrNull(webCompanyId, WEB_COMPANY_ID),
                getStringOrNull(webCompanyName, WEB_COMPANY_NAME),
                getStringOrNull(webAddress.getStreetNumber(), WEB_ADDRESS_STREET_NUM),
                getStringOrNull(webAddress.getStreetName(), WEB_ADDRESS_STREET_NAME1),
                getStringOrNull(webAddress.getStreetType(), WEB_ADDRESS_STREET_TYPE),
                getStringOrNull(webAddress.getSuiteNumber(), WEB_ADDRESS_SUITE_NUM),
                getStringOrNull(webAddress.getCity(), WEB_ADDRESS_CITY),
                getStringOrNull(webAddress.getCounty(), WEB_ADDRESS_COUNTY),
                getStringOrNull(webAddress.getState(), WEB_ADDRESS_STATE),
                getStringOrNull(format("%05d", webAddress.getZip()), WEB_ADDRESS_ZIP),
                getStringOrNull(webAddress.getCountry(), WEB_ADDRESS_COUNTRY),
                getStringOrNull(webAddress.getGmtOffset(), WEB_ADDRESS_GMT_OFFSET),
                getStringOrNull(webTaxPercentage, WEB_TAX_PERCENTAGE));
    }
}
