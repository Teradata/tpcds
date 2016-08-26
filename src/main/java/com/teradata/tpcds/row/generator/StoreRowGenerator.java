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
import com.teradata.tpcds.SlowlyChangingDimensionUtils.SlowlyChangingDimensionKey;
import com.teradata.tpcds.row.StoreRow;
import com.teradata.tpcds.type.Address;
import com.teradata.tpcds.type.Decimal;

import java.util.Optional;

import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.computeScdKey;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.getValueForSlowlyChangingDimension;
import static com.teradata.tpcds.Table.S_STORE;
import static com.teradata.tpcds.distribution.CallCenterDistributions.pickRandomCallCenterHours;
import static com.teradata.tpcds.distribution.EnglishDistributions.SYLLABLES_DISTRIBUTION;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.GENERAL_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.MALE_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomFirstName;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomLastName;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_ADDRESS;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_CLOSED_DATE_ID;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_EMPLOYEES;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_FLOOR_SPACE;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_HOURS;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_MANAGER;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_MARKET_DESC;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_MARKET_ID;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_MARKET_MANAGER;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_NULLS;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_SCD;
import static com.teradata.tpcds.generator.StoreGeneratorColumn.W_STORE_TAX_PERCENTAGE;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomText;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomDecimal;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.random.RandomValueGenerator.generateWord;
import static com.teradata.tpcds.type.Address.makeAddressForColumn;
import static com.teradata.tpcds.type.Date.JULIAN_DATE_MINIMUM;
import static java.lang.String.format;

