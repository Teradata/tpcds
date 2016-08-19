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

import static com.teradata.tpcds.Table.CATALOG_PAGE;

public enum CatalogPageColumn
        implements Column
{
    CP_CATALOG_PAGE_SK(),
    CP_CATALOG_PAGE_ID(),
    CP_START_DATE_SK(),
    CP_END_DATE_SK(),
    CP_DEPARTMENT(),
    CP_CATALOG_NUMBER(),
    CP_CATALOG_PAGE_NUMBER(),
    CP_DESCRIPTION(),
    CP_TYPE();

    @Override
    public Table getTable()
    {
        return CATALOG_PAGE;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
