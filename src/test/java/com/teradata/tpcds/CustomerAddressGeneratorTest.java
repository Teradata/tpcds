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
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;

public class CustomerAddressGeneratorTest
{
    private static final Session TEST_SESSION = getDefaultSession().withTable(CUSTOMER_ADDRESS);

    // See the comment in CallCenterGeneratorTest for an explanation on the purpose of this test.
    @Test
    public void testScaleFactor0_01()
    {
        Session session = TEST_SESSION.withScale(0.01);
        assertPartialMD5(1, session.getScaling().getRowCount(CUSTOMER_ADDRESS), CUSTOMER_ADDRESS, session, "187c61cf295f00170bac17408c322dd9");
    }

    @Test
    public void testScaleFactor1()
    {
        assertPartialMD5(1, TEST_SESSION.getScaling().getRowCount(CUSTOMER_ADDRESS), CUSTOMER_ADDRESS, TEST_SESSION, "abac2e3925ab9bf66cec3b527a0468ed");
    }

    @Test
    public void testScaleFactor10()
    {
        Session session = TEST_SESSION.withScale(10);
        assertPartialMD5(1, session.getScaling().getRowCount(CUSTOMER_ADDRESS), CUSTOMER_ADDRESS, session, "860602fea368111009ef08b167e1e299");
    }

    @Test
    public void testScaleFactor100()
    {
        Session session = TEST_SESSION.withScale(100).withParallelism(100).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "3b94a24f194bd74cb4f42a1763613d1b");

        session = session.withChunkNumber(50);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "0e8232abb70606dd73fe9dd5be08ba9e");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "a098b9cde25e80519beae93c02839c07");
    }

    @Test
    public void testScaleFactor300()
    {
        Session session = TEST_SESSION.withScale(300).withParallelism(300).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "632e9869623e0d66d1668b477fcdfea5");

        session = session.withChunkNumber(50);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "d49a29fbe711808024df58cbb6f1509e");

        session = session.withChunkNumber(100);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "b9bc108cddd60451f0ace0962cc19ae0");
    }

    @Test
    public void testScaleFactor1000()
    {
        Session session = TEST_SESSION.withScale(1000).withParallelism(1000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "ab78f9d5d47efad43035b7731215595b");

        session = session.withChunkNumber(500);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "d4ea75d723c640dfeb07483343438e6d");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "f312f6e32362152213034a8446d1efca");
    }

    @Test
    public void testScaleFactor3000()
    {
        Session session = TEST_SESSION.withScale(3000).withParallelism(3000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "3cf2c6975495a8e6992539c47b00635d");

        session = session.withChunkNumber(500);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "94b8fe7053316f7491bc6096e1172b63");

        session = session.withChunkNumber(1000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "642b5952322240fffea6fd0be1923e31");
    }

    @Test
    public void testScaleFactor10000()
    {
        Session session = TEST_SESSION.withScale(10000).withParallelism(10000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "839453ec9fc1f6b1fcf5db9c718f7c52");

        session = session.withChunkNumber(5000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "32a6a9e1e2a049aaeb53a0e455bdf478");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "92952ecdaeece71eb5f8f0395091a094");
    }

    @Test
    public void testScaleFactor30000()
    {
        Session session = TEST_SESSION.withScale(30000).withParallelism(30000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "6407a51661a4397839727ab56fbcfc71");

        session = session.withChunkNumber(5000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "198aa88d758feb2b751b9295d58f1bcb");

        session = session.withChunkNumber(10000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "5e65ca9f573ca645898da26e7201ad0a");
    }

    @Test
    public void testScaleFactor100000()
    {
        Session session = TEST_SESSION.withScale(100000).withParallelism(100000).withChunkNumber(1);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "a28092bc1844b2b5c1798c3f6ca01ecb");

        session = session.withChunkNumber(50000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "b9799ff0f3b02f0900e9c108dc5e1c0b");

        session = session.withChunkNumber(100000);
        chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "2290ae8327742881aa06234a888aca16");
    }

    @Test
    public void testUndefinedScale()
    {
        Session session = TEST_SESSION.withScale(15);
        ChunkBoundaries chunkBoundaries = splitWork(CUSTOMER_ADDRESS, session);
        assertPartialMD5(chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), CUSTOMER_ADDRESS, session, "5c057e723d1f0a0687a2a4221e4e3267");
    }
}
