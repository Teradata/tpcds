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

import org.testng.annotations.Test;

import static com.teradata.tpcds.distribution.AddressDistributions.StreetNamesWeights.DEFAULT;
import static com.teradata.tpcds.distribution.AddressDistributions.StreetNamesWeights.HALF_EMPTY;
import static com.teradata.tpcds.distribution.AddressDistributions.pickRandomStreetName;
import static org.testng.Assert.assertEquals;

public class StreetNamesDistributionTest
{
    @Test
    public void testPickRandomStreetNameDefault() throws Exception
    {
        String result = pickRandomStreetName(DEFAULT, new TestingRandomNumberStream(7000));
        assertEquals(result, "Center");
    }

    @Test
    public void testPickRandomStreetNameHalfEmpty() throws Exception
    {
        String result = pickRandomStreetName(HALF_EMPTY, new TestingRandomNumberStream(7000));
        assertEquals(result, "");

        result = pickRandomStreetName(HALF_EMPTY, new TestingRandomNumberStream(324000));
        assertEquals(result, "Center");
    }
}
