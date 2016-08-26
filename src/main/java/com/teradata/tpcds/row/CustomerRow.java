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
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_BIRTH_COUNTRY;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_BIRTH_DAY;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_BIRTH_MONTH;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_BIRTH_YEAR;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_CURRENT_ADDR_SK;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_CURRENT_CDEMO_SK;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_CURRENT_HDEMO_SK;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_CUSTOMER_ID;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_CUSTOMER_SK;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_EMAIL_ADDRESS;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_FIRST_NAME;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_FIRST_SALES_DATE_ID;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_FIRST_SHIPTO_DATE_ID;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_LAST_NAME;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_LAST_REVIEW_DATE;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_PREFERRED_CUST_FLAG;
import static com.teradata.tpcds.generator.CustomerGeneratorColumn.C_SALUTATION;

public class CustomerRow
        extends TableRowWithNulls
{
    private final long cCustomerSk;
    private final String cCustomerId;
    private final long cCurrentCdemoSk;
    private final long cCurrentHdemoSk;
    private final long cCurrentAddrSk;
    private final int cFirstShiptoDateId;
    private final int cFirstSalesDateId;
    private final String cSalutation;
    private final String cFirstName;
    private final String cLastName;
    private final boolean cPreferredCustFlag;
    private final int cBirthDay;
    private final int cBirthMonth;
    private final int cBirthYear;
    private final String cBirthCountry;
    private final String cLogin;
    private final String cEmailAddress;
    private final int cLastReviewDate;

    public CustomerRow(long cCustomerSk,
            String cCustomerId,
            long cCurrentCdemoSk,
            long cCurrentHdemoSk,
            long cCurrentAddrSk,
            int cFirstShiptoDateId,
            int cFirstSalesDateId,
            String cSalutation,
            String cFirstName,
            String cLastName,
            boolean cPreferredCustFlag,
            int cBirthDay,
            int cBirthMonth,
            int cBirthYear,
            String cBirthCountry,
            String cEmailAddress,
            int cLastReviewDate,
            long nullBitMap)
    {
        super(nullBitMap, C_CUSTOMER_SK);
        this.cCustomerSk = cCustomerSk;
        this.cCustomerId = cCustomerId;
        this.cCurrentCdemoSk = cCurrentCdemoSk;
        this.cCurrentHdemoSk = cCurrentHdemoSk;
        this.cCurrentAddrSk = cCurrentAddrSk;
        this.cFirstShiptoDateId = cFirstShiptoDateId;
        this.cFirstSalesDateId = cFirstSalesDateId;
        this.cSalutation = cSalutation;
        this.cFirstName = cFirstName;
        this.cLastName = cLastName;
        this.cPreferredCustFlag = cPreferredCustFlag;
        this.cBirthDay = cBirthDay;
        this.cBirthMonth = cBirthMonth;
        this.cBirthYear = cBirthYear;
        this.cBirthCountry = cBirthCountry;
        this.cLogin = null;  // never gets set to anything
        this.cEmailAddress = cEmailAddress;
        this.cLastReviewDate = cLastReviewDate;
    }

    @Override
    public List<String> getValues()
    {
        return newArrayList(getStringOrNullForKey(cCustomerSk, C_CUSTOMER_SK),
                getStringOrNull(cCustomerId, C_CUSTOMER_ID),
                getStringOrNullForKey(cCurrentCdemoSk, C_CURRENT_CDEMO_SK),
                getStringOrNullForKey(cCurrentHdemoSk, C_CURRENT_HDEMO_SK),
                getStringOrNullForKey(cCurrentAddrSk, C_CURRENT_ADDR_SK),
                getStringOrNull(cFirstShiptoDateId, C_FIRST_SHIPTO_DATE_ID),
                getStringOrNull(cFirstSalesDateId, C_FIRST_SALES_DATE_ID),
                getStringOrNull(cSalutation, C_SALUTATION),
                getStringOrNull(cFirstName, C_FIRST_NAME),
                getStringOrNull(cLastName, C_LAST_NAME),
                getStringOrNullForBoolean(cPreferredCustFlag, C_PREFERRED_CUST_FLAG),
                getStringOrNull(cBirthDay, C_BIRTH_DAY),
                getStringOrNull(cBirthMonth, C_BIRTH_MONTH),
                getStringOrNull(cBirthYear, C_BIRTH_YEAR),
                getStringOrNull(cBirthCountry, C_BIRTH_COUNTRY),
                cLogin,
                getStringOrNull(cEmailAddress, C_EMAIL_ADDRESS),
                getStringOrNull(cLastReviewDate, C_LAST_REVIEW_DATE)
        );
    }
}
