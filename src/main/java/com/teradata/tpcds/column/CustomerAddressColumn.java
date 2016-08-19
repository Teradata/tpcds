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

import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;

public enum CustomerAddressColumn
        implements Column
{
    CA_ADDRESS_SK(),
    CA_ADDRESS_ID(),
    CA_STREET_NUMBER(),
    CA_STREET_NAME(),
    CA_STREET_TYPE(),
    CA_SUITE_NUMBER(),
    CA_CITY(),
    CA_COUNTY(),
    CA_STATE(),
    CA_ZIP(),
    CA_COUNTRY(),
    CA_GMT_OFFSET(),
    CA_LOCATION_TYPE();

    @Override
    public Table getTable()
    {
        return CUSTOMER_ADDRESS;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
