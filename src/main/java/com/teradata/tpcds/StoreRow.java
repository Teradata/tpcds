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

import com.teradata.tpcds.type.Address;
import com.teradata.tpcds.type.Decimal;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_CITY;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_COUNTRY;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_COUNTY;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_GMT_OFFSET;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_STATE;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_STREET_NAME1;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_STREET_NUM;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_STREET_TYPE;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_SUITE_NUM;
import static com.teradata.tpcds.StoreColumn.W_STORE_ADDRESS_ZIP;
import static com.teradata.tpcds.StoreColumn.W_STORE_CLOSED_DATE_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_COMPANY_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_COMPANY_NAME;
import static com.teradata.tpcds.StoreColumn.W_STORE_DIVISION_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_DIVISION_NAME;
import static com.teradata.tpcds.StoreColumn.W_STORE_EMPLOYEES;
import static com.teradata.tpcds.StoreColumn.W_STORE_FLOOR_SPACE;
import static com.teradata.tpcds.StoreColumn.W_STORE_GEOGRAPHY_CLASS;
import static com.teradata.tpcds.StoreColumn.W_STORE_HOURS;
import static com.teradata.tpcds.StoreColumn.W_STORE_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_MANAGER;
import static com.teradata.tpcds.StoreColumn.W_STORE_MARKET_DESC;
import static com.teradata.tpcds.StoreColumn.W_STORE_MARKET_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_MARKET_MANAGER;
import static com.teradata.tpcds.StoreColumn.W_STORE_NAME;
import static com.teradata.tpcds.StoreColumn.W_STORE_REC_END_DATE_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_REC_START_DATE_ID;
import static com.teradata.tpcds.StoreColumn.W_STORE_SK;
import static com.teradata.tpcds.StoreColumn.W_STORE_TAX_PERCENTAGE;
import static java.lang.String.format;

public class StoreRow
        extends TableRowWithNulls
{
    private final long storeSk;
    private final String storeId;
    private final long recStartDateId;
    private final long recEndDateId;
    private final long closedDateId;
    private final String storeName;
    private final int employees;
    private final int floorSpace;
    private final String hours;
    private final String storeManager;
    private final int marketId;
    private final Decimal dTaxPercentage;
    private final String geographyClass;
    private final String marketDesc;
    private final String marketManager;
    private final long divisionId;
    private final String divisionName;
    private final long companyId;
    private final String companyName;
    private final Address address;

    public StoreRow(long nullBitMap,
            long storeSk,
            String storeId,
            long recStartDateId,
            long recEndDateId,
            long closedDateId,
            String storeName,
            int employees,
            int floorSpace,
            String hours,
            String storeManager,
            int marketId,
            Decimal dTaxPercentage,
            String geographyClass,
            String marketDesc,
            String marketManager,
            long divisionId,
            String divisionName,
            long companyId,
            String companyName,
            Address address)
    {
        super(nullBitMap, W_STORE_SK);
        this.storeSk = storeSk;
        this.storeId = storeId;
        this.recStartDateId = recStartDateId;
        this.recEndDateId = recEndDateId;
        this.closedDateId = closedDateId;
        this.storeName = storeName;
        this.employees = employees;
        this.floorSpace = floorSpace;
        this.hours = hours;
        this.storeManager = storeManager;
        this.marketId = marketId;
        this.dTaxPercentage = dTaxPercentage;
        this.geographyClass = geographyClass;
        this.marketDesc = marketDesc;
        this.marketManager = marketManager;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.companyId = companyId;
        this.companyName = companyName;
        this.address = address;
    }

    public long getClosedDateId()
    {
        return closedDateId;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public int getEmployees()
    {
        return employees;
    }

    public int getFloorSpace()
    {
        return floorSpace;
    }

    public String getHours()
    {
        return hours;
    }

    public String getStoreManager()
    {
        return storeManager;
    }

    public int getMarketId()
    {
        return marketId;
    }

    public Decimal getdTaxPercentage()
    {
        return dTaxPercentage;
    }

    public String getMarketDesc()
    {
        return marketDesc;
    }

    public String getMarketManager()
    {
        return marketManager;
    }

    public Address getAddress()
    {
        return address;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(storeSk, W_STORE_SK),
                getStringOrNull(storeId, W_STORE_ID),
                getDateStringOrNullFromJulianDays(recStartDateId, W_STORE_REC_START_DATE_ID),
                getDateStringOrNullFromJulianDays(recEndDateId, W_STORE_REC_END_DATE_ID),
                getStringOrNullForKey(closedDateId, W_STORE_CLOSED_DATE_ID),
                getStringOrNull(storeName, W_STORE_NAME),
                getStringOrNull(employees, W_STORE_EMPLOYEES),
                getStringOrNull(floorSpace, W_STORE_FLOOR_SPACE),
                getStringOrNull(hours, W_STORE_HOURS),
                getStringOrNull(storeManager, W_STORE_MANAGER),
                getStringOrNull(marketId, W_STORE_MARKET_ID),
                getStringOrNull(geographyClass, W_STORE_GEOGRAPHY_CLASS),
                getStringOrNull(marketDesc, W_STORE_MARKET_DESC),
                getStringOrNull(marketManager, W_STORE_MARKET_MANAGER),
                getStringOrNullForKey(divisionId, W_STORE_DIVISION_ID),
                getStringOrNull(divisionName, W_STORE_DIVISION_NAME),
                getStringOrNullForKey(companyId, W_STORE_COMPANY_ID),
                getStringOrNull(companyName, W_STORE_COMPANY_NAME),
                getStringOrNull(address.getStreetNumber(), W_STORE_ADDRESS_STREET_NUM),
                getStringOrNull(address.getStreetName(), W_STORE_ADDRESS_STREET_NAME1),
                getStringOrNull(address.getStreetType(), W_STORE_ADDRESS_STREET_TYPE),
                getStringOrNull(address.getSuiteNumber(), W_STORE_ADDRESS_SUITE_NUM),
                getStringOrNull(address.getCity(), W_STORE_ADDRESS_CITY),
                getStringOrNull(address.getCounty(), W_STORE_ADDRESS_COUNTY),
                getStringOrNull(address.getState(), W_STORE_ADDRESS_STATE),
                getStringOrNull(format("%05d", address.getZip()), W_STORE_ADDRESS_ZIP),
                getStringOrNull(address.getCountry(), W_STORE_ADDRESS_COUNTRY),
                getStringOrNull(address.getGmtOffset(), W_STORE_ADDRESS_GMT_OFFSET),
                getStringOrNull(dTaxPercentage, W_STORE_TAX_PERCENTAGE));
    }
}
