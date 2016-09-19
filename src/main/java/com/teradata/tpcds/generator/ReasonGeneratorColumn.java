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

import static com.teradata.tpcds.Table.REASON;

public enum ReasonGeneratorColumn
        implements GeneratorColumn
{
    R_REASON_SK(248, 1),
    R_REASON_ID(249, 1),
    R_REASON_DESCRIPTION(250, 1),
    R_NULLS(251, 2);

    private final int globalColumnNumber;
    private final int seedsPerRow;

    ReasonGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return REASON;
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
