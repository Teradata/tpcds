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

import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;

public enum CustomerDemographicsGeneratorColumn
        implements GeneratorColumn
{
    CD_DEMO_SK(149, 1),
    CD_GENDER(150, 1),
    CD_MARITAL_STATUS(151, 1),
    CD_EDUCATION_STATUS(152, 1),
    CD_PURCHASE_ESTIMATE(153, 1),
    CD_CREDIT_RATING(154, 1),
    CD_DEP_COUNT(155, 1),
    CD_DEP_EMPLOYED_COUNT(156, 1),
    CD_DEP_COLLEGE_COUNT(157, 1),
    CD_NULLS(158, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    CustomerDemographicsGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return CUSTOMER_DEMOGRAPHICS;
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
