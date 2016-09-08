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

import com.teradata.tpcds.Scaling;
import com.teradata.tpcds.Session;
import com.teradata.tpcds.SlowlyChangingDimensionUtils.SlowlyChangingDimensionKey;
import com.teradata.tpcds.row.WebSiteRow;
import com.teradata.tpcds.type.Address;
import com.teradata.tpcds.type.Decimal;

import javax.annotation.concurrent.NotThreadSafe;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;
import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.computeScdKey;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.getValueForSlowlyChangingDimension;
import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.Table.WEB_SITE;
import static com.teradata.tpcds.distribution.EnglishDistributions.SYLLABLES_DISTRIBUTION;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.GENERAL_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.FirstNamesWeights.MALE_FREQUENCY;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomFirstName;
import static com.teradata.tpcds.distribution.NamesDistributions.pickRandomLastName;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_ADDRESS;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_CLOSE_DATE;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_COMPANY_ID;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_MANAGER;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_MARKET_CLASS;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_MARKET_DESC;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_MARKET_ID;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_MARKET_MANAGER;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_NULLS;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_OPEN_DATE;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_SCD;
import static com.teradata.tpcds.generator.WebSiteGeneratorColumn.WEB_TAX_PERCENTAGE;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomText;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomDecimal;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.random.RandomValueGenerator.generateWord;
import static com.teradata.tpcds.type.Address.makeAddressForColumn;
import static com.teradata.tpcds.type.Decimal.ZERO;
import static java.lang.String.format;

