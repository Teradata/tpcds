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

import com.teradata.tpcds.row.CustomerAddressRow;
import com.teradata.tpcds.type.Address;

import static com.teradata.tpcds.BusinessKeyGenerator.makeBusinessKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.column.CustomerAddressColumn.CA_ADDRESS;
import static com.teradata.tpcds.column.CustomerAddressColumn.CA_LOCATION_TYPE;
import static com.teradata.tpcds.column.CustomerAddressColumn.CA_NULLS;
import static com.teradata.tpcds.distribution.LocationTypesDistribution.LocationTypeWeights.UNIFORM;
import static com.teradata.tpcds.distribution.LocationTypesDistribution.pickRandomLocationType;
import static com.teradata.tpcds.type.Address.makeAddressForColumn;

public class CustomerAddressRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(CA_NULLS);
        long caAddrSk = rowNumber;
        String caAddrId = makeBusinessKey(rowNumber);
        Address caAddr = makeAddressForColumn(CA_ADDRESS, session.getScaling());
        String caLocationType = pickRandomLocationType(CA_LOCATION_TYPE.getRandomNumberStream(), UNIFORM);
        return new RowGeneratorResult(new CustomerAddressRow(nullBitMap, caAddrSk, caAddrId, caAddr, caLocationType));
    }

    @Override
    public void reset() {}
}
