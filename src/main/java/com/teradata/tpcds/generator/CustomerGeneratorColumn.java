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

package com.teradata.tpcds.generator;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.CUSTOMER;

public enum CustomerGeneratorColumn
        implements GeneratorColumn
{
    C_CUSTOMER_SK(114, 1),
    C_CUSTOMER_ID(115, 1),
    C_CURRENT_CDEMO_SK(116, 1),
    C_CURRENT_HDEMO_SK(117, 1),
    C_CURRENT_ADDR_SK(118, 1),
    C_FIRST_SHIPTO_DATE_ID(119, 0),
    C_FIRST_SALES_DATE_ID(120, 1),
    C_SALUTATION(121, 1),
    C_FIRST_NAME(122, 1),
    C_LAST_NAME(123, 1),
    C_PREFERRED_CUST_FLAG(124, 2),
    C_BIRTH_DAY(125, 1),
    C_BIRTH_MONTH(126, 0),
    C_BIRTH_YEAR(127, 0),
    C_BIRTH_COUNTRY(128, 1),
    C_LOGIN(129, 1),
    C_EMAIL_ADDRESS(130, 23),
    C_LAST_REVIEW_DATE(131, 1),
    C_NULLS(132, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    CustomerGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return CUSTOMER;
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
