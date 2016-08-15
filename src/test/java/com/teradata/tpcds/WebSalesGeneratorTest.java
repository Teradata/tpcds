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
import static com.teradata.tpcds.Table.WEB_SALES;

public class WebSalesGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(WEB_SALES);

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, 1000, WEB_SALES, session, "e7e262c9c8c3db5107543814bb4b8572");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, 1000, WEB_SALES, session, "6e17ab59aa7b634c4e099eb0fd19d7f1");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "003d2bdf7d3ce07ee6906a7994d157cf");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "c1e4bfea4efb1ecaca4392e48eed3e86");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "f0b9f277bed90c35f8df51809f1096ce");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "62c660e52b4014caa6facf4a36e54244");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(100));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "91b62722d18913fc30e17d2c5cbf0409");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "a1d0dc638c4a4d93a412321be2239a78");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "58aa4764d29035377282369a195da8b0");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "8af5bb31f83d00407348dd29945d34c3");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "aaf85189a41288a7827c7e42b7d65ba8");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "2853a314897e7092ed1ea6a554bd55de");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "8ec0977b92fa0b1ee47ed5dde4d81abf");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "213b695e7356c00d12f3911fc5229bf4");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "07d42d1f6e6b9b930b93d56b644174ed");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "648204d56c8ece41d57cdb5ff45f42d2");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "fcbcdc6fede66ea07374429e77c67871");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "5ddb0b4788f5bfa7f8deaf529d81c0e5");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(10000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "9d3c59554daa1161c57d57a1ece0eff0");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "efdb57225ba01a1edc3f7ceed235daf7");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000);
        ChunkBoundaries chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "9ca4b5c64e4a17deaadfabc8df4553ac");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(100000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "bf009827dbc5e6aeaab3f0be1b98bd93");

        chunkBoundaries = splitWork(WEB_SALES, session.withChunkNumber(1000000));
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), WEB_SALES, session, "2abc490c24a0c401bb5f937fe1f395e3");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        assertPartialMD5(1, 1000, WEB_SALES, session, "7ee63d2ab2251a4f141d077e32dd0c57");
    }
}
