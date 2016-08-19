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

package com.teradata.tpcds.column.generator;

import com.teradata.tpcds.Table;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.TIME_DIM;

public enum TimeDimGeneratorColumn
        implements GeneratorColumn
{
    T_TIME_SK(340, 1),
    T_TIME_ID(341, 1),
    T_TIME(342, 1),
    T_HOUR(343, 1),
    T_MINUTE(344, 1),
    T_SECOND(345, 1),
    T_AM_PM(346, 1),
    T_SHIFT(347, 1),
    T_SUB_SHIFT(348, 1),
    T_MEAL_TIME(349, 1),
    T_NULLS(350, 1);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    TimeDimGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return TIME_DIM;
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
