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

import static com.teradata.tpcds.Table.STORE_RETURNS;
import static com.teradata.tpcds.column.ColumnTypes.IDENTIFIER;
import static com.teradata.tpcds.column.ColumnTypes.INTEGER;
import static com.teradata.tpcds.column.ColumnTypes.decimal;

public enum StoreReturnsColumn
        implements Column
{
    SR_RETURNED_DATE_SK(IDENTIFIER),
    SR_RETURN_TIME_SK(IDENTIFIER),
    SR_ITEM_SK(IDENTIFIER),
    SR_CUSTOMER_SK(IDENTIFIER),
    SR_CDEMO_SK(IDENTIFIER),
    SR_HDEMO_SK(IDENTIFIER),
    SR_ADDR_SK(IDENTIFIER),
    SR_STORE_SK(IDENTIFIER),
    SR_REASON_SK(IDENTIFIER),
    SR_TICKET_NUMBER(IDENTIFIER),
    SR_RETURN_QUANTITY(INTEGER),
    SR_RETURN_AMT(decimal(7, 2)),
    SR_RETURN_TAX(decimal(7, 2)),
    SR_RETURN_AMT_INC_TAX(decimal(7, 2)),
    SR_FEE(decimal(7, 2)),
    SR_RETURN_SHIP_COST(decimal(7, 2)),
    SR_REFUNDED_CASH(decimal(7, 2)),
    SR_REVERSED_CHARGE(decimal(7, 2)),
    SR_STORE_CREDIT(decimal(7, 2)),
    SR_NET_LOSS(decimal(7, 2));

    private final ColumnType type;

    StoreReturnsColumn(ColumnType type)
    {
        this.type = type;
    }

    @Override
    public Table getTable()
    {
        return STORE_RETURNS;
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
