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
        return new ChunkBoundaries(firstRowOfChunk, rowCount);
    }

    public static class ChunkBoundaries
    {
        private final long firstRow;
        private final long rowCount;

        private ChunkBoundaries(long firstRow, long rowCount)
        {
            checkArgument(firstRow >= 0, "firstRow is negative");
            checkArgument(rowCount >= 0, "rowCount is negative");
            this.firstRow = firstRow;
            this.rowCount = rowCount;
        }

        public long getFirstRow()
        {
            return firstRow;
        }

        public long getRowCount()
        {
            return rowCount;
        }
    }
}
