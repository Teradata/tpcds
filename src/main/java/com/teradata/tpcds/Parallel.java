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

import static com.google.common.base.Preconditions.checkArgument;
import static com.teradata.tpcds.type.Date.JULIAN_DATA_START_DATE;

public final class Parallel
{
    private Parallel() {}

    public static ChunkBoundaries splitWork(Table table, Session session)
    {
        // Tables with fewer than 1000000 are not parallelized. Return no rows for chunks > 1
        long totalRows = session.getScaling().getRowCount(table);
        int chunk = session.getChunkNumber();
        if (totalRows < 1000000 && chunk > 1) {
            return new ChunkBoundaries(0, 0);
        }

        int parallelism = session.getParallelism();
        long extraRows = totalRows % parallelism;
        long rowSetSize = totalRows / parallelism;

        long firstRowOfChunk = 1; // row numbering starts at 1
        firstRowOfChunk += (chunk - 1) * rowSetSize;
        if (extraRows != 0 && (chunk - 1) != 0) {
            // add as many extra rows as there have been
            firstRowOfChunk += ((chunk - 1) < extraRows) ? (chunk - 1) : extraRows;
        }

        long rowCount = rowSetSize;
        if (extraRows != 0 && chunk <= extraRows) {
            rowCount += 1;
        }
        return new ChunkBoundaries(firstRowOfChunk, firstRowOfChunk + rowCount - 1);
    }

    public static DateNextIndexPair skipDaysUntilFirstRowOfChunk(Table table, Session session)
    {
        // set initial conditions
        long julianDate = JULIAN_DATA_START_DATE;
        Scaling scaling = session.getScaling();
        int index = 1;
        long newDateIndex = scaling.getRowCountForDate(table, julianDate) + index;

        // move forward one day at a time
        ChunkBoundaries boundary = splitWork(table, session);
        while (index < boundary.getFirstRow()) {
            index += scaling.getRowCountForDate(table, julianDate);
            julianDate += 1;
            newDateIndex = index;
        }
        if (index > boundary.getFirstRow()) {
            julianDate -= 1;
        }

        return new DateNextIndexPair(julianDate, newDateIndex);
    }

    public static class ChunkBoundaries
    {
        private final long firstRow;
        private final long lastRow;

        private ChunkBoundaries(long firstRow, long lastRow)
        {
            checkArgument(firstRow >= 0, "firstRow is negative");
            checkArgument(lastRow >= 0, "lastRow is negative");
            this.firstRow = firstRow;
            this.lastRow = lastRow;
        }

        public long getFirstRow()
        {
            return firstRow;
        }

        public long getLastRow()
        {
            return lastRow;
        }
    }

    public static final class DateNextIndexPair
    {
        private final long julianDate;
        private final long nextDateIndex;

        public DateNextIndexPair(long julianDate, long nextDateIndex)
        {
            this.julianDate = julianDate;
            this.nextDateIndex = nextDateIndex;
        }

        long getJulianDate()
        {
            return julianDate;
        }

        long getNextDateIndex()
        {
            return nextDateIndex;
        }
    }
}
