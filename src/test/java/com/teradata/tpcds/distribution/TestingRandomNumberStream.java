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

/**
 * Random number stream with constant output for testing purposes
 */
public class TestingRandomNumberStream
        implements RandomNumberStream
{
    private final long value;

    public TestingRandomNumberStream(long value)
    {
        this.value = value;
    }

    @Override
    public long nextRandom()
    {
        return value;
    }

    @Override
    public double nextRandomDouble()
    {
        return value * 0.5;
    }

    @Override
    public void skipRows(long numberOfValuesToSkip) {}

    @Override
    public void resetSeed() {}

    @Override
    public int getSeedsUsed()
    {
        return 0;
    }

    @Override
    public void resetSeedsUsed() {}

    @Override
    public int getSeedsPerRow()
    {
        return 0;
    }
}
