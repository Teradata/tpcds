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
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.charr;
import static com.teradata.tpcds.column.ColumnTypes.decimal;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum CustomerAddressColumn
        implements Column
{
    CA_ADDRESS_SK(IDENTIFIER),
    CA_ADDRESS_ID(charr(16)),
    CA_STREET_NUMBER(charr(10)),
    CA_STREET_NAME(varchar(60)),
    CA_STREET_TYPE(charr(15)),
    CA_SUITE_NUMBER(charr(10)),
    CA_CITY(varchar(60)),
    CA_COUNTY(varchar(30)),
    CA_STATE(charr(2)),
    CA_ZIP(charr(10)),
    CA_COUNTRY(varchar(20)),
    CA_GMT_OFFSET(decimal(5, 2)),
    CA_LOCATION_TYPE(charr(20));

    private final ColumnType type;

    CustomerAddressColumn(ColumnType type)
    {
        this.type = type;
    }

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

    @Override
    public ColumnType getType()
    {
        return type;
    }
}