public class StoreRowGenerator
        implements RowGenerator
{
    private static final int ROW_SIZE_S_MARKET_DESC = 100;
    private static final Decimal STORE_MIN_TAX_PERCENTAGE = new Decimal(0, 2);
    private static final Decimal STORE_MAX_TAX_PERCENTAGE = new Decimal(11, 2);
    private static final int STORE_MIN_DAYS_OPEN = 5;
    private static final int STORE_MAX_DAYS_OPEN = 500;
    private static final int STORE_CLOSED_PCT = 30;
    private static final int STORE_DESC_MIN = 15;

    private Optional<StoreRow> previousRow = Optional.empty();

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        long nullBitMap = createNullBitMap(W_STORE_NULLS);
        long storeSk = rowNumber;

        // The id combined with start and end dates represent the unique key for this row.
        // The id is what would be a primary key if there were only one version of each row
        // the start and end dates are the version information for the row.
        SlowlyChangingDimensionKey slowlyChangingDimensionKey = computeScdKey(S_STORE, rowNumber);
        String storeId = slowlyChangingDimensionKey.getBusinessKey();
        long recStartDateId = slowlyChangingDimensionKey.getStartDate();
        long recEndDateId = slowlyChangingDimensionKey.getEndDate();
        boolean isNewBusinessKey = slowlyChangingDimensionKey.isNewBusinessKey();

        // Select the random number that controls if a field changes from
        // one record to the next.
        int fieldChangeFlags = (int) W_STORE_SCD.getRandomNumberStream().nextRandom();

        // The rest of the fields can either be a new data value or not.
        // We use a random number to determine which fields to replace and which to retain.
        // A field changes if isNewBusinessKey is true or the lowest order bit of the random number is zero.
        // Then we do a single bitshift so the next field is determined by the next lower bit.

        int percentage = generateUniformRandomInt(1, 100, W_STORE_CLOSED_DATE_ID.getRandomNumberStream());
        int daysOpen = generateUniformRandomInt(STORE_MIN_DAYS_OPEN, STORE_MAX_DAYS_OPEN, W_STORE_CLOSED_DATE_ID.getRandomNumberStream());
        long closedDateId;
        if (percentage < STORE_CLOSED_PCT) {
            closedDateId = JULIAN_DATE_MINIMUM + daysOpen;
        }
        else {
            closedDateId = -1;
        }
        if (previousRow.isPresent()) {
            closedDateId = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getClosedDateId(), closedDateId);
        }
        fieldChangeFlags >>= 1;

        String storeName = generateWord(rowNumber, 5, SYLLABLES_DISTRIBUTION);
        if (previousRow.isPresent()) {
            storeName = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getStoreName(), storeName);
        }
        fieldChangeFlags >>= 1;

        int employees = generateUniformRandomInt(200, 300, W_STORE_EMPLOYEES.getRandomNumberStream());
        if (previousRow.isPresent()) {
            employees = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getEmployees(), employees);
        }
        fieldChangeFlags >>= 1;

        int floorSpace = generateUniformRandomInt(5000000, 10000000, W_STORE_FLOOR_SPACE.getRandomNumberStream());
        if (previousRow.isPresent()) {
            floorSpace = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getFloorSpace(), floorSpace);
        }
        fieldChangeFlags >>= 1;

        String hours = pickRandomCallCenterHours(W_STORE_HOURS.getRandomNumberStream());
        fieldChangeFlags >>= 1;

        String firstName = pickRandomFirstName(session.isSexist() ? MALE_FREQUENCY : GENERAL_FREQUENCY, W_STORE_MANAGER.getRandomNumberStream());
        String lastName = pickRandomLastName(W_STORE_MANAGER.getRandomNumberStream());
        String storeManager = format("%s %s", firstName, lastName);
        if (previousRow.isPresent()) {
            storeManager = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getStoreManager(), storeManager);
        }
        fieldChangeFlags >>= 1;

        int marketId = generateUniformRandomInt(1, 10, W_STORE_MARKET_ID.getRandomNumberStream());
        if (previousRow.isPresent()) {
            marketId = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getMarketId(), marketId);
        }
        fieldChangeFlags >>= 1;

        Decimal dTaxPercentage = generateUniformRandomDecimal(STORE_MIN_TAX_PERCENTAGE, STORE_MAX_TAX_PERCENTAGE, W_STORE_TAX_PERCENTAGE.getRandomNumberStream());
        if (previousRow.isPresent()) {
            dTaxPercentage = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getdTaxPercentage(), dTaxPercentage);
        }
        fieldChangeFlags >>= 1;

        // The geography_class distribution had only a single possible value, which we inline here as a constant.
        // We nevertheless need to update the field change flags
        String geographyClass = "Unknown";
        fieldChangeFlags >>= 1; // geographyClass

        String marketDesc = generateRandomText(STORE_DESC_MIN, ROW_SIZE_S_MARKET_DESC, W_STORE_MARKET_DESC.getRandomNumberStream());
        if (previousRow.isPresent()) {
            marketDesc = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getMarketDesc(), marketDesc);
        }
        fieldChangeFlags >>= 1;

        firstName = pickRandomFirstName(session.isSexist() ? MALE_FREQUENCY : GENERAL_FREQUENCY, W_STORE_MARKET_MANAGER.getRandomNumberStream());
        lastName = pickRandomLastName(W_STORE_MARKET_MANAGER.getRandomNumberStream());
        String marketManager = format("%s %s", firstName, lastName);
        if (previousRow.isPresent()) {
            marketManager = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getMarketManager(), marketManager);
        }
        fieldChangeFlags >>= 1;

        // These distributions also had only a single possible value, which we inline here as constants.
        // We nevertheless need to update the field change flags for each field
        String divisionName = "Unknown";
        int divisionId = 1;
        fieldChangeFlags >>= 1; // divisionId
        fieldChangeFlags >>= 1; // divisionName

        String companyName = "Unknown";
        int companyId = 1;
        fieldChangeFlags >>= 1; // companyId
        fieldChangeFlags >>= 1; // companyName

        // Many of the address values never get updated due to a bug in the C code whose behavior we are copying.
        // The fieldChangeFlags still need to be updated
        Address address = makeAddressForColumn(W_STORE_ADDRESS, session.getScaling());
        fieldChangeFlags >>= 1; // city
        fieldChangeFlags >>= 1; // county

        int gmtOffset = address.getGmtOffset();
        if (previousRow.isPresent()) {
            gmtOffset = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getAddress().getGmtOffset(), gmtOffset);
        }
        fieldChangeFlags >>= 1;

        fieldChangeFlags >>= 1; // state
        fieldChangeFlags >>= 1; // streetType
        fieldChangeFlags >>= 1; // streetName1
        fieldChangeFlags >>= 1; // streetName2

        int streetNumber = address.getStreetNumber();
        if (previousRow.isPresent()) {
            streetNumber = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getAddress().getStreetNumber(), streetNumber);
        }
        fieldChangeFlags >>= 1;

        int zip = address.getZip();
        if (previousRow.isPresent()) {
            zip = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getAddress().getZip(), zip);
        }

        address = new Address(address.getSuiteNumber(),
                streetNumber,
                address.getStreetName1(),
                address.getStreetName2(),
                address.getStreetType(),
                address.getCity(),
                address.getCounty(),
                address.getState(),
                address.getCountry(),
                zip,
                gmtOffset);

        StoreRow row = new StoreRow(nullBitMap,
                storeSk,
                storeId,
                recStartDateId,
                recEndDateId,
                closedDateId,
                storeName,
                employees,
                floorSpace,
                hours,
                storeManager,
                marketId,
                dTaxPercentage,
                geographyClass,
                marketDesc,
                marketManager,
                divisionId,
                divisionName,
                companyId,
                companyName,
                address);
        previousRow = Optional.of(row);
        return new RowGeneratorResult(row);
    }

    @Override
    public void reset()
    {
        previousRow = Optional.empty();
    }
}
