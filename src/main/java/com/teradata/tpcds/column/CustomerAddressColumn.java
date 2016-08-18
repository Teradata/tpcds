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

import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;

public enum CustomerAddressColumn
        implements Column
{
    CA_ADDRESS_SK(133, 1),
    CA_ADDRESS_ID(134, 1),
    CA_ADDRESS_STREET_NUM(135, 1),
    CA_ADDRESS_STREET_NAME(136, 1),
    CA_ADDRESS_STREET_TYPE(137, 1),
    CA_ADDRESS_SUITE_NUM(138, 1),
    CA_ADDRESS_CITY(139, 1),
    CA_ADDRESS_COUNTY(140, 1),
    CA_ADDRESS_STATE(141, 1),
    CA_ADDRESS_ZIP(142, 1),
    CA_ADDRESS_COUNTRY(143, 1),
    CA_ADDRESS_GMT_OFFSET(144, 1),
    CA_LOCATION_TYPE(145, 1),
    CA_NULLS(146, 2),
    CA_ADDRESS(147, 7),
    CA_ADDRESS_STREET_NAME2(148, 1);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    CustomerAddressColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return CUSTOMER_ADDRESS;
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

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
