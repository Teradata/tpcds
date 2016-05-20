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

import com.teradata.tpcds.SlowlyChangingDimensionUtils.SlowlyChangingDimensionKey;
import com.teradata.tpcds.type.Decimal;

import javax.annotation.concurrent.NotThreadSafe;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.CallCenterColumn.CC_ADDRESS;
import static com.teradata.tpcds.CallCenterColumn.CC_CALL_CENTER_ID;
import static com.teradata.tpcds.CallCenterColumn.CC_CLASS;
import static com.teradata.tpcds.CallCenterColumn.CC_COMPANY;
import static com.teradata.tpcds.CallCenterColumn.CC_EMPLOYEES;
import static com.teradata.tpcds.CallCenterColumn.CC_HOURS;
import static com.teradata.tpcds.CallCenterColumn.CC_MANAGER;
import static com.teradata.tpcds.CallCenterColumn.CC_MARKET_CLASS;
import static com.teradata.tpcds.CallCenterColumn.CC_MARKET_DESC;
import static com.teradata.tpcds.CallCenterColumn.CC_MARKET_ID;
import static com.teradata.tpcds.CallCenterColumn.CC_MARKET_MANAGER;
import static com.teradata.tpcds.CallCenterColumn.CC_NULLS;
import static com.teradata.tpcds.CallCenterColumn.CC_SCD;
import static com.teradata.tpcds.CallCenterColumn.CC_SQ_FT;
import static com.teradata.tpcds.CallCenterColumn.CC_TAX_PERCENTAGE;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.computeScdKey;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.getValueForSlowlyChangingDimension;
import static com.teradata.tpcds.Table.CALL_CENTER;
import static com.teradata.tpcds.distribution.CallCenterDistributions.getCallCenterAtIndex;
import static com.teradata.tpcds.distribution.CallCenterDistributions.getNumberOfCallCenters;
import static com.teradata.tpcds.distribution.CallCenterDistributions.pickRandomCallCenterClass;
import static com.teradata.tpcds.distribution.CallCenterDistributions.pickRandomCallCenterHours;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.MALE_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomFirstName;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomLastName;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomText;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomDecimal;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.random.RandomValueGenerator.generateWord;
import static com.teradata.tpcds.type.Address.makeAddressForColumn;
import static com.teradata.tpcds.type.Date.JULIAN_DATA_START_DATE;
import static java.lang.String.format;

