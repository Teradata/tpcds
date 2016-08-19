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

import com.teradata.tpcds.column.generator.GeneratorColumn;
import com.teradata.tpcds.random.RandomNumberStream;

import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomKey;

public class Nulls
{
    private Nulls() {}

    public static long createNullBitMap(GeneratorColumn column)
    {
        Table table = column.getTable();
        RandomNumberStream randomNumberStream = column.getRandomNumberStream();

        int threshold = generateUniformRandomInt(0, 9999, randomNumberStream);
        long bitMap = generateUniformRandomKey(1, Integer.MAX_VALUE, randomNumberStream);

        // set the bitmap based on threshold and NOT NULL definitions
        if (threshold < table.getNullBasisPoints()) {
            return bitMap & ~table.getNotNullBitMap();
        }

        return 0;
    }
}
