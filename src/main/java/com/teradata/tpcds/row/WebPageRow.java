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
import static com.teradata.tpcds.column.WebPageColumn.WP_ACCESS_DATE_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_AUTOGEN_FLAG;
import static com.teradata.tpcds.column.WebPageColumn.WP_CHAR_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_CREATION_DATE_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_CUSTOMER_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_IMAGE_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_LINK_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_MAX_AD_COUNT;
import static com.teradata.tpcds.column.WebPageColumn.WP_PAGE_ID;
import static com.teradata.tpcds.column.WebPageColumn.WP_PAGE_SK;
import static com.teradata.tpcds.column.WebPageColumn.WP_REC_END_DATE_ID;
import static com.teradata.tpcds.column.WebPageColumn.WP_REC_START_DATE_ID;
import static com.teradata.tpcds.column.WebPageColumn.WP_TYPE;
import static com.teradata.tpcds.column.WebPageColumn.WP_URL;

public class WebPageRow
        extends TableRowWithNulls
{
    private final long wpPageSk;
    private final String wpPageId;
    private final long wpRecStartDateId;
    private final long wpRecEndDateId;
    private final long wpCreationDateSk;
    private final long wpAccessDateSk;
    private final boolean wpAutogenFlag;
    private final long wpCustomerSk;
    private final String wpUrl;
    private final String wpType;
    private final int wpCharCount;
    private final int wpLinkCount;
    private final int wpImageCount;
    private final int wpMaxAdCount;

    public WebPageRow(long nullBitMap,
            long wpPageSk,
            String wpPageId,
            long wpRecStartDateId,
            long wpRecEndDateId,
            long wpCreationDateSk,
            long wpAccessDateSk,
            boolean wpAutogenFlag,
            long wpCustomerSk,
            String wpUrl,
            String wpType,
            int wpCharCount,
            int wpLinkCount,
            int wpImageCount,
            int wpMaxAdCount)
    {
        super(nullBitMap, WP_PAGE_SK);
        this.wpPageSk = wpPageSk;
        this.wpPageId = wpPageId;
        this.wpRecStartDateId = wpRecStartDateId;
        this.wpRecEndDateId = wpRecEndDateId;
        this.wpCreationDateSk = wpCreationDateSk;
        this.wpAccessDateSk = wpAccessDateSk;
        this.wpAutogenFlag = wpAutogenFlag;
        this.wpCustomerSk = wpCustomerSk;
        this.wpUrl = wpUrl;
        this.wpType = wpType;
        this.wpCharCount = wpCharCount;
        this.wpLinkCount = wpLinkCount;
        this.wpImageCount = wpImageCount;
        this.wpMaxAdCount = wpMaxAdCount;
    }

    public long getWpCreationDateSk()
    {
        return wpCreationDateSk;
    }

    public long getWpAccessDateSk()
    {
        return wpAccessDateSk;
    }

    public boolean getWpAutogenFlag()
    {
        return wpAutogenFlag;
    }

    public long getWpCustomerSk()
    {
        return wpCustomerSk;
    }

    public int getWpCharCount()
    {
        return wpCharCount;
    }

    public int getWpLinkCount()
    {
        return wpLinkCount;
    }

    public int getWpImageCount()
    {
        return wpImageCount;
    }

    public int getWpMaxAdCount()
    {
        return wpMaxAdCount;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(wpPageSk, WP_PAGE_SK),
                getStringOrNull(wpPageId, WP_PAGE_ID),
                getDateStringOrNullFromJulianDays(wpRecStartDateId, WP_REC_START_DATE_ID),
                getDateStringOrNullFromJulianDays(wpRecEndDateId, WP_REC_END_DATE_ID),
                getStringOrNullForKey(wpCreationDateSk, WP_CREATION_DATE_SK),
                getStringOrNullForKey(wpAccessDateSk, WP_ACCESS_DATE_SK),
                getStringOrNullForBoolean(wpAutogenFlag, WP_AUTOGEN_FLAG),
                getStringOrNullForKey(wpCustomerSk, WP_CUSTOMER_SK),
                getStringOrNull(wpUrl, WP_URL),
                getStringOrNull(wpType, WP_TYPE),
                getStringOrNull(wpCharCount, WP_CHAR_COUNT),
                getStringOrNull(wpLinkCount, WP_LINK_COUNT),
                getStringOrNull(wpImageCount, WP_IMAGE_COUNT),
                getStringOrNull(wpMaxAdCount, WP_MAX_AD_COUNT));
    }
}
