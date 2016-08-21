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

import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.charr;
import static com.teradata.tpcds.column.ColumnTypes.varchar;

public enum CustomerColumn
        implements Column
{
    C_CUSTOMER_SK(IDENTIFIER),
    C_CUSTOMER_ID(charr(16)),
    C_CURRENT_CDEMO_SK(IDENTIFIER),
    C_CURRENT_HDEMO_SK(IDENTIFIER),
    C_CURRENT_ADDR_SK(IDENTIFIER),
    C_FIRST_SHIPTO_DATE_SK(IDENTIFIER),
    C_FIRST_SALES_DATE_SK(IDENTIFIER),
    C_SALUTATION(charr(10)),
    C_FIRST_NAME(charr(20)),
    C_LAST_NAME(charr(30)),
    C_PREFERRED_CUST_FLAG(charr(1)),
    C_BIRTH_DAY(INTEGER),
    C_BIRTH_MONTH(INTEGER),
    C_BIRTH_YEAR(INTEGER),
    C_BIRTH_COUNTRY(varchar(20)),
    C_LOGIN(charr(13)),
    C_EMAIL_ADDRESS(charr(50)),
    C_LAST_REVIEW_DATE(IDENTIFIER);

    private final ColumnType type;

    CustomerColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return CUSTOMER;
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
