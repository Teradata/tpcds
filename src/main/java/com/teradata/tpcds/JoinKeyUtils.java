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

import com.teradata.tpcds.distribution.CalendarDistribution;
import com.teradata.tpcds.type.Date;

import static com.teradata.tpcds.CatalogPageRowGenerator.CATALOGS_PER_YEAR;
import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_MAX_SHIP_DELAY;
import static com.teradata.tpcds.CatalogSalesRowGenerator.CS_MIN_SHIP_DELAY;
import static com.teradata.tpcds.SlowlyChangingDimensionUtils.matchSurrogateKey;
import static com.teradata.tpcds.StoreSalesRowGenerator.SS_MAX_SHIP_DELAY;
import static com.teradata.tpcds.StoreSalesRowGenerator.SS_MIN_SHIP_DELAY;
import static com.teradata.tpcds.Table.CATALOG_PAGE;
import static com.teradata.tpcds.distribution.CalendarDistribution.Weights.SALES;
import static com.teradata.tpcds.distribution.CalendarDistribution.Weights.SALES_LEAP_YEAR;
import static com.teradata.tpcds.distribution.CalendarDistribution.Weights.UNIFORM_LEAP_YEAR;
import static com.teradata.tpcds.distribution.CalendarDistribution.pickRandomDayOfYear;
import static com.teradata.tpcds.distribution.CatalogPageDistributions.pickRandomCatalogPageType;
import static com.teradata.tpcds.distribution.HoursDistribution.Weights.CATALOG_AND_WEB;
import static com.teradata.tpcds.distribution.HoursDistribution.Weights.STORE;
import static com.teradata.tpcds.distribution.HoursDistribution.Weights.UNIFORM;
import static com.teradata.tpcds.distribution.HoursDistribution.pickRandomHour;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomInt;
import static com.teradata.tpcds.random.RandomValueGenerator.generateUniformRandomKey;
import static com.teradata.tpcds.type.Date.DATE_MAXIMUM;
import static com.teradata.tpcds.type.Date.DATE_MINIMUM;
import static com.teradata.tpcds.type.Date.JULIAN_DATA_START_DATE;
import static com.teradata.tpcds.type.Date.JULIAN_TODAYS_DATE;
import static com.teradata.tpcds.type.Date.isLeapYear;
import static com.teradata.tpcds.type.Date.toJulianDays;
import static java.lang.String.format;

public final class JoinKeyUtils
{
    private JoinKeyUtils() {}

    public static long generateJoinKey(Column fromColumn, Table toTable, long joinCount, Scaling scaling)
    {
        Table fromTable = fromColumn.getTable();

        switch (toTable) {
            case CATALOG_PAGE:
                return generateCatalogPageJoinKey(fromColumn, joinCount, scaling);
            case DATE_DIM:
                int year = generateUniformRandomInt(DATE_MINIMUM.getYear(), DATE_MAXIMUM.getYear(), fromColumn.getRandomNumberStream());
                return generateDateJoinKey(fromTable, fromColumn, joinCount, year);
            case TIME_DIM:
                return generateTimeJoinKey(fromTable, fromColumn);
            default:
                if (toTable.keepsHistory()) {
                    return generateScdJoinKey(toTable, fromColumn, joinCount, scaling);
                }

                return generateUniformRandomKey(1, scaling.getRowCount(toTable), fromColumn.getRandomNumberStream());
        }
    }

    private static long generateCatalogPageJoinKey(Column fromColumn, long julianDate, Scaling scaling)
    {
        int pagesPerCatalog = ((int) scaling.getRowCount(CATALOG_PAGE) / CATALOGS_PER_YEAR) / (DATE_MAXIMUM.getYear() - DATE_MINIMUM.getYear() + 2);

        String type = pickRandomCatalogPageType(fromColumn.getRandomNumberStream());
        int page = generateUniformRandomInt(1, pagesPerCatalog, fromColumn.getRandomNumberStream());
        int offset = (int) julianDate - JULIAN_DATA_START_DATE - 1;
        int count = (offset / 365) * CATALOGS_PER_YEAR;
        offset %= 365;

        switch (type) {
            case "bi-annual":
                if (offset > 183) {
                    count += 1;
                }
                break;
            case "quarterly": // quarterly
                count += offset / 91;
                break;
            case "monthly": // monthly
                count += offset / 31;
                break;
            default:
                throw new TpcdsException(format("Invalid catalog_page_type: %s", type));
        }

        return count * pagesPerCatalog + page;
    }

