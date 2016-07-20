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
import static com.teradata.tpcds.Table.PROMOTION;

public class PromotionGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(PROMOTION);

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(PROMOTION), PROMOTION, TEST_SESSION, "acb42558d0dc5e0ab6df5a664c1629cf");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "b8e8a7741f64edc5d09fdb0453c86705");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "1d36d55e0dd471e106bf9f6a8ae7ad6a");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "cdda72807f18b8cb2603ded828e599a3");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "85a93630e8affdd2a4270bf786373a00");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "2b5d5447b48401c3bf637b04fb38235f");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "f46a5075d3ca5b0b0bf614e5524774e1");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "7bd194be9d7e07213b4bcef7d9df8782");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "20ed846a6687e222e636d83508a96e43");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, session.getScaling().getRowCount(PROMOTION), PROMOTION, session, "f5753aa1a10502488d520a8c025cd8c9");
    }
}
