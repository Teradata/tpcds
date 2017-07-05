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
import static com.teradata.tpcds.Table.STORE_RETURNS;

public class StoreReturnsGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(STORE_RETURNS);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_01()
    {
        Session session = TEST_SESSION.withScale(0.01);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE_RETURNS), STORE_RETURNS, session, "2d6e049368329a08b9775f810fcbb210");
    }

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE_RETURNS), STORE_RETURNS, session, "9009d804c02ee839e0b2ecd5fb4ae03f");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10).withParallelism(100).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "c89528039dd70326102f49f255c03415");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "e3ebd49ce78c2e376a6dd825a29870c0");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "3dd663daff554e3b634f72671bb250b8");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "ea2db4a02afce7fbd33f4ee20360d1e1");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "e579d47b46805d82efc80129c7a68b3e");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "a507c5623f24c5d37c27ec649d99d29b");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "9d2b5c7441a78f39f6eeeb48695a7799");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "d84eafbb54b442e247ae326f3fde3cb0");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "597fc3a650d0cd7a07dc6b4d4864f2e9");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "3ae1c3ee832e820d8dcb5fb4f881a4a2");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "7e4e723ab65d94fd0e0b763f42e51664");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "1d6d8b7b0244592cebadb7c41d9b7903");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "479aab948ee79be9ba7477099bee68b1");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "097b3399c6515b9f58d44bb8144f6420");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "dfb697b4cee604137de35a8644cb1fb4");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "31edfc553ee5259f3bfb7434e3740f90");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "54d5e7543d4a7cf9bba574b2e1117a42");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "538f0821675a344319ab6776bffb5e37");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "03eea585b45b33111e102ade02184d30");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "1669d086b71ec961c5ca4e0a721fc9c6");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "78d6ad670958883c159a223e98766566");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "72c2b0f767a5eb2c2c514dc56c8a56d9");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "21108e1ccf189cb6d3540075532bc943");

        session = session.withChunkNumber(1000000);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "36f4ec14ea5b2613a967499108eda043");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15).withParallelism(150).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "e8e59356f8a7136dbbe809559eb20ec0");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "c912061c9c702e81ab7ac3f026bc79a8");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_RETURNS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_RETURNS, session, "6ca86235821af2231be77531510e2900");
    }
}
