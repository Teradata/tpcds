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

import org.testng.annotations.Test;

import static com.teradata.tpcds.SlowlyChangingDimensionUtils.getValueForSlowlyChangingDimension;
import static org.testng.Assert.assertEquals;

public class SlowlyChangingDimensionUtilsTest
{
    @Test
    public void testUpdatesDimensionForNewKey()
    {
        int oldInt = 1;
        int newInt = 2;
        boolean isNewKey = true;
        int flag = 11;
        int result = getValueForSlowlyChangingDimension(flag, isNewKey, oldInt, newInt);
        assertEquals(result, newInt);
    }

    @Test
    public void testUpdatesDimensionForEvenFlag()
    {
        int oldInt = 1;
        int newInt = 2;
        boolean isNewKey = false;
        int flag = 10;
        int result = getValueForSlowlyChangingDimension(flag, isNewKey, oldInt, newInt);
        assertEquals(result, newInt);
    }

    @Test
    public void testDoesNotUpdateDimensionForOddFlag()
    {
        int oldInt = 1;
        int newInt = 2;
        boolean isNewKey = false;
        int flag = 11;
        int result = getValueForSlowlyChangingDimension(flag, isNewKey, oldInt, newInt);
        assertEquals(result, oldInt);
    }
}
