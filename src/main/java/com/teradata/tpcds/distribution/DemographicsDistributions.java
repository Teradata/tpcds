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

package com.teradata.tpcds.distribution;

import static com.teradata.tpcds.distribution.IntValuesDistribution.buildIntValuesDistribution;
import static com.teradata.tpcds.distribution.StringValuesDistribution.buildStringValuesDistribution;

public final class DemographicsDistributions
{
    public static final StringValuesDistribution GENDER_DISTRIBUTION = buildStringValuesDistribution("genders.dst", 1, 1);
    public static final StringValuesDistribution MARITAL_STATUS_DISTRIBUTION = buildStringValuesDistribution("marital_statuses.dst", 1, 1);
    public static final StringValuesDistribution EDUCATION_DISTRIBUTION = buildStringValuesDistribution("education.dst", 1, 4);
    public static final IntValuesDistribution PURCHASE_BAND_DISTRIBUTION = buildIntValuesDistribution("purchase_band.dst", 1, 1);
    public static final StringValuesDistribution CREDIT_RATING_DISTRIBUTION = buildStringValuesDistribution("credit_ratings.dst", 1, 1);
    public static final IntValuesDistribution INCOME_BAND_DISTRIBUTION = buildIntValuesDistribution("income_band.dst", 2, 1);
    public static final StringValuesDistribution BUY_POTENTIAL_DISTRIBUTION = buildStringValuesDistribution("buy_potential.dst", 1, 1);
    public static final IntValuesDistribution DEP_COUNT_DISTRIBUTION = buildIntValuesDistribution("dep_count.dst", 1, 1);
    public static final IntValuesDistribution VEHICLE_COUNT_DISTRIBUTION = buildIntValuesDistribution("vehicle_count.dst", 1, 1);

    private DemographicsDistributions() {}

    public static String getGenderForIndexModSize(long index)
    {
        return GENDER_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static String getMaritalStatusForIndexModSize(long index)
    {
        return MARITAL_STATUS_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static String getEducationForIndexModSize(long index)
    {
        return EDUCATION_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static Integer getPurchaseBandForIndexModSize(long index)
    {
        return PURCHASE_BAND_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static String getCreditRatingForIndexModSize(long index)
    {
        return CREDIT_RATING_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static String getBuyPotentialForIndexModSize(long index)
    {
        return BUY_POTENTIAL_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static Integer getDepCountForIndexModSize(long index)
    {
        return DEP_COUNT_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static Integer getVehicleCountForIndexModSize(long index)
    {
        return VEHICLE_COUNT_DISTRIBUTION.getValueForIndexModSize(index, 0);
    }

    public static Integer getIncomeBandLowerBoundAtIndex(int index)
    {
        return INCOME_BAND_DISTRIBUTION.getValueAtIndex(0, index);
    }

    public static Integer getIncomeBandUpperBoundAtIndex(int index)
    {
        return INCOME_BAND_DISTRIBUTION.getValueAtIndex(1, index);
    }
}
