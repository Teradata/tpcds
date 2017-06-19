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
import static com.teradata.tpcds.Table.CATALOG_SALES;

public class CatalogSalesGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CATALOG_SALES);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_SALES), CATALOG_SALES, session, "f1dc6cca6abb04375752c1990967f342");
    }

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_SALES), CATALOG_SALES, session, "51a0bc401b4b64d94736634b54068240");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10).withParallelism(100).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "b403c3f51ce370d97f80ebf0fb4959d1");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "da9f9f417e50907c3369afdc2bcc12fb");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "5a2a60841cec97085560b072f57c7af9");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "3ef5fd6bc447993011de83ca90eb0da7");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "57fb3abd448d7329bc34faad64d5aafa");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "21470c27ede5df27b40aaa66c1294e89");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "1264a75e95966f401843e9bbed923474");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "03bbf4492854a6eeb28bfdb554bff9ad");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "78673e1cc3962a8d83638827b65fc463");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "44cb3da52ae2f57fe7ed7b95389fb5ce");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "a242fa149fee85252c6e061fc4949edf");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "ca49525d798d0595d214889fac628089");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "d9ff55d9a193c4810015678155cc0fe6");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "2ecb634fd601af4f2d29463db633018c");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "2d8bdbf41eed9997cdfc31c968c30d60");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "8fc4167e91bbfbbfff4c6d8c13a3d10b");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "f875b390866616ea059baefb0de1f292");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "8845312014eebb1296a72fa1ad82b93e");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "337cea350ae2d01386e0621bf6e05ad0");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "b966b74a54940de2ec3acffd2784c071");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "f4baa1b5939f6e7468f5108b433b1baa");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "694feb93f747bf636a8a99788f659eb9");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "6867d6c767565886bc779584fc8a0405");

        session = session.withChunkNumber(1000000);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "44ab3571eff85faf1525b88bb6d31273");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15).withParallelism(150).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "fee83b3dd32f3b23a37431bcd115b9ce");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "99d99644f09fb052e2ada76cf109b3ec");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CATALOG_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "1c3dc333ad950c06a0fca3d451ce4701");
    }
}
