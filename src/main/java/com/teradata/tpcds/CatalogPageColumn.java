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

package com.teradata.tpcds;

import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.CATALOG_PAGE;

public enum CatalogPageColumn
        implements Column
{
    CP_CATALOG_PAGE_SK(1),
    CP_CATALOG_PAGE_ID(1),
    CP_START_DATE_ID(1),
    CP_END_DATE_ID(1),
    CP_PROMO_ID(1),
    CP_DEPARTMENT(1),
    CP_CATALOG_NUMBER(1),
    CP_CATALOG_PAGE_NUMBER(1),
    CP_DESCRIPTION(100), //S_CP_DESCRIPTION
    CP_TYPE(1),
    CP_NULLS(2);

    private final RandomNumberStream randomNumberStream;

    CatalogPageColumn(int seedsPerRow)
    {
        this.randomNumberStream = new RandomNumberStreamImpl(seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return CATALOG_PAGE;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
    }
}
