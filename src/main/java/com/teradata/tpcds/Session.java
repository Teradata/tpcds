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

import static com.teradata.tpcds.Options.DEFAULT_DIRECTORY;
import static com.teradata.tpcds.Options.DEFAULT_DO_NOT_TERMINATE;
import static com.teradata.tpcds.Options.DEFAULT_NO_SEXISM;
import static com.teradata.tpcds.Options.DEFAULT_NULL_STRING;
import static com.teradata.tpcds.Options.DEFAULT_OVERWRITE;
import static com.teradata.tpcds.Options.DEFAULT_PARALLELISM;
import static com.teradata.tpcds.Options.DEFAULT_SCALE;
import static com.teradata.tpcds.Options.DEFAULT_SEPARATOR;
import static com.teradata.tpcds.Options.DEFAULT_SUFFIX;

public class Session
{
    private final Scaling scaling;
    private final String targetDirectory;
    private final String suffix;
    private final Optional<Table> table;
    private final String nullString;
    private final char separator;
    private final boolean doNotTerminate;
    private final boolean noSexism;
    private final int parallelism;
    private final int chunkNumber;
    private final boolean overwrite;

    public Session(int scale, String targetDirectory, String suffix, Optional<Table> table, String nullString, char separator, boolean doNotTerminate, boolean noSexism, int parallelism, boolean overwrite)
    {
        this(scale, targetDirectory, suffix, table, nullString, separator, doNotTerminate, noSexism, parallelism, 1, overwrite);
    }

    public Session(int scale, String targetDirectory, String suffix, Optional<Table> table, String nullString, char separator, boolean doNotTerminate, boolean noSexism, int parallelism, int chunkNumber, boolean overwrite)
    {
        this.scaling = new Scaling(scale);
        this.targetDirectory = targetDirectory;
        this.suffix = suffix;
        this.table = table;
        this.nullString = nullString;
        this.separator = separator;
        this.doNotTerminate = doNotTerminate;
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
                this.doNotTerminate,
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
                this.doNotTerminate,
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
                this.doNotTerminate,
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
                this.doNotTerminate,
                this.noSexism,
                this.parallelism,
                chunkNumber,
                this.overwrite
        );
    }

    public Session withNoSexism(boolean noSexism)
    {
        return new Session(
                this.scaling.getScale(),
                this.targetDirectory,
                this.suffix,
                this.table,
                this.nullString,
                this.separator,
                this.doNotTerminate,
                noSexism,
                this.parallelism,
                this.chunkNumber,
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

    public boolean generateOnlyOneTable()
    {
        return table.isPresent();
    }

    public Table getOnlyTableToGenerate()
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
        return !doNotTerminate;
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

    public String getCommandLineArguments()
    {
        StringBuilder output = new StringBuilder();
        if (scaling.getScale() != DEFAULT_SCALE) {
            output.append("--scale ").append(scaling.getScale()).append(" ");
        }
        if (!targetDirectory.equals(DEFAULT_DIRECTORY)) {
            output.append("--directory ").append(targetDirectory).append(" ");
        }
        if (!suffix.equals(DEFAULT_SUFFIX)) {
            output.append("--suffix ").append(suffix).append(" ");
        }
        if (table.isPresent()) {
            output.append("--table ").append(table.get().getName()).append(" ");
        }
        if (!nullString.equals(DEFAULT_NULL_STRING)) {
            output.append("--null ").append(nullString).append(" ");
        }
        if (separator != DEFAULT_SEPARATOR) {
            output.append("--separator ").append(separator).append(" ");
        }
        if (doNotTerminate != DEFAULT_DO_NOT_TERMINATE) {
            output.append("--do-not-terminate ");
        }
        if (noSexism != DEFAULT_NO_SEXISM) {
            output.append("--no-sexism ");
        }
        if (parallelism != DEFAULT_PARALLELISM) {
            output.append("--parallelism ").append(parallelism).append(" ");
        }
        if (overwrite != DEFAULT_OVERWRITE) {
            output.append("--overwrite ");
        }

        // remove trailing space
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }
}
