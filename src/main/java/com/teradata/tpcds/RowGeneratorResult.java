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

import com.google.common.collect.ImmutableList;

import java.util.List;

public class RowGeneratorResult
{
    private final List<TableRow> rowAndChildRows;
    private final boolean shouldEndRow;

    public RowGeneratorResult(TableRow row)
    {
        this(ImmutableList.of(row), true);
    }

    public RowGeneratorResult(List<TableRow> rowAndChildRows, boolean shouldEndRow)
    {
        this.rowAndChildRows = rowAndChildRows;
        this.shouldEndRow = shouldEndRow;
    }

    public List<TableRow> getRowAndChildRows()
    {
        return rowAndChildRows;
    }

    public boolean shouldEndRow()
    {
        return shouldEndRow;
    }
}
