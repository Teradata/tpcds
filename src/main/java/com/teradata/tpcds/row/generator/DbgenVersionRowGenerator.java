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
package com.teradata.tpcds.row.generator;

import com.teradata.tpcds.Session;
import com.teradata.tpcds.Table;
import com.teradata.tpcds.row.DbgenVersionRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DbgenVersionRowGenerator
        extends AbstractRowGenerator
{
    private static final String DBGEN_VERSION = "2.0.0";

    public DbgenVersionRowGenerator()
    {
        super(Table.DBGEN_VERSION);
    }

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session, RowGenerator parentRowGenerator, RowGenerator childRowGenerator)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        DbgenVersionRow row = new DbgenVersionRow(
                0,
                DBGEN_VERSION,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                session.getCommandLineArguments());
        return new RowGeneratorResult(row);
    }
}
