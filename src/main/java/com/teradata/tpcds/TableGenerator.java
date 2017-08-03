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

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.teradata.tpcds.Results.constructResults;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class TableGenerator
{
    private final Session session;

    public TableGenerator(Session session)
    {
        this.session = requireNonNull(session, "session is null");
    }

    public void generateTable(Table table)
    {
        // If this is a child table and not the only table being generated, it will be generated when its parent is generated, so move on.
        if (table.isChild() && !session.generateOnlyOneTable()) {
            return;
        }

        try (OutputStreamWriter parentWriter = addFileWriterForTable(table);
                OutputStreamWriter childWriter = table.hasChild() && !session.generateOnlyOneTable() ? addFileWriterForTable(table.getChild()) : null) {
            Results results = constructResults(table, session);
            for (List<List<String>> parentAndChildRows : results) {
                if (parentAndChildRows.size() > 0) {
                    writeResults(parentWriter, parentAndChildRows.get(0));
                }
                if (parentAndChildRows.size() > 1) {
                    requireNonNull(childWriter, "childWriter is null, but a child row was produced");
                    writeResults(childWriter, parentAndChildRows.get(1));
                }
            }
        }
        catch (IOException e) {
            throw new TpcdsException(e.getMessage());
        }
    }

    private OutputStreamWriter addFileWriterForTable(Table table)
            throws IOException
    {
        String path = getPath(table);
        if (session.getHadoopConfig() == null) {
            File file = new File(path);
            boolean newFileCreated = file.createNewFile();
            if (!newFileCreated) {
                if (session.shouldOverwrite()) {
                    // truncate the file
                    new FileOutputStream(path).close();
                }
                else {
                    throw new TpcdsException(format("File %s exists.  Remove it or run with the '--overwrite' option", path));
                }
            }
            return new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.ISO_8859_1);
        }
        try {
            FileSystem fs = FileSystem.get(new URI(session.getTargetDirectory()), session.getHadoopConfig());

            Path hadoopPath = new Path(path);
            if (fs.exists(hadoopPath)) {
                if (session.shouldOverwrite()) {
                    fs.delete(hadoopPath, true);
                }
                else {
                    throw new TpcdsException(format("File %s exists.  Remove it or run with the '--overwrite' option", path));
                }
            }
            return new OutputStreamWriter(fs.create(hadoopPath, true, 4096), StandardCharsets.ISO_8859_1);
        }
        catch (final URISyntaxException uriEx) {
            throw new TpcdsException("Could not create URI for connecting to Hadoop:" + uriEx.getMessage());
        }
    }

    private String getPath(Table table)
    {
        if (session.getParallelism() > 1) {
            return format("%s%s%s_%d_%d%s",
                    session.getTargetDirectory(),
                    File.separator,
                    table.getName(),
                    session.getChunkNumber(),
                    session.getParallelism(),
                    session.getSuffix());
        }

        // TODO: path names for update case
        return format("%s%s%s%s",
                session.getTargetDirectory(),
                File.separator,
                table.getName(),
                session.getSuffix());
    }

    private void writeResults(Writer writer, List<String> values)
            throws IOException
    {
        writer.write(formatRow(values, session));
    }

    public static String formatRow(List<String> values, Session session)
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
}
