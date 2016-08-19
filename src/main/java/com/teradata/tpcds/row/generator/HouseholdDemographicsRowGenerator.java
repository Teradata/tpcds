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

package com.teradata.tpcds.row.generator;

import com.teradata.tpcds.Session;
import com.teradata.tpcds.row.HouseholdDemographicsRow;

import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.column.generator.HouseholdDemographicsGeneratorColumn.HD_NULLS;
import static com.teradata.tpcds.distribution.DemographicsDistributions.BUY_POTENTIAL_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.DEP_COUNT_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.INCOME_BAND_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getBuyPotentialForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getDepCountForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getVehicleCountForIndexModSize;

public class HouseholdDemographicsRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(HD_NULLS);
        long hdDemoSk = rowNumber;
        long index = hdDemoSk;
        long hdIncomeBandId = (index % INCOME_BAND_DISTRIBUTION.getSize()) + 1;

        index /= INCOME_BAND_DISTRIBUTION.getSize();
        String hdBuyPotential = getBuyPotentialForIndexModSize(index);

        index /= BUY_POTENTIAL_DISTRIBUTION.getSize();
        int hdDepCount = getDepCountForIndexModSize(index);

        index /= DEP_COUNT_DISTRIBUTION.getSize();
        int hdVehicleCount = getVehicleCountForIndexModSize(index);

        return new RowGeneratorResult(new HouseholdDemographicsRow(nullBitMap, hdDemoSk, hdIncomeBandId, hdBuyPotential, hdDepCount, hdVehicleCount));
    }

    @Override
    public void reset() {}
}
