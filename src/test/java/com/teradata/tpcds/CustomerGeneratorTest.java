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
import static com.teradata.tpcds.Table.CUSTOMER;

public class CustomerGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CUSTOMER);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(CUSTOMER), CUSTOMER, session, "a423e304ec05fba3da6b9d2358b35aa7");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(CUSTOMER), CUSTOMER, TEST_SESSION, "3672ffdefac3cf00413ecef71a753636");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(CUSTOMER), CUSTOMER, session, "486a030a55d468ef15ff2ff01583e6dc");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(100).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "aee17ceb6e0eb91e619f952b6483b56f");

        session = session.withChunkNumber(50);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "082a5eba9c3e7e0a460800cd3187f924");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "0d5d8e9be795a5b726468e327727bf90");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(300).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "a383aa95f9ef6f37a97a89e54f35c295");

        session = session.withChunkNumber(50);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "5139a52ecc791304f8a253edd96fcc29");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "79c3a3edb6745f94fecd0e4f7e16cbc3");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(1000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "dc20e6a33f5535d3b85f76c6dcb2347b");

        session = session.withChunkNumber(500);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "e2974772ba45183629aea977554f7c04");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "7f64d07701b9ecd7299ab986b3200c5a");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(3000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "2121a0ea90b98d45d22c5148544a9781");

        session = session.withChunkNumber(500);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "f8b85d5d1ad9ed2570df44a7dc97a31b");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "3cadc16f1e08ee9f4a5c769df08c798d");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(10000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "2c67fc07bf4b9882713990d4e014d49e");

        session = session.withChunkNumber(5000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "e24ebde1409a8abe1f4dab35c1919e99");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "78d78d71fa3d380c02fbf51f902f74e1");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(30000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "cc64d069c57741b2654d0bb4e21e6883");

        session = session.withChunkNumber(5000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "c70fe2a14a707ee4cd7120dfcf44139e");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "12abe5c74a1195ffec7b04d90457b8d7");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(100000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "1b379ecef8408f28611b902a788c3001");

        session = session.withChunkNumber(50000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "7b6bd5faa653ee115a05e820b13827f0");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "11780d763791f4bd610dd96efd94248c");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER, session, "94a17969467a8d7b027ec0709e022942");
    }
}
