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

import static com.teradata.tpcds.Table.CALL_CENTER;

public enum CallCenterColumn
        implements Column
{
    CC_CALL_CENTER_SK(),
    CC_CALL_CENTER_ID(),
    CC_REC_START_DATE(),
    CC_REC_END_DATE(),
    CC_CLOSED_DATE_SK(),
    CC_OPEN_DATE_SK(),
    CC_NAME(),
    CC_CLASS(),
    CC_EMPLOYEES(),
    CC_SQ_FT(),
    CC_HOURS(),
    CC_MANAGER(),
    CC_MKT_ID(),
    CC_MKT_CLASS(),
    CC_MKT_DESC(),
    CC_MARKET_MANAGER(),
    CC_DIVISION(),
    CC_DIVISION_NAME(),
    CC_COMPANY(),
    CC_COMPANY_NAME(),
    CC_STREET_NUMBER(),
    CC_STREET_NAME(),
    CC_STREET_TYPE(),
    CC_SUITE_NUMBER(),
    CC_CITY(),
    CC_COUNTY(),
    CC_STATE(),
    CC_ZIP(),
    CC_COUNTRY(),
    CC_GMT_OFFSET(),
    CC_TAX_PERCENTAGE();

    @Override
    public Table getTable()
    {
        return CALL_CENTER;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
