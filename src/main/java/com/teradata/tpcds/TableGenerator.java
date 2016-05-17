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

import com.teradata.tpcds.random.RandomNumberStream;

import java.io.FileWriter;
import java.io.IOException;

import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class TableGenerator
{
    private static final char PATH_SEPARATOR = '/';
    private final Session session;

    public TableGenerator(Session session)
    {
        this.session = requireNonNull(session, "session is null");
    }

    public void generateTable(Table table, long startingRowNumber, long rowCount)
    {
        String path = getPath(table);
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            RowGenerator rowGenerator = table.getRowGenerator();
            for (long i = startingRowNumber; i < rowCount; i++) {
                // TODO: apparently not all generated rows should be printed and that depends on some return code
                // I'll wait on that until I see a case that has a non-zero return code.
                fileWriter.write(rowGenerator.generateRow(i, session.getScaling()).toFormattedString());
                rowStop(table);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rowStop(Table table)
    {
        // TODO: handle parent/child tables.
        consumeRemainingSeedsForRow(table);
    }

    private void consumeRemainingSeedsForRow(Table table)
    {
        for (Column column : table.getColumns()) {
            RandomNumberStream randomNumberStream = column.getRandomNumberStream();
            while (randomNumberStream.getSeedsUsed() < randomNumberStream.getSeedsPerRow()) {
                generateUniformRandomInt(1, 100, randomNumberStream);
            }

            randomNumberStream.resetSeedsUsed();
        }
    }

    private String getPath(Table table)
    {
        // TODO: path names for update and parallel cases
        return format("%s%c%s%s",
                      session.getTargetDirectory(),
                      PATH_SEPARATOR,
                      table.toString(),
                      session.getSuffix());
    }
}
