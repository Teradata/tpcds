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
import static com.teradata.tpcds.Table.CALL_CENTER;

public class CallCenterGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CALL_CENTER);

    // A scale of 0.1 is not part of the spec. Support for it was added because of Presto. Since
    // the scale is not part of the spec there is no way to verify its correctness against the
    // C generator. This test is testing the Java generator against itself and was added to
    // ensure no regressions are introduced for this scale in the future.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, TEST_SESSION, "9cd59d54edf7cdbad6cff8c17b66a544");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, TEST_SESSION, "cc9aabc63eb8603bd7330b6735ed0961");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "235909679f4d125e769aa38eb16e9098");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "a9863ae0f09a67890c1b94646d2e9063");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "db093f3998a70736e1b36e87caf3c217");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "0069f84ceca50c0d999c8fe8992bd2f3");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "228b573a4b8d29dccfe3c0d1e729a319");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "26f1e54ac3e00a64e7d3b489656b41a4");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "49d6b3dc249197696d92351fb5b42d55");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "627d14cd9875dfa10135e716f1684192");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(CALL_CENTER), CALL_CENTER, session, "ec264855d31152d7ae3d0d25174158da");
    }
}
