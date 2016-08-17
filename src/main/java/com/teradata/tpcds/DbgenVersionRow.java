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

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.DbgenVersionColumn.DV_CMDLINE_ARGS;
import static com.teradata.tpcds.column.DbgenVersionColumn.DV_CREATE_DATE;
import static com.teradata.tpcds.column.DbgenVersionColumn.DV_CREATE_TIME;
import static com.teradata.tpcds.column.DbgenVersionColumn.DV_VERSION;

public class DbgenVersionRow
        extends TableRowWithNulls
{
    private final String dvVersion;
    private final String dvCreateDate;
    private final String dvCreateTime;
    private final String dvCmdlineArgs;

    protected DbgenVersionRow(long nullBitMap, String dvVersion, String dvCreateDate, String dvCreateTime, String dvCmdlineArgs)
    {
        super(nullBitMap, DV_VERSION);
        this.dvVersion = dvVersion;
        this.dvCreateDate = dvCreateDate;
        this.dvCreateTime = dvCreateTime;
        this.dvCmdlineArgs = dvCmdlineArgs;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNull(dvVersion, DV_VERSION),
                getStringOrNull(dvCreateDate, DV_CREATE_DATE),
                getStringOrNull(dvCreateTime, DV_CREATE_TIME),
                getStringOrNull(dvCmdlineArgs, DV_CMDLINE_ARGS));
    }
}
