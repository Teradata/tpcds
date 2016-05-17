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

public class CatalogPageRow
        implements TableRow
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
    private final long nullBitMap;

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
        this.cpCatalogPageSk = cpCatalogPageSk;
        this.cpCatalogPageId = cpCatalogPageId;
        this.cpStartDateId = cpStartDateId;
        this.cpEndDateId = cpEndDateId;
        this.cpDepartment = cpDepartment;
        this.cpCatalogNumber = cpCatalogNumber;
        this.cpCatalogPageNumber = cpCatalogPageNumber;
        this.cpDescription = cpDescription;
        this.cpType = cpType;
        this.nullBitMap = nullBitMap;
    }

    @Override
    public String toFormattedString()
    {
        // TODO: implement
        throw new RuntimeException("Not yet implemented");
    }
}