@NotThreadSafe
public class CallCenterRowGenerator
        implements RowGenerator
{
    private static final Decimal MIN_TAX_PERCENTAGE = new Decimal(2, 2, 0);  // 0.00
    private static final Decimal MAX_TAX_PERCENTAGE = new Decimal(2, 2, 12); // 0.12
    private static final int WIDTH_CC_DIVISION_NAME = 50;
    private static final int WIDTH_CC_MARKET_CLASS = 50;
    private static final int WIDTH_CC_MARKET_DESC = 100;
    private static final int MAX_NUMBER_OF_EMPLOYEES_UNSCALED = 7;   // rises ~ scale ^ 2
    private static final long JULIAN_DATE_START = JULIAN_DATA_START_DATE - 23;  // 23 is the id of the WEB_SITE table in the c code.  Seems arbitrarily chosen.

    private CallCenterRow previousRow;

    @Override
    public TableRow generateRow(long rowNumber, Scaling scaling)
    {
        CallCenterRow.Builder builder = new CallCenterRow.Builder();
        builder.setNullBitMap(createNullBitMap(CC_NULLS));
        builder.setCcCallCenterSk(rowNumber);

        // The id combined with start and end dates represent the unique key for this row.
        // The id is what would be a primary key if there were only one version of each row
        // the start and end dates are the version information for the row.
        SlowlyChangingDimensionKey slowlyChangingDimensionKey = computeScdKey(CALL_CENTER, rowNumber);
        builder.setCcCallCenterId(slowlyChangingDimensionKey.getBusinessKey());
        builder.setCcRecStartDateId(slowlyChangingDimensionKey.getStartDate());
        builder.setCcRecEndDateId(slowlyChangingDimensionKey.getEndDate());

        // -1 indicates null. This never gets set to anything else.  Copying C code.
        builder.setCcClosedDateId(-1);

        boolean isNewBusinessKey = slowlyChangingDimensionKey.isNewBusinessKey();
        // These fields only change when there is a new id.  They remain constant across different version of a row.
        if (isNewBusinessKey) {
            builder.setCcOpenDateId(JULIAN_DATE_START - generateUniformRandomInt(-365, 0, CC_CALL_CENTER_ID.getRandomNumberStream()));
            int numberOfCallCenters = getNumberOfCallCenters();
            int suffix = (int) rowNumber / numberOfCallCenters;
            String ccName = getCallCenterAtIndex((int) (rowNumber % numberOfCallCenters) + 1);
            if (suffix > 0) {
                ccName = format("%s_%d", ccName, suffix);
            }
            builder.setCcName(ccName);
            builder.setCcAddress(makeAddressForColumn(CC_ADDRESS, scaling));
        }
        else {
            checkState(previousRow != null, "previousRow has not yet been initialized");
            builder.setCcOpenDateId(previousRow.getCcOpenDateId());
            builder.setCcName(previousRow.getCcName());
            builder.setCcAddress(previousRow.getCcAddress());
        }

        // select the random number that controls if a field changes from one record to the next.
        int fieldChangeFlag = (int) CC_SCD.getRandomNumberStream().nextRandom();

        // The rest of the fields can either be a new data value or not.
        // We use a random number to determine which fields to replace and which to retain.
        // A field changes if isNewBusinessKey is true or the lowest order bit of the random number is zero.
        // Then we divide the fieldChangeFlag by 2 so the next field is determined by the next lower bit.
        String ccClass = pickRandomCallCenterClass(CC_CLASS.getRandomNumberStream());
        builder.setCcClass(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcClass(), ccClass));
        fieldChangeFlag /= 2;

        int ccEmployees = generateUniformRandomInt(1, MAX_NUMBER_OF_EMPLOYEES_UNSCALED * scaling.getScale() * scaling.getScale(), CC_EMPLOYEES.getRandomNumberStream());
        builder.setCcEmployees(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcEmployees(), ccEmployees));
        fieldChangeFlag /= 2;

        int ccSqFt = generateUniformRandomInt(100, 700, CC_SQ_FT.getRandomNumberStream());
        ccSqFt *= ccEmployees;
        builder.setCcSqFt(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcSqFt(), ccSqFt));
        fieldChangeFlag /= 2;

        String ccHours = pickRandomCallCenterHours(CC_HOURS.getRandomNumberStream());
        builder.setCcHours(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcHours(), ccHours));
        fieldChangeFlag /= 2;

        String managerFirstName = pickRandomFirstName(MALE_FREQUENCY, CC_MANAGER.getRandomNumberStream());
        String managerLastName = pickRandomLastName(CC_MANAGER.getRandomNumberStream());
        String ccManager = format("%s %s", managerFirstName, managerLastName);
        builder.setCcManager(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcManager(), ccManager));
        fieldChangeFlag /= 2;

        int ccMarketId = generateUniformRandomInt(1, 6, CC_MARKET_ID.getRandomNumberStream());
        builder.setCcMarketId(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcMarketId(), ccMarketId));
        fieldChangeFlag /= 2;

        String ccMarketClass = generateRandomText(20, WIDTH_CC_MARKET_CLASS, CC_MARKET_CLASS.getRandomNumberStream());
        builder.setCcMarketClass(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcMarketClass(), ccMarketClass));
        fieldChangeFlag /= 2;

        String ccMarketDesc = generateRandomText(20, WIDTH_CC_MARKET_DESC, CC_MARKET_DESC.getRandomNumberStream());
        builder.setCcMarketDesc(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcMarketDesc(), ccMarketDesc));
        fieldChangeFlag /= 2;

        String marketManagerFirstName = pickRandomFirstName(MALE_FREQUENCY, CC_MARKET_MANAGER.getRandomNumberStream());
        String marketManagerLastName = pickRandomLastName(CC_MARKET_MANAGER.getRandomNumberStream());
        String ccMarketManager = format("%s %s", marketManagerFirstName, marketManagerLastName);
        builder.setCcMarketManager(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcMarketManager(), ccMarketManager));
        fieldChangeFlag /= 2;

        int ccCompany = generateUniformRandomInt(1, 6, CC_COMPANY.getRandomNumberStream());
        builder.setCcCompany(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcCompany(), ccCompany));
        fieldChangeFlag /= 2;

        int ccDivisionId = generateUniformRandomInt(1, 6, CC_COMPANY.getRandomNumberStream());
        builder.setCcDivisionId(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcDivisionId(), ccDivisionId));
        fieldChangeFlag /= 2;

        String ccDivisionName = generateWord(ccDivisionId, WIDTH_CC_DIVISION_NAME);
        builder.setCcDivisionName(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcDivisionName(), ccDivisionName));
        fieldChangeFlag /= 2;

        String ccCompanyName = generateWord(ccCompany, 10);
        builder.setCcCompanyName(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcCompanyName(), ccCompanyName));
        fieldChangeFlag /= 2;

        Decimal ccTaxPercentage = generateUniformRandomDecimal(MIN_TAX_PERCENTAGE, MAX_TAX_PERCENTAGE, CC_TAX_PERCENTAGE.getRandomNumberStream());
        builder.setCcTaxPercentage(getValueForSlowlyChangingDimension(fieldChangeFlag, isNewBusinessKey, previousRow.getCcTaxPercentage(), ccTaxPercentage));

        previousRow = builder.build();
        return previousRow;
    }
}
