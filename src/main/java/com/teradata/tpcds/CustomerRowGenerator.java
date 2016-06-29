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

import com.teradata.tpcds.type.Date;

import static com.teradata.tpcds.BusinessKeyGenerator.makeBusinessKey;
import static com.teradata.tpcds.CustomerColumn.C_BIRTH_COUNTRY;
import static com.teradata.tpcds.CustomerColumn.C_BIRTH_DAY;
import static com.teradata.tpcds.CustomerColumn.C_CURRENT_ADDR_SK;
import static com.teradata.tpcds.CustomerColumn.C_CURRENT_CDEMO_SK;
import static com.teradata.tpcds.CustomerColumn.C_CURRENT_HDEMO_SK;
import static com.teradata.tpcds.CustomerColumn.C_EMAIL_ADDRESS;
import static com.teradata.tpcds.CustomerColumn.C_FIRST_NAME;
import static com.teradata.tpcds.CustomerColumn.C_FIRST_SALES_DATE_ID;
import static com.teradata.tpcds.CustomerColumn.C_LAST_NAME;
import static com.teradata.tpcds.CustomerColumn.C_LAST_REVIEW_DATE;
import static com.teradata.tpcds.CustomerColumn.C_NULLS;
import static com.teradata.tpcds.CustomerColumn.C_PREFERRED_CUST_FLAG;
import static com.teradata.tpcds.CustomerColumn.C_SALUTATION;
import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.Table.CUSTOMER_ADDRESS;
import static com.teradata.tpcds.Table.CUSTOMER_DEMOGRAPHICS;
import static com.teradata.tpcds.Table.HOUSEHOLD_DEMOGRAPHICS;
import static com.teradata.tpcds.distribution.AddressDistributions.pickRandomCountry;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.FEMALE_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.GENERAL_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.SalutationsWeights.FEMALE;
import static com.teradata.tpcds.distribution.NamesDistributions.SalutationsWeights.MALE;
import static com.teradata.tpcds.distribution.NamesDistributions.getFirstNameFromIndex;
import static com.teradata.tpcds.distribution.NamesDistributions.getWeightForIndex;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomIndex;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomLastName;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomSalutation;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomEmail;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomDate;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Date.JULIAN_TODAYS_DATE;
import static com.teradata.tpcds.type.Date.fromJulianDays;
import static com.teradata.tpcds.type.Date.toJulianDays;

public class CustomerRowGenerator
        implements RowGenerator
{
    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long cCustomerSk = rowNumber;
        String cCustomerId = makeBusinessKey(rowNumber);
        int randomInt = generateUniformRandomInt(1, 100, C_PREFERRED_CUST_FLAG.getRandomNumberStream());
        int cPreferredPercent = 50;
        boolean cPreferredCustFlag = randomInt < cPreferredPercent;

        Scaling scaling = session.getScaling();
        long cCurrentHdemoSk = generateJoinKey(C_CURRENT_HDEMO_SK, HOUSEHOLD_DEMOGRAPHICS, 1, scaling);
        long cCurrentCdemoSk = generateJoinKey(C_CURRENT_CDEMO_SK, CUSTOMER_DEMOGRAPHICS, 1, scaling);
        long cCurrentAddrSk = generateJoinKey(C_CURRENT_ADDR_SK, CUSTOMER_ADDRESS, cCustomerSk, scaling);

        int nameIndex = pickRandomIndex(GENERAL_FREQUENCY, C_FIRST_NAME.getRandomNumberStream());
        String cFirstName = getFirstNameFromIndex(nameIndex);
        String cLastName = pickRandomLastName(C_LAST_NAME.getRandomNumberStream());
        int femaleNameWeight = getWeightForIndex(nameIndex, FEMALE_FREQUENCY);
        String cSalutation = pickRandomSalutation(femaleNameWeight == 0 ? MALE : FEMALE, C_SALUTATION.getRandomNumberStream());

        Date maxBirthday = new Date(1992, 12, 31);
        Date minBirthday = new Date(1924, 1, 1);
        Date oneYearAgo = fromJulianDays(JULIAN_TODAYS_DATE - 365);
        Date tenYearsAgo = fromJulianDays(JULIAN_TODAYS_DATE - 3650);
        Date today = fromJulianDays(JULIAN_TODAYS_DATE);
        Date birthday = generateUniformRandomDate(minBirthday, maxBirthday, C_BIRTH_DAY.getRandomNumberStream());
        int cBirthDay = birthday.getDay();
        int cBirthMonth = birthday.getMonth();
        int cBirthYear = birthday.getYear();

        String cEmailAddress = generateRandomEmail(cFirstName, cLastName, C_EMAIL_ADDRESS.getRandomNumberStream());
        Date lastReviewDate = generateUniformRandomDate(oneYearAgo, today, C_LAST_REVIEW_DATE.getRandomNumberStream());
        int cLastReviewDate = toJulianDays(lastReviewDate);
        Date firstSalesDate = generateUniformRandomDate(tenYearsAgo, today, C_FIRST_SALES_DATE_ID.getRandomNumberStream());
        int cFirstSalesDateId = toJulianDays(firstSalesDate);
        int cFirstShiptoDateId = cFirstSalesDateId + 30;

        String cBirthCountry = pickRandomCountry(C_BIRTH_COUNTRY.getRandomNumberStream());

        return new RowGeneratorResult(new CustomerRow(cCustomerSk,
                cCustomerId,
                cCurrentCdemoSk,
                cCurrentHdemoSk,
                cCurrentAddrSk,
                cFirstShiptoDateId,
                cFirstSalesDateId,
                cSalutation,
                cFirstName,
                cLastName,
                cPreferredCustFlag,
                cBirthDay,
                cBirthMonth,
                cBirthYear,
                cBirthCountry,
                cEmailAddress,
                cLastReviewDate,
                createNullBitMap(C_NULLS)));
    }

    @Override
    public void reset() {}
}
