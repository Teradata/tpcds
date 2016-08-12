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

import java.util.Optional;

public class Session
{
    private final Scaling scaling;
    private final String targetDirectory;
    private final String suffix;
    private final Optional<Table> table;
    private final String nullString;
    private final char separator;
    private final boolean terminate;
    private final boolean noSexism;
    private final int parallelism;
    private final int chunkNumber;
    private final boolean overwrite;

    public Session(int scale, String targetDirectory, String suffix, Optional<Table> table, String nullString, char separator, boolean terminate, boolean noSexism, int parallelism, boolean overwrite)
    {
        this(scale, targetDirectory, suffix, table, nullString, separator, terminate, noSexism, parallelism, 1, overwrite);
    }

    public Session(int scale, String targetDirectory, String suffix, Optional<Table> table, String nullString, char separator, boolean terminate, boolean noSexism, int parallelism, int chunkNumber, boolean overwrite)
    {
        this.scaling = new Scaling(scale);
        this.targetDirectory = targetDirectory;
        this.suffix = suffix;
        this.table = table;
        this.nullString = nullString;
        this.separator = separator;
        this.terminate = terminate;
        this.noSexism = noSexism;
        this.parallelism = parallelism;
        this.chunkNumber = chunkNumber;
        this.overwrite = overwrite;
    }

    public static Session getDefaultSession()
    {
        return new Options().toSession();
    }

    public Session withTable(Table table)
    {
        return new Session(
                this.scaling.getScale(),
                this.targetDirectory,
                this.suffix,
                Optional.of(table),
                this.nullString,
                this.separator,
                this.terminate,
                this.noSexism,
                this.parallelism,
                this.chunkNumber,
                this.overwrite
        );
    }

    public Session withScale(int scale)
    {
        return new Session(
                scale,
                this.targetDirectory,
                this.suffix,
                this.table,
                this.nullString,
                this.separator,
                this.terminate,
                this.noSexism,
                this.parallelism,
                this.chunkNumber,
                this.overwrite
        );
    }

    public Session withParallelism(int parallelism)
    {
        return new Session(
                this.scaling.getScale(),
                this.targetDirectory,
                this.suffix,
                this.table,
                this.nullString,
                this.separator,
                this.terminate,
                this.noSexism,
                parallelism,
                this.chunkNumber,
                this.overwrite
        );
    }

    public Session withChunkNumber(int chunkNumber)
    {
        return new Session(
                this.scaling.getScale(),
                this.targetDirectory,
                this.suffix,
                this.table,
                this.nullString,
                this.separator,
                this.terminate,
                this.noSexism,
                this.parallelism,
                chunkNumber,
                this.overwrite
        );
    }

    public Scaling getScaling()
    {
        return scaling;
    }

    public String getTargetDirectory()
    {
        return targetDirectory;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public boolean hasTable()
    {
        return table.isPresent();
    }

    public Table getTable()
    {
        if (!table.isPresent()) {
            throw new TpcdsException("table not present");
        }
        return table.get();
    }

    public String getNullString()
    {
        return nullString;
    }

    public char getSeparator()
    {
        return separator;
    }

    public boolean terminateRowsWithSeparator()
    {
        return terminate;
    }

    public boolean isSexist()
    {
        return !noSexism;
    }

    public int getParallelism()
    {
        return parallelism;
    }

    public int getChunkNumber()
    {
        return chunkNumber;
    }

    public boolean shouldOverwrite()
    {
        return overwrite;
    }
}
