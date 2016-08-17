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

import com.google.common.collect.AbstractIterator;
import com.teradata.tpcds.column.Column;
import com.teradata.tpcds.random.RandomNumberStream;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static java.util.Objects.requireNonNull;

public class Results
        implements Iterable<List<String>>
{
    private final Table table;
    private final long startingRowNumber;
    private final long rowCount;
    private final Session session;

    public Results(Table table, long startingRowNumber, long rowCount, Session session)
    {
        this.table = table;
        this.startingRowNumber = startingRowNumber;
        this.rowCount = rowCount;
        this.session = session;
    }

    public static Results constructResults(Table table, long startingRowNumber, long endingRowNumber, Session session)
    {
        return new Results(table, startingRowNumber, endingRowNumber, session);
    }

    @Override
    public Iterator<List<String>> iterator()
    {
        return new ResultsIterator(table, startingRowNumber, rowCount, session);
    }

    public static class ResultsIterator
            extends AbstractIterator<List<String>>
    {
        private final long endingRowNumber;
        private final Table table;
        private final Session session;
        private long rowNumber;

        public ResultsIterator(Table table, long startingRowNumber, long endingRowNumber, Session session)
        {
            requireNonNull(table, "table is null");
            requireNonNull(session, "session is null");
            checkArgument(startingRowNumber >= 1, "starting row number is less than 1: %s", startingRowNumber);
            checkArgument(endingRowNumber <= session.getScaling().getRowCount(table), "starting row number is greater than the total rows in %s: %s", table, endingRowNumber);

            this.table = table;
            this.rowNumber = startingRowNumber;
            this.endingRowNumber = endingRowNumber;
            this.session = session;

            skipRowsUntilStartingRowNumber(table, startingRowNumber);
        }

        private void skipRowsUntilStartingRowNumber(Table table, long startingRowNumber)
        {
            for (Column column : table.getColumns()) {
                column.getRandomNumberStream().skipRows((int) startingRowNumber - 1);  // casting long to int copies C code
            }

            if (table.isChild()) {
                skipRowsUntilStartingRowNumber(table.getParent(), startingRowNumber);
            }
        }

        @Override
        protected List<String> computeNext()
        {
            if (rowNumber > endingRowNumber) {
                resetColumnsAndRowGenerator(table);
                return endOfData();
            }

            RowGeneratorResult result = table.getRowGenerator().generateRowAndChildRows(rowNumber, session);
            List<String> formattedTableRows = result.getRowAndChildRows().stream().map(r -> formatRow(r.getValues())).collect(Collectors.toList());

            if (result.shouldEndRow()) {
                rowStop(table);
                rowNumber++;
            }

            if (result.getRowAndChildRows().isEmpty()) {
                formattedTableRows = computeNext();
            }

            return formattedTableRows;
        }

        private void resetColumnsAndRowGenerator(Table table)
        {
            for (Column column : table.getColumns()) {
                column.getRandomNumberStream().resetSeed();
            }
            table.getRowGenerator().reset();

            if (table.isChild()) {
                resetColumnsAndRowGenerator(table.getParent());
            }
        }

        private String formatRow(List<String> values)
        {
            // replace nulls with the string representation for null
            values = values.stream().map(value -> value != null ? value : session.getNullString()).collect(Collectors.toList());

            StringBuilder stringBuilder = new StringBuilder();
            char separator = session.getSeparator();
            stringBuilder.append(values.get(0));
            for (int i = 1; i < values.size(); i++) {
                stringBuilder.append(separator);
                stringBuilder.append(values.get(i));
            }
            if (session.terminateRowsWithSeparator()) {
                stringBuilder.append(separator);
            }
            stringBuilder.append('\n');
            return stringBuilder.toString();
        }

        private void rowStop(Table table)
        {
            consumeRemainingSeedsForRow(table);
            if (table.hasChild() && !session.generateOnlyOneTable()) {
                rowStop(table.getChild());
            }
            else if (session.generateOnlyOneTable() && table.isChild()) {
                rowStop(table.getParent());
            }
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
    }
}
