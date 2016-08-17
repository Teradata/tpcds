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

import com.teradata.tpcds.row.IncomeBandRow;

import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.column.IncomeBandColumn.IB_NULLS;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getIncomeBandLowerBoundAtIndex;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getIncomeBandUpperBoundAtIndex;

public class IncomeBandRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(IB_NULLS);
        int ibIncomeBandId = (int) rowNumber;
        int ibLowerBound = getIncomeBandLowerBoundAtIndex((int) rowNumber - 1);
        int ibUpperBound = getIncomeBandUpperBoundAtIndex((int) rowNumber - 1);
        return new RowGeneratorResult(new IncomeBandRow(nullBitMap, ibIncomeBandId, ibLowerBound, ibUpperBound));
    }

    @Override
    public void reset() {}
}
