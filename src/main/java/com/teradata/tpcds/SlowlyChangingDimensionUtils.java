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

import static com.teradata.tpcds.BusinessKeyGenerator.makeBusinessKey;
import static com.teradata.tpcds.type.Date.JULIAN_DATA_END_DATE;
import static com.teradata.tpcds.type.Date.JULIAN_DATA_START_DATE;

public final class SlowlyChangingDimensionUtils
{
    private static final long ONE_HALF_DATE = JULIAN_DATA_START_DATE + (JULIAN_DATA_END_DATE - JULIAN_DATA_START_DATE) / 2;
    private static final long ONE_THIRD_PERIOD = (JULIAN_DATA_END_DATE - JULIAN_DATA_START_DATE) / 3;
    private static final long ONE_THIRD_DATE = JULIAN_DATA_START_DATE + ONE_THIRD_PERIOD;
    private static final long TWO_THIRDS_DATE = ONE_THIRD_DATE + ONE_THIRD_PERIOD;

    private SlowlyChangingDimensionUtils() {}

    public static SlowlyChangingDimensionKey computeScdKey(Table table, long rowNumber)
    {
        int modulo = (int) rowNumber % 6;
        long startDate;
        long endDate;
        boolean isNewKey = false;
        String businessKey;
        int tableNumber = table.ordinal();
        switch (modulo) {
            case 1: // 1 revision
                businessKey = makeBusinessKey(rowNumber);
                isNewKey = true;
                startDate = JULIAN_DATA_START_DATE - tableNumber * 6;
                endDate = -1;
                break;
            case 2: // 1 of 2 revisions
                businessKey = makeBusinessKey(rowNumber);
                isNewKey = true;
                startDate = JULIAN_DATA_START_DATE - tableNumber * 6;
                endDate = ONE_HALF_DATE - tableNumber * 6;
                break;
            case 3: // 2 of 2 revisions
                businessKey = makeBusinessKey(rowNumber - 1);
                startDate = ONE_HALF_DATE - tableNumber * 6 + 1;
                endDate = -1;
                break;
            case 4: // 1 of 3 revisions
                businessKey = makeBusinessKey(rowNumber);
                isNewKey = true;
                startDate = JULIAN_DATA_START_DATE - tableNumber * 6;
                endDate = ONE_THIRD_DATE - tableNumber * 6;
                break;
            case 5: // 2 of 3 revisions
                businessKey = makeBusinessKey(rowNumber - 1);
                startDate = ONE_THIRD_DATE - tableNumber * 6 + 1;
                endDate = TWO_THIRDS_DATE - tableNumber * 6;
                break;
            case 0: // 3 of 3 revisions
                businessKey = makeBusinessKey(rowNumber - 2);
                startDate = TWO_THIRDS_DATE - tableNumber * 6 + 1;
                endDate = -1;
                break;
            default:
                throw new InternalError("Something's wrong. Positive integers % 6 should always be covered by one of the cases");
        }

        if (endDate > JULIAN_DATA_END_DATE) {
            endDate = -1;
        }

        return new SlowlyChangingDimensionKey(businessKey, startDate, endDate, isNewKey);
    }

    public static <T> T getValueForSlowlyChangingDimension(int fieldChangeFlag, boolean isNewKey, T oldValue, T newValue)
    {
        return shouldChangeDimension(fieldChangeFlag, isNewKey) ? newValue : oldValue;
    }

    public static boolean shouldChangeDimension(int flags, boolean isNewKey)
    {
        return flags % 2 == 0 || isNewKey;
    }

    public static class SlowlyChangingDimensionKey
    {
        // A slowly changing dimension table is a dimension table that contains relatively static data that changes
        // slowly but unpredictably. The TPCDS data set contains slowly changing dimensions that use the Type 2 methodology
        // for managing changes to such rows. In the type 2 methodology, historical data is tracked by creating a new
        // row for each change. This means that multiple records will exist for a given natural key.
        // To uniquely identify a row, you need the business key, also known as the natural key,
        // along with the version number or effective dates for the row.
        // Here we use effective dates, denoted by the startDate and endDate fields.
        // A businessKey is called a new businessKey if it is the first version of a row with that businessKey.
        private final String businessKey;
        private final long startDate;
        private final long endDate;
        private final boolean isNewBusinessKey;

        public SlowlyChangingDimensionKey(String businessKey, long startDate, long endDate, boolean isNewBusinessKey)
        {
            this.businessKey = businessKey;
            this.startDate = startDate;
            this.endDate = endDate;
            this.isNewBusinessKey = isNewBusinessKey;
        }

        public String getBusinessKey()
        {
            return businessKey;
        }

        public long getStartDate()
        {
            return startDate;
        }

        public long getEndDate()
        {
            return endDate;
        }

        public boolean isNewBusinessKey()
        {
            return isNewBusinessKey;
        }
    }
}
