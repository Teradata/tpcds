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
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_CITY;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_COUNTRY;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_COUNTY;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_GMT_OFFSET;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_ID;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_SK;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_STATE;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_STREET_NAME;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_STREET_NUM;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_STREET_TYPE;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_SUITE_NUM;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_ADDRESS_ZIP;
import static com.teradata.tpcds.generator.CustomerAddressGeneratorColumn.CA_LOCATION_TYPE;
import static java.lang.String.format;

public class CustomerAddressRow
        extends TableRowWithNulls
{
    private final long caAddrSk;
    private final String caAddrId;
    private final Address caAddress;
    private final String caLocationType;

    public CustomerAddressRow(long nullBitMap, long caAddrSk, String caAddrId, Address caAddress, String caLocationType)
    {
        super(nullBitMap, CA_ADDRESS_SK);
        this.caAddrSk = caAddrSk;
        this.caAddrId = caAddrId;
        this.caAddress = caAddress;
        this.caLocationType = caLocationType;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(caAddrSk, CA_ADDRESS_SK),
                getStringOrNull(caAddrId, CA_ADDRESS_ID),
                getStringOrNull(caAddress.getStreetNumber(), CA_ADDRESS_STREET_NUM),
                getStringOrNull(caAddress.getStreetName(), CA_ADDRESS_STREET_NAME),
                getStringOrNull(caAddress.getStreetType(), CA_ADDRESS_STREET_TYPE),
                getStringOrNull(caAddress.getSuiteNumber(), CA_ADDRESS_SUITE_NUM),
                getStringOrNull(caAddress.getCity(), CA_ADDRESS_CITY),
                getStringOrNull(caAddress.getCounty(), CA_ADDRESS_COUNTY),
                getStringOrNull(caAddress.getState(), CA_ADDRESS_STATE),
                getStringOrNull(format("%05d", caAddress.getZip()), CA_ADDRESS_ZIP),
                getStringOrNull(caAddress.getCountry(), CA_ADDRESS_COUNTRY),
                getStringOrNull(caAddress.getGmtOffset(), CA_ADDRESS_GMT_OFFSET),
                getStringOrNull(caLocationType, CA_LOCATION_TYPE));
    }
}
