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

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_CITY;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_COUNTRY;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_COUNTY;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_GMT_OFFSET;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_STATE;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_STREET_NAME1;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_STREET_NUM;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_STREET_TYPE;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_SUITE_NUM;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_ADDRESS_ZIP;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_WAREHOUSE_ID;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_WAREHOUSE_NAME;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_WAREHOUSE_SK;
import static com.teradata.tpcds.column.generator.WarehouseGeneratorColumn.W_WAREHOUSE_SQ_FT;
import static java.lang.String.format;

public class WarehouseRow
        extends TableRowWithNulls
{
    private final long wWarehouseSk;
    private final String wWarehouseId;
    private final String wWarehouseName;
    private final int wWarehouseSqFt;
    private final Address wAddress;

    public WarehouseRow(long nullBitMap, long wWarehouseSk, String wWarehouseId, String wWarehouseName, int wWarehouseSqFt, Address wAddress)
    {
        super(nullBitMap, W_WAREHOUSE_SK);
        this.wWarehouseSk = wWarehouseSk;
        this.wWarehouseId = wWarehouseId;
        this.wWarehouseName = wWarehouseName;
        this.wWarehouseSqFt = wWarehouseSqFt;
        this.wAddress = wAddress;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(wWarehouseSk, W_WAREHOUSE_SK),
                getStringOrNull(wWarehouseId, W_WAREHOUSE_ID),
                getStringOrNull(wWarehouseName, W_WAREHOUSE_NAME),
                getStringOrNull(wWarehouseSqFt, W_WAREHOUSE_SQ_FT),
                getStringOrNull(wAddress.getStreetNumber(), W_ADDRESS_STREET_NUM),
                getStringOrNull(wAddress.getStreetName(), W_ADDRESS_STREET_NAME1),
                getStringOrNull(wAddress.getStreetType(), W_ADDRESS_STREET_TYPE),
                getStringOrNull(wAddress.getSuiteNumber(), W_ADDRESS_SUITE_NUM),
                getStringOrNull(wAddress.getCity(), W_ADDRESS_CITY),
                getStringOrNull(wAddress.getCounty(), W_ADDRESS_COUNTY),
                getStringOrNull(wAddress.getState(), W_ADDRESS_STATE),
                getStringOrNull(format("%05d", wAddress.getZip()), W_ADDRESS_ZIP),
                getStringOrNull(wAddress.getCountry(), W_ADDRESS_COUNTRY),
                getStringOrNull(wAddress.getGmtOffset(), W_ADDRESS_GMT_OFFSET));
    }
}
