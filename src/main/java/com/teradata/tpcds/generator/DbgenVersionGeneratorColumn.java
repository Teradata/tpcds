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

import static com.teradata.tpcds.Table.DBGEN_VERSION;

public enum DbgenVersionGeneratorColumn
        implements GeneratorColumn
{
    DV_VERSION(476, 1),
    DV_CREATE_DATE(477, 1),
    DV_CREATE_TIME(478, 1),
    DV_CMDLINE_ARGS(479, 1);

    private final int globalColumnNumber;
    private final int seedsPerRow;

    DbgenVersionGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.seedsPerRow = seedsPerRow;
    }

    @Override
    public Table getTable()
    {
        return DBGEN_VERSION;
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

    @Override
    public int getSeedsPerRow()
    {
        return seedsPerRow;
    }
}
