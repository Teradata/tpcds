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
import static com.teradata.tpcds.Table.STORE_SALES;

public class StoreSalesGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(STORE_SALES);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_1()
    {
        Session session = TEST_SESSION.withScale(0.1f);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE_SALES), STORE_SALES, session, "3cac4deaedd53c8bfb0392bcbba266af");
    }

    @Test
    public void testScaleFactor1()
    {
        Session session = TEST_SESSION.withScale(1);
        assertPartialMD5(1, session.getScaling().getRowCount(STORE_SALES), STORE_SALES, session, "f003b3810e042d6dd47f48506616d88d");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10).withParallelism(100).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "6d9e48c1c68357481c1726c4f3188ff0");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "1b1e82114c82251f635b0da34d94bec7");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "37d545933a5dc90262de4564bdf38a17");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(1000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "b68babc4a3d38fc07f83b916d2f3efdb");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "190132ba69a76cfddbfb5569655eeaa0");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "77da04ae48f644889fed649011c86c05");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(3000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "168f2a87c886a33d9a460a50ebbe8637");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "a3729fd4e5fc2e7db6e84f767f4585b4");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "cd8ae61701c8be67a27a806994eff227");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(10000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "18eb0b9ee680cab4583a14d64cf373c3");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "69a52406a5f835afd3f01d38b8c4e6ef");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "f0ada0ccb0a7ae92f2cadad3dd0593d7");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(30000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "d2d83b48ffae7b4577ff4b99abb6f7e6");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "90345a5ab207305e56c7b03d673abd34");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "94406945622849f3215572e2e0dbc222");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(100000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "d9a9bce5e809651744a98d8fd0908019");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "f47b6b10044041dcf43c805790a3f7b2");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "b0ba7317a92c8ab4eaf0d9233ad37ecb");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(300000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "94f217e231e0cb3ce29c94d1449090a4");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "ff3d29f89e410d87ffd13840196886f6");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "0055c7a18a24406897cf39d4f0769249");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(1000000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "3531c27fdd43884a883d42dedc737be4");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "de5bd07e97bbabbb0092e4a3eb619b20");

        session = session.withChunkNumber(1000000);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "29dd85694f7ae1f0b9718d21c1e9fa9b");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15).withParallelism(150).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "8f4cba435ce81902757e7ab198cbd66e");

        session = session.withChunkNumber(10);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "2ced7f5a3093797aa6977d31bc1b76ce");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(STORE_SALES, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), STORE_SALES, session, "d535f06ce741f45b4dd9fceb70d6ccf4");
    }
}
