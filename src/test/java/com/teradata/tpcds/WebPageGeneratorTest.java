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
import static com.teradata.tpcds.Table.WEB_PAGE;

public class WebPageGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(WEB_PAGE);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_01()
    {
        Session session = TEST_SESSION.withScale(0.01);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "fe617777ed9b42a280f3b76187391400");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, TEST_SESSION, "6feef91675c336d6f25e55ebbdf8c13c");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "e55695fdb2b86f96cf46e2a55b6f3748");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "8bc51851e0ddddeb22ee2373043e2b1a");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "098b86a9c97edc9a806ab021c34a2428");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "7d6385eb0ef4c2eca04883d2500791fa");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "6071ade03629c5bccd282dc97ed254bb");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "971eb80ebe061dd32ebb4500c3c24820");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "fab5267ddae6177fb117512119fa7420");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "534cdd4c0419d9d47f1392c591efea9a");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_PAGE), WEB_PAGE, session, "1c55afb0eda1adbb5993a1c7a670777e");
    }
}
