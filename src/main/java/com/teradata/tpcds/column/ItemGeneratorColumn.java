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
import com.teradata.tpcds.random.RandomNumberStream;
import com.teradata.tpcds.random.RandomNumberStreamImpl;

import static com.teradata.tpcds.Table.ITEM;

public enum ItemGeneratorColumn
        implements GeneratorColumn
{
    I_ITEM_SK(203, 1),
    I_ITEM_ID(204, 1),
    I_REC_START_DATE_ID(205, 1),
    I_REC_END_DATE_ID(206, 2),
    I_ITEM_DESC(207, 200),
    I_CURRENT_PRICE(208, 2),
    I_WHOLESALE_COST(209, 1),
    I_BRAND_ID(210, 1),
    I_BRAND(211, 1),
    I_CLASS_ID(212, 1),
    I_CLASS(213, 1),
    I_CATEGORY_ID(214, 1),
    I_CATEGORY(215, 1),
    I_MANUFACT_ID(216, 2),
    I_MANUFACT(217, 1),
    I_SIZE(218, 1),
    I_FORMULATION(219, 50),
    I_COLOR(220, 1),
    I_UNITS(221, 1),
    I_CONTAINER(222, 1),
    I_MANAGER_ID(223, 2),
    I_PRODUCT_NAME(224, 1),
    I_NULLS(225, 2),
    I_SCD(226, 1),
    I_PROMO_SK(227, 2);

    private final RandomNumberStream randomNumberStream;
    private final int globalColumnNumber;

    ItemGeneratorColumn(int globalColumnNumber, int seedsPerRow)
    {
        this.globalColumnNumber = globalColumnNumber;
        this.randomNumberStream = new RandomNumberStreamImpl(globalColumnNumber, seedsPerRow);
    }

    @Override
    public Table getTable()
    {
        return ITEM;
    }

    @Override
    public RandomNumberStream getRandomNumberStream()
    {
        return randomNumberStream;
    }

    @Override
    public int getGlobalColumnNumber()
    {
        return globalColumnNumber;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
