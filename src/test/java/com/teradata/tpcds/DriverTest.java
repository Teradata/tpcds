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

import org.testng.annotations.Test;

import static com.teradata.tpcds.Table.CALL_CENTER;
import static io.airlift.airline.SingleCommand.singleCommand;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.FileAssert.fail;

public class DriverTest
{
    @Test
    public void testParsing()
    {
        Driver driver = singleCommand(Driver.class).parse("--scale", "10", "--suffix", "abcd");
        assertEquals(driver.options.scale, 10.0f);
        assertEquals(driver.options.suffix, "abcd");
        assertEquals(driver.options.directory, ".");
        Session session = driver.options.toSession();
        assertFalse(session.generateOnlyOneTable());
    }

    @Test
    public void testParsingTableName()
    {
        Driver driver = singleCommand(Driver.class).parse("--table", "call_center");
        assertEquals(driver.options.table, "call_center");
        Session session = driver.options.toSession();
        assertTrue(session.generateOnlyOneTable());
        assertEquals(session.getOnlyTableToGenerate(), CALL_CENTER);
    }

    @Test
    public void testInvalidTable()
    {
        Driver driver = singleCommand(Driver.class).parse("--table", "bad_table_name");
        assertEquals(driver.options.table, "bad_table_name");
        try {
            driver.options.toSession();
            fail("expected exception");
        }
        catch (InvalidOptionException e) {
           assertEquals(e.getMessage(), "Invalid value for table: 'bad_table_name'. ");
        }
    }

    @Test
    public void testBadDirectory()
    {
        Driver driver = singleCommand(Driver.class).parse("--directory", "");
        assertEquals(driver.options.table, null);
        try {
            driver.options.toSession();
            fail("expected exception");
        }
        catch (InvalidOptionException e) {
            assertEquals(e.getMessage(), "Invalid value for directory: ''. Directory cannot be an empty string");
        }
    }

    @Test
    public void testBadSuffix()
    {
        Driver driver = singleCommand(Driver.class).parse("--suffix", "");
        assertEquals(driver.options.table, null);
        try {
            driver.options.toSession();
            fail("expected exception");
        }
        catch (InvalidOptionException e) {
            assertEquals(e.getMessage(), "Invalid value for suffix: ''. Suffix cannot be an empty string");
        }
    }

    @Test
    public void testInvalidScale()
    {
        Driver driver = singleCommand(Driver.class).parse("--scale", "-1");
        try {
            driver.options.toSession();
            fail("expected exception");
        }
        catch (InvalidOptionException e) {
            assertEquals(e.getMessage(), "Invalid value for scale: '-1.0'. Scale must be greater than 0 and less than 100000");
        }
    }

    @Test
    public void testDecimalScale()
    {
        Driver driver = singleCommand(Driver.class).parse("--scale", "0.1");
        assertEquals(driver.options.scale, 0.1f);
    }
}
