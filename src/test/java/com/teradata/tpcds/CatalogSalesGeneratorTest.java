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
        assertPartialMD5(1, session.getScaling().getRowCount(CATALOG_SALES), CATALOG_SALES, session, "161809563a9e73396eb6ecd03715195c");
    }

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, 10000, CATALOG_SALES, session, "4619dc9c59c9a4314fb3e66c68fe5d5d");
        assertPartialMD5(10001, 20000, CATALOG_SALES, session, "80193c6d6bea6f9ec7bdb27b6db6478b");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10).withParallelism(100);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "b403c3f51ce370d97f80ebf0fb4959d1");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "da9f9f417e50907c3369afdc2bcc12fb");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "5a2a60841cec97085560b072f57c7af9");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "3ef5fd6bc447993011de83ca90eb0da7");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "57fb3abd448d7329bc34faad64d5aafa");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "21470c27ede5df27b40aaa66c1294e89");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "1264a75e95966f401843e9bbed923474");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "03bbf4492854a6eeb28bfdb554bff9ad");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "78673e1cc3962a8d83638827b65fc463");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "44cb3da52ae2f57fe7ed7b95389fb5ce");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "a242fa149fee85252c6e061fc4949edf");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "ca49525d798d0595d214889fac628089");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "d9ff55d9a193c4810015678155cc0fe6");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "2ecb634fd601af4f2d29463db633018c");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "2d8bdbf41eed9997cdfc31c968c30d60");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "8fc4167e91bbfbbfff4c6d8c13a3d10b");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "f875b390866616ea059baefb0de1f292");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "8845312014eebb1296a72fa1ad82b93e");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "337cea350ae2d01386e0621bf6e05ad0");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "b966b74a54940de2ec3acffd2784c071");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "f4baa1b5939f6e7468f5108b433b1baa");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "694feb93f747bf636a8a99788f659eb9");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "6867d6c767565886bc779584fc8a0405");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1000000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "44ab3571eff85faf1525b88bb6d31273");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15).withParallelism(150);
        ChunkBoundaries chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "fee83b3dd32f3b23a37431bcd115b9ce");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(10));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "99d99644f09fb052e2ada76cf109b3ec");

        chunkBoundaries = splitWork(CATALOG_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CATALOG_SALES, session, "1c3dc333ad950c06a0fca3d451ce4701");
    }
}
