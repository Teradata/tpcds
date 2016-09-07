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
import com.teradata.tpcds.Parallel.ChunkBoundaries;
import com.teradata.tpcds.generator.GeneratorColumn;
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.row.TableRow;
import com.teradata.tpcds.row.generator.RowGenerator;
import com.teradata.tpcds.row.generator.RowGeneratorResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.Parallel.splitWork;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static java.util.Objects.requireNonNull;

public class Results
        implements Iterable<List<List<String>>>
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

    public static Results constructResults(Table table, Session session)
    {
        ChunkBoundaries chunkBoundaries = splitWork(table, session);
        return new Results(table, chunkBoundaries.getFirstRow(), chunkBoundaries.getLastRow(), session);
    }

    public static Results constructResults(Table table, long startingRowNumber, long endingRowNumber, Session session)
    {
        return new Results(table, startingRowNumber, endingRowNumber, session);
    }

    @Override
    public Iterator<List<List<String>>> iterator()
    {
        return new ResultsIterator(table, startingRowNumber, rowCount, session);
    }

    private static class ResultsIterator
            extends AbstractIterator<List<List<String>>>
    {
        private final long endingRowNumber;
        private final Table table;
        private final Session session;
        private long rowNumber;
        private final RowGenerator rowGenerator;
        private final RowGenerator parentRowGenerator;
        private final RowGenerator childRowGenerator;

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
            try {
                this.rowGenerator = table.getRowGeneratorClass().getDeclaredConstructor().newInstance();
                this.parentRowGenerator = table.isChild() ? table.getParent().getRowGeneratorClass().getDeclaredConstructor().newInstance() : null;
                this.childRowGenerator = table.hasChild() ? table.getChild().getRowGeneratorClass().getDeclaredConstructor().newInstance() : null;
            }
            catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new TpcdsException(e.toString());
            }
            skipRowsUntilStartingRowNumber(table, startingRowNumber);
        }

        private void skipRowsUntilStartingRowNumber(Table table, long startingRowNumber)
        {
            for (GeneratorColumn column : table.getGeneratorColumns()) {
                column.getRandomNumberStream().skipRows((int) startingRowNumber - 1);  // casting long to int copies C code
            }

            if (table.isChild()) {
                skipRowsUntilStartingRowNumber(table.getParent(), startingRowNumber);
            }
        }

        @Override
        protected List<List<String>> computeNext()
        {
            if (rowNumber > endingRowNumber) {
                resetColumnsAndRowGenerator(table);
                return endOfData();
            }

            RowGeneratorResult result = rowGenerator.generateRowAndChildRows(rowNumber, session, parentRowGenerator, childRowGenerator);
            List<List<String>> tableRows = result.getRowAndChildRows().stream().map(TableRow::getValues).collect(Collectors.toList());

            if (result.shouldEndRow()) {
                rowStop(table);
                rowNumber++;
            }

            if (result.getRowAndChildRows().isEmpty()) {
                tableRows = computeNext();
            }

            return tableRows;
        }

        private void resetColumnsAndRowGenerator(Table table)
        {
            for (GeneratorColumn column : table.getGeneratorColumns()) {
                column.getRandomNumberStream().resetSeed();
            }
            rowGenerator.reset();

            if (table.isChild()) {
                resetColumnsAndRowGenerator(table.getParent());
            }
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
            for (GeneratorColumn column : table.getGeneratorColumns()) {
                RandomNumberStream randomNumberStream = column.getRandomNumberStream();
                while (randomNumberStream.getSeedsUsed() < randomNumberStream.getSeedsPerRow()) {
                    generateUniformRandomInt(1, 100, randomNumberStream);
                }
                randomNumberStream.resetSeedsUsed();
            }
        }
    }
}
