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

import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.google.common.io.BaseEncoding.base16;
import static com.teradata.tpcds.Results.constructResults;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.testng.Assert.assertEquals;

// This class was derived from the GeneratorAssertions class in the following repo
// https://github.com/airlift/tpch. The license for that class can be found here
// https://www.apache.org/licenses/LICENSE-2.0.
public final class GeneratorAssertions
{
    private GeneratorAssertions() {}

    private static void assertEntityLinesMD5(Results results, String expectedMD5)
    {
        try {
            DigestOutputStream out = md5OutputStream(ByteStreams.nullOutputStream());
            for (List<String> parentAndChildRows : results) {
                out.write(parentAndChildRows.get(0).getBytes(ISO_8859_1));
            }
            byte[] md5Digest = out.getMessageDigest().digest();
            assertEquals(base16().lowerCase().encode(md5Digest), expectedMD5);
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    private static DigestOutputStream md5OutputStream(OutputStream out)
    {
        try {
            return new DigestOutputStream(out, MessageDigest.getInstance("MD5"));
        }
        catch (NoSuchAlgorithmException e) {
            throw Throwables.propagate(e);
        }
    }

    static void assertPartialMD5(long startingRowNumber, long endingRowNumber, Table table, Session session, String expectedMD5)
    {
        assertEntityLinesMD5(constructResults(table, startingRowNumber, endingRowNumber, session), expectedMD5);
    }
}
