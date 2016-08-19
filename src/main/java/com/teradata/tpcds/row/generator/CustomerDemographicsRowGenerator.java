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
import com.teradata.tpcds.row.CustomerDemographicsRow;

import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.column.CustomerDemographicsGeneratorColumn.CD_NULLS;
import static com.teradata.tpcds.distribution.DemographicsDistributions.CREDIT_RATING_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.EDUCATION_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.GENDER_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.MARITAL_STATUS_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.PURCHASE_BAND_DISTRIBUTION;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getCreditRatingForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getEducationForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getGenderForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getMaritalStatusForIndexModSize;
import static com.teradata.tpcds.distribution.DemographicsDistributions.getPurchaseBandForIndexModSize;

public class CustomerDemographicsRowGenerator
        implements RowGenerator
{
    private static final int MAX_CHILDREN = 7;
    private static final int MAX_EMPLOYED = 7;
    private static final int MAX_COLLEGE = 7;

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(CD_NULLS);
        long cDemoSk = rowNumber;
        long index = cDemoSk - 1;

        String cdGender = getGenderForIndexModSize(index);
        index = index / GENDER_DISTRIBUTION.getSize();
        String cdMaritalStatus = getMaritalStatusForIndexModSize(index);

        index = index / MARITAL_STATUS_DISTRIBUTION.getSize();
        String cdEducationStatus = getEducationForIndexModSize(index);

        index = index / EDUCATION_DISTRIBUTION.getSize();
        int cdPurchaseEstimate = getPurchaseBandForIndexModSize(index);

        index = index / PURCHASE_BAND_DISTRIBUTION.getSize();
        String cdCreditRating = getCreditRatingForIndexModSize(index);

        index = index / CREDIT_RATING_DISTRIBUTION.getSize();
        int cdDepCount = (int) (index % (long) MAX_CHILDREN);

        index /= (long) MAX_CHILDREN;
        int cdEmployedCount = (int) (index % (long) MAX_EMPLOYED);

        index /= (long) MAX_EMPLOYED;
        int cdDepCollegeCount = (int) (index % (long) MAX_COLLEGE);

        return new RowGeneratorResult(new CustomerDemographicsRow(nullBitMap,
                cDemoSk,
                cdGender,
                cdMaritalStatus,
                cdEducationStatus,
                cdPurchaseEstimate,
                cdCreditRating,
                cdDepCount,
                cdEmployedCount,
                cdDepCollegeCount));
    }

    @Override
    public void reset() {}
}
