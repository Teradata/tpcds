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

import static com.teradata.tpcds.GeneratorAssertions.assertPartialMD5;
import static com.teradata.tpcds.Session.getDefaultSession;
import static com.teradata.tpcds.Table.WAREHOUSE;

public class WarehouseGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(WAREHOUSE);

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, TEST_SESSION, "f56789e8b724b989d74e213e0686052f");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "e0c56fe622774d09c9dec42029881ad5");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "05358fc9e114230aa71fba7449525ac8");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "b1a2e84f4d3ff562ed579234b147acaf");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "5c0c21d5c03c630a0ab6fc5c65358a2c");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "f38de2d82bc6d94c316daa9735e2ebf6");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "90a9e1209e5951086366ca99ef5e4089");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "b0230571a2396f768712150bbbe75d11");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "2ea9fdfa9cc61f34766b8d178f8a38c3");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(WAREHOUSE), WAREHOUSE, session, "f56789e8b724b989d74e213e0686052f");
    }
}
