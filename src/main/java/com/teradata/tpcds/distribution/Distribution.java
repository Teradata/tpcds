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

package com.teradata.tpcds.distribution;

import com.teradata.tpcds.random.RandomNumberStream;

//TODO: ultimately these will all be instance methods, or they'll have to get distributions passed in.
// until all that's worked out though, this makes for an easier stand in.
public abstract class Distribution
{
    public static String pickRandomValue(int valueSet, int weightSet, RandomNumberStream randomNumberStream)
    {
        //TODO: implement
        return "foo";
    }

    public static int getWeight(int index, int weightSet)
    {
        //TODO: implement
        return 0;
    }

    public static int getSize()
    {
        //TODO: implement
        return 0;
    }

    public static String getMemberString(int valueSet, int weightSet)
    {
        //TODO: implement.  Also rename to something clearer, e.g. getValueForWeight
        return "foo";
    }

    public static int getMemberInt(int valueSet, int weightSet)
    {
        //TODO: implement.  Also rename to something clearer, e.g. getValueForWeight
        return 0;
    }
}
