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

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;

public final class Permutations
{
    private Permutations() {}

    public static int[] makePermutation(int size, RandomNumberStream stream)
    {
        int[] numberSet = new int[size];
        for (int i = 0; i < numberSet.length; i++) {
            numberSet[i] = i;
        }

        for (int i = 0; i < numberSet.length; i++) {
            int index = generateUniformRandomInt(0, size - 1, stream);
            int temp = numberSet[i];
            numberSet[i] = numberSet[index];
            numberSet[index] = temp;
        }
        return numberSet;
    }

    public static int getPermutationEntry(int[] permuation, int index)
    {
        checkArgument(index >= 1, "index is not greater than or equal to 1: %s", index);
        return permuation[index - 1] + 1;
    }
}
