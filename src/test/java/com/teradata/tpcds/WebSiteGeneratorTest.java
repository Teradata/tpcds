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
import static com.teradata.tpcds.Table.WEB_SITE;

public class WebSiteGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(WEB_SITE);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_01()
    {
        Session session = TEST_SESSION.withScale(0.01);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "5b23913ffdeaf555352bf879dbc5b6ef");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(WEB_SITE), WEB_SITE, TEST_SESSION, "de5fb00a80673cb44b4b508da75d4bcf");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "4669d52e36cd112af10e137e5d8d7697");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "48b163682ba13740d05fcbdc7564edfe");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "f499965ffc5c6edf997e1eb7c810f8e1");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "2fcdd8a9558e88467af4c075e3aeea3a");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "a5701495fda4f667fbad1731054ec6e7");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "72e03685998724ed51d92bce50dc32a1");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "272849b7c27f6ca256f2a52ced5215f7");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "83c601456f2556cf4cf6da42ee07ac83");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_SITE), WEB_SITE, session, "de5fb00a80673cb44b4b508da75d4bcf");
    }
}
