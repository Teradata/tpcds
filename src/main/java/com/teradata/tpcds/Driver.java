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

import io.airlift.airline.Command;
import io.airlift.airline.HelpOption;

import javax.inject.Inject;

import static io.airlift.airline.SingleCommand.singleCommand;

@Command(name = "dsdgen", description = "data generator for TPC-DS")
public class Driver
{
    @Inject
    public HelpOption helpOption;

    @Inject
    public Options options = new Options();

    public static void main(String[] args)
    {
        Driver driver = singleCommand(Driver.class).parse(args);
        driver.run();
    }

    protected void run()
    {
        if (helpOption.showHelpIfRequested()) {
            return;
        }

        Session session = options.toSession();

        TableGenerator tableGenerator = new TableGenerator(session);
        Table[] tablesToGenerate;

        if (session.hasTable()) {
            tablesToGenerate = new Table[]{session.getTable()};
        }
        else {
            tablesToGenerate = Table.values();
        }

        Scaling scaling = session.getScaling();
        for (Table table : tablesToGenerate) {
            tableGenerator.generateTable(table, 1, scaling.getRowCount(table));
        }
    }
}
