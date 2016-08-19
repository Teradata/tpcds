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

import static com.teradata.tpcds.Table.WAREHOUSE;

public enum WarehouseColumn
        implements Column
{
    W_WAREHOUSE_SK(),
    W_WAREHOUSE_ID(),
    W_WAREHOUSE_NAME(),
    W_WAREHOUSE_SQ_FT(),
    W_STREET_NUMBER(),
    W_STREET_NAME(),
    W_STREET_TYPE(),
    W_SUITE_NUMBER(),
    W_CITY(),
    W_COUNTY(),
    W_STATE(),
    W_ZIP(),
    W_COUNTRY(),
    W_GMT_OFFSET();

    @Override
    public Table getTable()
    {
        return WAREHOUSE;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
