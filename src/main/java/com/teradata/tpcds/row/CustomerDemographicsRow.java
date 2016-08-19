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
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_CREDIT_RATING;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_DEMO_SK;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_DEP_COLLEGE_COUNT;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_DEP_COUNT;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_DEP_EMPLOYED_COUNT;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_EDUCATION_STATUS;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_GENDER;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_MARITAL_STATUS;
import static com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn.CD_PURCHASE_ESTIMATE;

public class CustomerDemographicsRow
        extends TableRowWithNulls
{
    private final long cdDemoSk;
    private final String cdGender;
    private final String cdMaritalStatus;
    private final String cdEducationStatus;
    private final int cdPurchaseEstimate;
    private final String cdCreditRating;
    private final int cdDepCount;
    private final int cdDepEmployedCount;
    private final int cdDepCollegeCount;

    public CustomerDemographicsRow(long nullBitMap,
            long cdDemoSk,
            String cdGender,
            String cdMaritalStatus,
            String cdEducationStatus,
            int cdPurchaseEstimate,
            String cdCreditRating,
            int cdDepCount,
            int cdDepEmployedCount,
            int cdDepCollegeCount)
    {
        super(nullBitMap, CD_DEMO_SK);
        this.cdDemoSk = cdDemoSk;
        this.cdGender = cdGender;
        this.cdMaritalStatus = cdMaritalStatus;
        this.cdEducationStatus = cdEducationStatus;
        this.cdPurchaseEstimate = cdPurchaseEstimate;
        this.cdCreditRating = cdCreditRating;
        this.cdDepCount = cdDepCount;
        this.cdDepEmployedCount = cdDepEmployedCount;
        this.cdDepCollegeCount = cdDepCollegeCount;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(cdDemoSk, CD_DEMO_SK),
                getStringOrNull(cdGender, CD_GENDER),
                getStringOrNull(cdMaritalStatus, CD_MARITAL_STATUS),
                getStringOrNull(cdEducationStatus, CD_EDUCATION_STATUS),
                getStringOrNull(cdPurchaseEstimate, CD_PURCHASE_ESTIMATE),
                getStringOrNull(cdCreditRating, CD_CREDIT_RATING),
                getStringOrNull(cdDepCount, CD_DEP_COUNT),
                getStringOrNull(cdDepEmployedCount, CD_DEP_EMPLOYED_COUNT),
                getStringOrNull(cdDepCollegeCount, CD_DEP_COLLEGE_COUNT)
        );
    }
}
