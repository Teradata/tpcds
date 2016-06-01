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

    public Session(int scale, String targetDirectory, String suffix, Optional<Table> table, String nullString, char separator, boolean terminate, boolean noSexism)
    {
        this.separator = separator;
        this.terminate = terminate;
        this.scaling = new Scaling(scale);
        this.targetDirectory = targetDirectory;
        this.suffix = suffix;
        this.table = table;
        this.nullString = nullString;
        this.noSexism = noSexism;
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
}
