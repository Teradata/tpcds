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
import static com.teradata.tpcds.Table.STORE;

public class StoreGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(STORE);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_01()
    {
        Session session = TEST_SESSION.withScale(0.01);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "2efbb51460dd4c7c41cb0efeaef44b5e");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(STORE), STORE, TEST_SESSION, "80082d03e1b01340e19db3187d8edbd6");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "430a01467a2d55d0e9a1bebad4f1c44b");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "99de4a21237d03a8576428f34c6c689f");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "f0b39b1cf06111128150577cbaeb5db0");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "63b90d0e6bbe55c5d76652be3ca98177");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "4a2f6b12559ae710caf4f6a00e748f20");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "f250d37ce03cbb695d6b6abbedacc489");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "1751a73d45eb5877d10674de45f0c8f2");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "22cd1415cecb71a92f2d67016e261e21");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE), STORE, session, "bfcf946d0ef0519e060e80234c309b13");
    }
}
