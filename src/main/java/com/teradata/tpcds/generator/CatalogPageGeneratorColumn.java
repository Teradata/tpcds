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

import static com.teradata.tpcds.Table.CATALOG_PAGE;

public enum CatalogPageGeneratorColumn
        implements GeneratorColumn
{
    CP_CATALOG_PAGE_SK(35, 1),
    CP_CATALOG_PAGE_ID(36, 1),
    CP_START_DATE_ID(37, 1),
    CP_END_DATE_ID(38, 1),
    CP_PROMO_ID(39, 1),
    CP_DEPARTMENT(40, 1),
    CP_CATALOG_NUMBER(41, 1),
    CP_CATALOG_PAGE_NUMBER(42, 1),
    CP_DESCRIPTION(43, 100), //S_CP_DESCRIPTION
    CP_TYPE(44, 1),
    CP_NULLS(45, 2);

    private final int globalColumnNumber;
    private final int seedsPerRow;

    CatalogPageGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return CATALOG_PAGE;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }

    @Override
    public int getSeedsPerRow()
    {
        return seedsPerRow;
    }
}
