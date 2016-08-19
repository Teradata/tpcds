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

import static com.teradata.tpcds.Table.ITEM;

public enum ItemColumn
        implements Column
{
    I_ITEM_SK(),
    I_ITEM_ID(),
    I_REC_START_DATE(),
    I_REC_END_DATE(),
    I_ITEM_DESC(),
    I_CURRENT_PRICE(),
    I_WHOLESALE_COST(),
    I_BRAND_ID(),
    I_BRAND(),
    I_CLASS_ID(),
    I_CLASS(),
    I_CATEGORY_ID(),
    I_CATEGORY(),
    I_MANUFACT_ID(),
    I_MANUFACT(),
    I_SIZE(),
    I_FORMULATION(),
    I_COLOR(),
    I_UNITS(),
    I_CONTAINER(),
    I_MANAGER_ID(),
    I_PRODUCT_NAME();

    @Override
    public Table getTable()
    {
        return ITEM;
    }

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
