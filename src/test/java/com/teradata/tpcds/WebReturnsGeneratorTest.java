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

import com.teradata.tpcds.Parallel.ChunkBoundaries;
import org.testng.annotations.Test;

import static com.teradata.tpcds.GeneratorAssertions.assertPartialMD5;
import static com.teradata.tpcds.Parallel.splitWork;
import static com.teradata.tpcds.Session.getDefaultSession;
import static com.teradata.tpcds.Table.WEB_RETURNS;

public class WebReturnsGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(WEB_RETURNS);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_RETURNS), WEB_RETURNS, session, "1730b040c336f8bbc4ce7db04ce9a82d");
    }

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, session.getScaling().getRowCount(WEB_RETURNS), WEB_RETURNS, session, "e45390d32d1698fef71f05f474a4d748");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, 10000, WEB_RETURNS, session, "c61503b6c9873421de57d6569f184362");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "37424e80bf91958fcd60166a6ae239f9");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(100), "0fa817c32180f437299b2dd91ddc7fa9");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1000), "c25cc7bf054d3988ccb439abc3642682");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "4a55e233abd7dfd4816245e885fe85c4");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(100), "f6b8e4c24c8a7f3ca0658eb84ea3b0aa");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1000), "730d04e6ca36c6f00f4ee7be033ae6c8");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "30650f38714ad74eab33921dab172103");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1000), "357fe06aefddc031787cfc9325840883");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(10000), "ff8fd7304d71020e015d540bdc024aec");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "ea835ad63c39e1a42dd95b3ad8350c0b");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1000), "068bc2a6ee5cbe746aa525852c3a09aa");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(10000), "e3bae69f3cd8d94e14e605a21af02b5c");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "8a1385e7808d886dd68ce2af7c0b3904");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(10000), "1567f1bf4d190739d681c45fa372ad81");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(100000), "9082045950daa2a46a45c58785695284");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "03a71bba8d64ec69d8cb810c5dc1e5a9");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(10000), "63ea6aa9318aea63bccf3cce03ed861e");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(100000), "0003eba5d83d1c8e21ec14060623b309");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1), "93a120ce488b62ae533cb42f0fae8415");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(100000), "543be56650506577bf6c98046121e82d");

        chunkBoundaries = splitWork(WEB_RETURNS, session.withChunkNumber(1000000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_RETURNS, session.withChunkNumber(1000000), "046a5de598005d3b9536e96aa718e735");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, 10000, WEB_RETURNS, session, "fa6f21b4e1eedc493b369eb5cb580566");
    }
}