    private static long generateDateJoinKey(Table fromTable, Column fromColumn, long joinCount, int year)
    {
        int dayNumber;
        switch (fromTable) {
            case STORE_SALES:
            case CATALOG_SALES:
            case WEB_SALES:
                CalendarDistribution.Weights weights = SALES;
                if (isLeapYear(year)) {
                    weights = SALES_LEAP_YEAR;
                }
                dayNumber = pickRandomDayOfYear(weights, fromColumn.getRandomNumberStream());
                int result = toJulianDays(new Date(year, 1, 1)) + dayNumber;
                return result > JULIAN_TODAYS_DATE ? -1 : result;

            // returns are keyed to the sale date, with the lag between sale and return selected within a known range, based on
            // sales channel
            case STORE_RETURNS:
            case CATALOG_RETURNS:
            case WEB_RETURNS:
                return generateDateReturnsJoinKey(fromTable, fromColumn, joinCount);
            case WEB_SITE:
            case WEB_PAGE:
                // TODO: web_join
                throw new RuntimeException("not yet implemented");
            default:
                weights = CalendarDistribution.Weights.UNIFORM;
                if (isLeapYear(year)) {
                    weights = UNIFORM_LEAP_YEAR;
                }
                dayNumber = pickRandomDayOfYear(weights, fromColumn.getRandomNumberStream());
                result = toJulianDays(new Date(year, 1, 1)) + dayNumber;
                return result > JULIAN_TODAYS_DATE ? -1 : result;
        }
    }

    private static long generateDateReturnsJoinKey(Table fromTable, Column fromColumn, long joinCount)
    {
        int min;
        int max;
        switch (fromTable) {
            case STORE_RETURNS:
                min = SS_MIN_SHIP_DELAY;
                max = SS_MAX_SHIP_DELAY;
            case CATALOG_RETURNS:
                min = CS_MIN_SHIP_DELAY;
                max = CS_MAX_SHIP_DELAY;
                break;
            case WEB_RETURNS:
                // TODO: set min and max
                throw new RuntimeException("not yet implemented");
            default:
                throw new TpcdsException("Invalid table for dateJoinReturns");
        }
        int lag = generateUniformRandomInt(min * 2, max * 2, fromColumn.getRandomNumberStream());
        return joinCount + lag;
    }

    private static long generateTimeJoinKey(Table fromTable, Column fromColumn)
    {
        int hour;
        switch (fromTable) {
            case STORE_SALES:
            case STORE_RETURNS:
                hour = pickRandomHour(STORE, fromColumn.getRandomNumberStream());
                break;
            case CATALOG_SALES:
            case WEB_SALES:
            case CATALOG_RETURNS:
            case WEB_RETURNS:
                hour = pickRandomHour(CATALOG_AND_WEB, fromColumn.getRandomNumberStream());
                break;
            default:
                hour = pickRandomHour(UNIFORM, fromColumn.getRandomNumberStream());
                break;
        }

        int seconds = generateUniformRandomInt(0, 3599, fromColumn.getRandomNumberStream());

        return (long) (hour * 3600 + seconds);
    }

    private static long generateScdJoinKey(Table toTable, Column fromColumn, long julianDate, Scaling scaling)
    {
        // can't have a revision in the future
        if (julianDate > Date.JULIAN_DATA_END_DATE) {
            return -1;
        }

        long idCount = scaling.getIdCount(toTable);
        long key = generateUniformRandomKey(1, idCount, fromColumn.getRandomNumberStream());
        // map to the date sensitive surrogate key
        key = matchSurrogateKey(key, julianDate, toTable, scaling);

        return key > scaling.getRowCount(toTable) ? -1 : key;
    }
}
