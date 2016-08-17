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

import java.util.Optional;

import static com.teradata.tpcds.JoinKeyUtils.generateJoinKey;
import static com.teradata.tpcds.Nulls.createNullBitMap;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.computeScdKey;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.getValueForSlowlyChangingDimension;
import static com.teradata.tpcds.Table.CUSTOMER;
import static com.teradata.tpcds.Table.DATE_DIM;
import static com.teradata.tpcds.Table.WEB_PAGE;
import static com.teradata.tpcds.column.WebPageColumn.WP_ACCESS_DATE_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_AUTOGEN_FLAG;
import static com.teradata.tpcds.column.WebPageColumn.WP_CHAR_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_CREATION_DATE_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_CUSTOMER_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_IMAGE_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_LINK_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_MAX_AD_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_NULLS;
import static com.teradata.tpcds.column.WebPageColumn.WP_SCD;
import static com.teradata.tpcds.column.WebPageColumn.WP_TYPE;
import static com.teradata.tpcds.column.WebPageColumn.WP_URL;
import static com.teradata.tpcds.distribution.WebPageUseDistribution.pickRandomWebPageUseType;
import static com.teradata.tpcds.random.RandomValueGenerator.generateRandomUrl;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.type.Date.JULIAN_TODAYS_DATE;

public class WebPageRowGenerator
        implements RowGenerator
{
    private static final int WP_AUTOGEN_PERCENT = 30;
    private Optional<WebPageRow> previousRow = Optional.empty();

    @Override
    public RowGeneratorResult generateRowAndChildRows(long rowNumber, Session session)
    {
        Scaling scaling = session.getScaling();

        long nullBitMap = createNullBitMap(WP_NULLS);
        long wpPageSk = rowNumber;

        SlowlyChangingDimensionKey slowlyChangingDimensionKey = computeScdKey(WEB_PAGE, rowNumber);
        String wpPageId = slowlyChangingDimensionKey.getBusinessKey();
        long wpRecStartDateId = slowlyChangingDimensionKey.getStartDate();
        long wpRecStartEndDateId = slowlyChangingDimensionKey.getEndDate();
        boolean isNewKey = slowlyChangingDimensionKey.isNewBusinessKey();
        int fieldChangeFlags = (int) WP_SCD.getRandomNumberStream().nextRandom();

        long wpCreationDateSk = generateJoinKey(WP_CREATION_DATE_SK, DATE_DIM, rowNumber, scaling);
        if (previousRow.isPresent()) {
            wpCreationDateSk = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpCreationDateSk(), wpCreationDateSk);
        }
        fieldChangeFlags >>= 1;

        int lastAccess = generateUniformRandomInt(0, 100, WP_ACCESS_DATE_SK.getRandomNumberStream());
        long wpAccessDateSk = JULIAN_TODAYS_DATE - lastAccess;
        if (previousRow.isPresent()) {
            wpAccessDateSk = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpAccessDateSk(), wpAccessDateSk);
        }
        fieldChangeFlags >>= 1;

        int randomInt = generateUniformRandomInt(0, 99, WP_AUTOGEN_FLAG.getRandomNumberStream());
        boolean wpAutogenFlag = randomInt < WP_AUTOGEN_PERCENT;
        if (previousRow.isPresent()) {
            wpAutogenFlag = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpAutogenFlag(), wpAutogenFlag);
        }
        fieldChangeFlags >>= 1;

        long wpCustomersk = generateJoinKey(WP_CUSTOMER_SK, CUSTOMER, 1, scaling);
        if (previousRow.isPresent()) {
            wpCustomersk = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpCustomerSk(), wpCustomersk);
        }
        fieldChangeFlags >>= 1;

        String wpUrl = generateRandomUrl(WP_URL.getRandomNumberStream()); // this actually returns the same value every time, so no need to check if it should change
        fieldChangeFlags >>= 1;

        String wpType = pickRandomWebPageUseType(WP_TYPE.getRandomNumberStream());  // always uses a new value due to a bug in the C code
        fieldChangeFlags >>= 1;

        int wpLinkCount = generateUniformRandomInt(2, 25, WP_LINK_COUNT.getRandomNumberStream());
        if (previousRow.isPresent()) {
            wpLinkCount = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpLinkCount(), wpLinkCount);
        }
        fieldChangeFlags >>= 1;

        int wpImageCount = generateUniformRandomInt(1, 7, WP_IMAGE_COUNT.getRandomNumberStream());
        if (previousRow.isPresent()) {
            wpImageCount = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpImageCount(), wpImageCount);
        }
        fieldChangeFlags >>= 1;

        int wpMaxAdCount = generateUniformRandomInt(0, 4, WP_MAX_AD_COUNT.getRandomNumberStream());
        if (previousRow.isPresent()) {
            wpMaxAdCount = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpMaxAdCount(), wpMaxAdCount);
        }
        fieldChangeFlags >>= 1;

        int wpCharCount = generateUniformRandomInt(wpLinkCount * 125 + wpImageCount * 50,
                wpLinkCount * 300 + wpImageCount * 150,
                WP_CHAR_COUNT.getRandomNumberStream());
        if (previousRow.isPresent()) {
            wpCharCount = getValueForSlowlyChangingDimension(fieldChangeFlags, isNewKey, previousRow.get().getWpCharCount(), wpCharCount);
        }

        previousRow = Optional.of(new WebPageRow(nullBitMap,
                wpPageSk,
                wpPageId,
                wpRecStartDateId,
                wpRecStartEndDateId,
                wpCreationDateSk,
                wpAccessDateSk,
                wpAutogenFlag,
                wpCustomersk,
                wpUrl,
                wpType,
                wpCharCount,
                wpLinkCount,
                wpImageCount,
                wpMaxAdCount));

        return new RowGeneratorResult(new WebPageRow(nullBitMap,
                wpPageSk,
                wpPageId,
                wpRecStartDateId,
                wpRecStartEndDateId,
                wpCreationDateSk,
                wpAccessDateSk,
                wpAutogenFlag,
                wpAutogenFlag ? wpCustomersk : -1,
                wpUrl,
                wpType,
                wpCharCount,
                wpLinkCount,
                wpImageCount,
                wpMaxAdCount));
    }

    @Override
    public void reset()
    {
        previousRow = Optional.empty();
    }
}
