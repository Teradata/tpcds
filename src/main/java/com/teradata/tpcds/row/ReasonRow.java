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

package com.teradata.tpcds.row;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.teradata.tpcds.column.ReasonColumn.R_REASON_DESCRIPTION;
import static com.teradata.tpcds.column.ReasonColumn.R_REASON_ID;
import static com.teradata.tpcds.column.ReasonColumn.R_REASON_SK;

public class ReasonRow
        extends TableRowWithNulls
{
    private final long rReasonSk;
    private final String rReasonId;
    private final String rReasonDescription;

    public ReasonRow(long nullBitMap, long rReasonSk, String rReasonId, String rReasonDescription)
    {
        super(nullBitMap, R_REASON_SK);
        this.rReasonSk = rReasonSk;
        this.rReasonId = rReasonId;
        this.rReasonDescription = rReasonDescription;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(rReasonSk, R_REASON_SK),
                getStringOrNull(rReasonId, R_REASON_ID),
                getStringOrNull(rReasonDescription, R_REASON_DESCRIPTION));
    }
}
