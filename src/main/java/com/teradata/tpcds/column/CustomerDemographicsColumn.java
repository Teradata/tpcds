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

import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;

public enum CustomerDemographicsColumn
        implements Column
{
    CD_DEMO_SK(),
    CD_GENDER(),
    CD_MARITAL_STATUS(),
    CD_EDUCATION_STATUS(),
    CD_PURCHASE_ESTIMATE(),
    CD_CREDIT_RATING(),
    CD_DEP_COUNT(),
    CD_DEP_EMPLOYED_COUNT(),
    CD_DEP_COLLEGE_COUNT();

    @Override
    public Table getTable()
    {
        return CUSTOMER_DEMOGRAPHICS;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
