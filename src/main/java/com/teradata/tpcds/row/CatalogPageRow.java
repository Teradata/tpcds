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

package com.teradata.tpcds.row;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_CATALOG_NUMBER;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_CATALOG_PAGE_ID;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_CATALOG_PAGE_NUMBER;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_CATALOG_PAGE_SK;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_DEPARTMENT;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_DESCRIPTION;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_END_DATE_ID;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_START_DATE_ID;
import static com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn.CP_TYPE;

public class CatalogPageRow
        extends TableRowWithNulls
{
    private final long cpCatalogPageSk;
    private final String cpCatalogPageId;
    private final long cpStartDateId;
    private final long cpEndDateId;
    private final String cpDepartment;
    private final int cpCatalogNumber;
    private final int cpCatalogPageNumber;
    private final String cpDescription;
    private final String cpType;

    public CatalogPageRow(long cpCatalogPageSk,
                          String cpCatalogPageId,
                          long cpStartDateId,
                          long cpEndDateId,
                          String cpDepartment,
                          int cpCatalogNumber,
                          int cpCatalogPageNumber,
                          String cpDescription,
                          String cpType,
                          long nullBitMap)
    {
        super(nullBitMap, CP_CATALOG_PAGE_SK);
        this.cpCatalogPageSk = cpCatalogPageSk;
        this.cpCatalogPageId = cpCatalogPageId;
        this.cpStartDateId = cpStartDateId;
        this.cpEndDateId = cpEndDateId;
        this.cpDepartment = cpDepartment;
        this.cpCatalogNumber = cpCatalogNumber;
        this.cpCatalogPageNumber = cpCatalogPageNumber;
        this.cpDescription = cpDescription;
        this.cpType = cpType;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(cpCatalogPageSk, CP_CATALOG_PAGE_SK),
                            getStringOrNull(cpCatalogPageId, CP_CATALOG_PAGE_ID),
                            getStringOrNullForKey(cpStartDateId, CP_START_DATE_ID),
                            getStringOrNullForKey(cpEndDateId, CP_END_DATE_ID),
                            getStringOrNull(cpDepartment, CP_DEPARTMENT),
                            getStringOrNull(cpCatalogNumber, CP_CATALOG_NUMBER),
                            getStringOrNull(cpCatalogPageNumber, CP_CATALOG_PAGE_NUMBER),
                            getStringOrNull(cpDescription, CP_DESCRIPTION),
                            getStringOrNull(cpType, CP_TYPE));
    }
}