@NotThreadSafe
public class WebSiteRowGenerator
        extends AbstractRowGenerator
{
    private Optional<WebSiteRow> previousRow = Optional.empty();

    public WebSiteRowGenerator()
    {
        super(WEB_SITE);
    }

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session, RowGenerator parentRowGenerator, RowGenerator childRowGenerator)
    {
        long nullBitMap = createNullBitMap(WEB_SITE, getRandomNumberStream(WEB_NULLS));
        long webSiteSk = rowNumber;
        String webClass = "Unknown";

        SlowlyChangingDimensionKey slowlyChangingDimensionKey = computeScdKey(WEB_SITE, rowNumber);
        String webSiteId = slowlyChangingDimensionKey.getBusinessKey();
        long webRecStartDateId = slowlyChangingDimensionKey.getStartDate();
        long webRecEndDateId = slowlyChangingDimensionKey.getEndDate();
        boolean isNewBusinessKey = slowlyChangingDimensionKey.isNewBusinessKey();

        Scaling scaling = session.getScaling();
        long webOpenDate;
        long webCloseDate;
        String webName;
        if (isNewBusinessKey) {
            webOpenDate = generateJoinKey(WEB_OPEN_DATE, getRandomNumberStream(WEB_OPEN_DATE), DATE_DIM, rowNumber, scaling);
            webCloseDate = generateJoinKey(WEB_CLOSE_DATE, getRandomNumberStream(WEB_CLOSE_DATE), DATE_DIM, rowNumber, scaling);
            if (webCloseDate > webRecEndDateId) {
                webCloseDate = -1;
            }
            webName = format("site_%d", (int) (rowNumber / 6));
        }
        else {
            checkState(previousRow.isPresent(), "previousRow has not yet been initialized");
            webOpenDate = previousRow.get().getWebOpenDate();
            webCloseDate = previousRow.get().getWebCloseDate();
            webName = previousRow.get().getWebName();
        }

        // controls whether a field changes from one row to the next
        int fieldChangeFlags = (int) getRandomNumberStream(WEB_SCD).nextRandom();

        String firstName = pickRandomFirstName(session.isSexist() ? MALE_FREQUENCY : GENERAL_FREQUENCY, getRandomNumberStream(WEB_MANAGER));
        String lastName = pickRandomLastName(getRandomNumberStream(WEB_MANAGER));
        String webManager = format("%s %s", firstName, lastName);
        if (previousRow.isPresent()) {
            webManager = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebManager(), webManager);
        }
        fieldChangeFlags >>= 1;

        int webMarketId = generateUniformRandomInt(1, 6, getRandomNumberStream(WEB_MARKET_ID));
        if (previousRow.isPresent()) {
            webMarketId = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebMarketId(), webMarketId);
        }
        fieldChangeFlags >>= 1;

        String webMarketClass = generateRandomText(20, 50, getRandomNumberStream(WEB_MARKET_CLASS));
        if (previousRow.isPresent()) {
            webMarketClass = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebMarketClass(), webMarketClass);
        }
        fieldChangeFlags >>= 1;

        String webMarketDesc = generateRandomText(20, 100, getRandomNumberStream(WEB_MARKET_DESC));
        if (previousRow.isPresent()) {
            webMarketDesc = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebMarketDesc(), webMarketDesc);
        }
        fieldChangeFlags >>= 1;

        firstName = pickRandomFirstName(session.isSexist() ? MALE_FREQUENCY : GENERAL_FREQUENCY, getRandomNumberStream(WEB_MARKET_MANAGER));
        lastName = pickRandomLastName(getRandomNumberStream(WEB_MARKET_MANAGER));
        String webMarketManager = format("%s %s", firstName, lastName);
        if (previousRow.isPresent()) {
            webMarketManager = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebMarketManager(), webMarketManager);
        }
        fieldChangeFlags >>= 1;

        int webCompanyId = generateUniformRandomInt(1, 6, getRandomNumberStream(WEB_COMPANY_ID));
        if (previousRow.isPresent()) {
            webCompanyId = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebCompanyId(), webCompanyId);
        }
        fieldChangeFlags >>= 1;

        String webCompanyName = generateWord(webCompanyId, 100, SYLLABLES_DISTRIBUTION);
        if (previousRow.isPresent()) {
            webCompanyName = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebCompanyName(), webCompanyName);
        }
        fieldChangeFlags >>= 1;

        Address webAddress = makeAddressForColumn(WEB_SITE, getRandomNumberStream(WEB_ADDRESS), scaling);

        // some of the fields of address always use a new value due to a bug in the C code, but we still need to update the fieldChangeFlags
        fieldChangeFlags >>= 1; // city
        fieldChangeFlags >>= 1; // county

        int gmtOffset = webAddress.getGmtOffset();
        if (previousRow.isPresent()) {
            gmtOffset = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebAddress().getGmtOffset(), gmtOffset);
        }
        fieldChangeFlags >>= 1;

        fieldChangeFlags >>= 1; // state
        fieldChangeFlags >>= 1; // streetType
        fieldChangeFlags >>= 1; // streetName1
        fieldChangeFlags >>= 1; // streetName 2

        int streetNumber = webAddress.getStreetNumber();
        if (previousRow.isPresent()) {
            streetNumber = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebAddress().getStreetNumber(), streetNumber);
        }
        fieldChangeFlags >>= 1;

        int zip = webAddress.getZip();
        if (previousRow.isPresent()) {
            zip = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebAddress().getZip(), zip);
        }
        fieldChangeFlags >>= 1;

        webAddress = new Address(webAddress.getSuiteNumber(),
                streetNumber,
                webAddress.getStreetName1(),
                webAddress.getStreetName2(),
                webAddress.getStreetType(),
                webAddress.getCity(),
                webAddress.getCounty(),
                webAddress.getState(),
                webAddress.getCountry(),
                zip,
                gmtOffset);

        Decimal webTaxPercentage = generateUniformRandomDecimal(ZERO, new Decimal(12, 2), getRandomNumberStream(WEB_TAX_PERCENTAGE));
        if (previousRow.isPresent()) {
            webTaxPercentage = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewBusinessKey, previousRow.get().getWebTaxPercentage(), webTaxPercentage);
        }

        WebSiteRow row = new WebSiteRow(nullBitMap,
                webSiteSk,
                webSiteId,
                webRecStartDateId,
                webRecEndDateId,
                webName,
                webOpenDate,
                webCloseDate,
                webClass,
                webManager,
                webMarketId,
                webMarketClass,
                webMarketDesc,
                webMarketManager,
                webCompanyId,
                webCompanyName,
                webAddress,
                webTaxPercentage);

        previousRow = Optional.of(row);
        return new RowGeneratorResult(row);
    }
}
