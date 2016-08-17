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

import static com.teradata.tpcds.Results.constructResults;
import static com.teradata.tpcds.Session.getDefaultSession;
import static com.teradata.tpcds.Table.DBGEN_VERSION;
import static org.testng.Assert.assertTrue;

public class DbgenVersionTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(DBGEN_VERSION);

    @Test
    public void testSingleRowCorrectlyGenerated()
    {
        Results outputRows = constructResults(DBGEN_VERSION, 1, 1, TEST_SESSION);
        String row = outputRows.iterator().next().get(0);

        assertTrue(row.startsWith("2.0.0|"));
        assertTrue(row.endsWith("|--table dbgen_version|\n"));
    }
}
