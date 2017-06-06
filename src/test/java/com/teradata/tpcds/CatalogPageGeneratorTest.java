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
import static com.teradata.tpcds.Table.CATALOG_PAGE;

public class CatalogPageGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CATALOG_PAGE);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "0bbac1b8bdcf8ce2d5f0034980ee0196");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, TEST_SESSION, "0bbac1b8bdcf8ce2d5f0034980ee0196");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "a5daa0d93ecde8bd9f6ed79cd3b63916");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "1600703ea0a101b6be7485beda5297d6");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "9fb3592ccee034ba2fcddb57231f0b8b");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "9875449411d1accad2066ece4afb27f7");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "ac12a69e77015b8f091b475426cac8d4");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "3490dfd706dd74f2b983c090a433a581");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "922feb3673dbef4694ff8e1d7259afae");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "95c62089b14cfa53251995936030ab35");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_PAGE), CATALOG_PAGE, session, "0bbac1b8bdcf8ce2d5f0034980ee0196");
    }
}
