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

import java.io.IOException;

import static com.teradata.tpcds.GeneratorAssertions.assertPartialMD5;
import static com.teradata.tpcds.Parallel.splitWork;
import static com.teradata.tpcds.Session.getDefaultSession;
import static com.teradata.tpcds.Table.CATALOG_RETURNS;

public class CatalogReturnsGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CATALOG_RETURNS);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_RETURNS), CATALOG_RETURNS, session, "71832e5ab94135c4ed247bf7c4713a96");
    }

    @Test
    public void testScaleFactor1()
            throws IOException
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, 10000, CATALOG_RETURNS, session, "933f40225f6bfb582de052e405a97760");
        assertPartialMD5(10001, 20000, CATALOG_RETURNS, session, "3485d78a050a5049587139f45dcbb1eb");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10).withParallelism(100);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "5490ddc714a439a60720039f530ae245");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "cfeb7061ed8cffe373153d7e5df78391");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "1a0c0e9fd930a1c0598f6c6cf5f1d4f2");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "d4365f6053abc03f0953fccb8892832e");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "563963ff86da94d19aa33daa7b48811c");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "57a96b8078f853ffb2c5a33dda36f471");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(1000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "15d8559538a43e6a605d8a3d0a549f40");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "dceb0fc4954640c5849d9c7a0e6a7019");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "94ec852a546ddd9c74a69e7829849047");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "25e867c01ff144f91b5af3b1610b766b");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "89572e59ee5179fb5881d1ebc503fbac");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "5b9efc78049b9a92dbf2035ed3000c0c");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "e4d1138b0abb20cef6eb65c1fab1d1c5");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "e0fd39b9ba04765a12cc41a1db726131");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "4aa3366987751c33d468f0f1d62377fb");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "4ab973ec37af2223cd029e47b9a43800");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "6603218ac8f5ec1959a901cc146afb74");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "c7f76458112bce1707d1c2667bdda92d");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "2a7b38f3b8fa2be5f05b61ee0ac85591");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "98a5dadf6bd2fb4d238b42f2ae777845");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "8bf004386391f0bd3e9e65f1446ea2e5");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "f6b25db057dfd6905d59a2e015d9efd1");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "995284b4c4d649bdfd06160d30995a3a");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1000000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "1940a4bb734bfee98059d204d8121fb6");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15).withParallelism(150);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "044a7966267d56185223ed625e85b5a9");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(10));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "516923a4c8cc28d8e8432822c5d969e5");

        chunkBoundaries = splitWork(CATALOG_RETURNS, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_RETURNS, session, "b17738ba13f92a2ec0d5a39ebc2f2080");
    }
}
