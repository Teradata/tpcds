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
package com.teradata.tpcds.column;

import com.teradata.tpcds.Table;

import static com.teradata.tpcds.Table.WEB_PAGE;

public enum WebPageColumn
        implements Column
{
    WP_WEB_PAGE_SK(),
    WP_WEB_PAGE_ID(),
    WP_REC_START_DATE(),
    WP_REC_END_DATE(),
    WP_CREATION_DATE_SK(),
    WP_ACCESS_DATE_SK(),
    WP_AUTOGEN_FLAG(),
    WP_CUSTOMER_SK(),
    WP_URL(),
    WP_TYPE(),
    WP_CHAR_COUNT(),
    WP_LINK_COUNT(),
    WP_IMAGE_COUNT(),
    WP_MAX_AD_COUNT();

    @Override
    public Table getTable()
    {
        return WEB_PAGE;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
