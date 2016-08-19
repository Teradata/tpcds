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

public enum CustomerColumn
        implements Column
{
    C_CUSTOMER_SK(),
    C_CUSTOMER_ID(),
    C_CURRENT_CDEMO_SK(),
    C_CURRENT_HDEMO_SK(),
    C_CURRENT_ADDR_SK(),
    C_FIRST_SHIPTO_DATE_SK(),
    C_FIRST_SALES_DATE_SK(),
    C_SALUTATION(),
    C_FIRST_NAME(),
    C_LAST_NAME(),
    C_PREFERRED_CUST_FLAG(),
    C_BIRTH_DAY(),
    C_BIRTH_MONTH(),
    C_BIRTH_YEAR(),
    C_BIRTH_COUNTRY(),
    C_LOGIN(),
    C_EMAIL_ADDRESS(),
    C_LAST_REVIEW_DATE();

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
}
