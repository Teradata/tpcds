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

import com.teradata.tpcds.TableFlags.TableFlagsBuilder;

import java.lang.reflect.InvocationTargetException;

import static com.teradata.tpcds.ScalingInfo.ScalingModel.LINEAR;
import static com.teradata.tpcds.ScalingInfo.ScalingModel.LOGARITHMIC;
import static com.teradata.tpcds.ScalingInfo.ScalingModel.STATIC;

public enum Table
{
    CALL_CENTER(new TableFlagsBuilder().setIsSmall().setKeepsHistory().build(),
                100,
                0xB,
                CallCenterRowGenerator.class,
                CallCenterColumn.values(),
                new ScalingInfo(0, LOGARITHMIC, new int[]{3, 12, 15, 18, 21, 24, 27, 30, 30}, 0)),
    CATALOG_PAGE(new TableFlagsBuilder().build(),
                 200,
                 0x3,
                 CatalogPageRowGenerator.class,
                 CatalogPageColumn.values(),
                 new ScalingInfo(0, STATIC, new int[]{11718, 12000, 20400, 26000, 30000, 36000, 40000, 46000, 50000}, 0)),
    CATALOG_RETURNS,
    CUSTOMER,
    CUSTOMER_ADDRESS,
    CUSTOMER_DEMOGRAPHICS,
    DATE_DIM,
    DSDGEN_VERSION,
    HOUSEHOLD_DEMOGRAPHICS,
    INCOME_BAND,
    INVENTORY,
    ITEM,
    PROMOTION,
    REASON,
    SHIP_MODE,
    STORE,
    STORE_RETURNS,
    STORE_SALES,
    TIME_DIM,
    WAREHOUSE,
    WEB_PAGE,
    WEB_RETURNS,
    WEB_SALES,
    WEB_SITE,

    DBGEN_VERSION,

    // source tables
    S_BRAND,
    S_CUSTOMER_ADDRESS,
    S_CALL_CENTER,
    S_CATALOG,
    S_CATALOG_ORDER,
    S_CATALOG_ORDER_LINEITEM,
    S_CATALOG_PAGE,
    S_CATALOG_PROMOTIONAL_ITEM,
    S_CATALOG_RETURNS,
    S_CATEGORY,
    S_CLASS,
    S_COMPANY,
    S_CUSTOMER,
    S_DIVISION,
    S_INVENTORY,
    S_ITEM,
    S_MANAGER,
    S_MANUFACTURER,
    S_MARKET,
    S_PRODUCT,
    S_PROMOTION,
    S_PURCHASE,
    S_PURCHASE_LINEITEM,
    S_REASON,
    S_STORE,
    S_STORE_PROMOTIONAL_ITEM,
    S_STORE_RETURNS,
    S_SUBCATEGORY,
    S_SUBCLASS,
    S_WAREHOUSE,
    S_WEB_ORDER,
    S_WEB_ORDER_LINEITEM,
    S_WEB_PAGE,
    S_WEB_PROMOTIONAL_ITEM,
    S_WEB_RETURNS,
    S_WEB_SITE,
    S_ZIPG;

    private final TableFlags tableFlags;
    private final int nullBasisPoints;
    private final long notNullBitMap;
    private final ThreadLocal<RowGenerator> rowGeneratorThreadLocal;
    private final Column[] columns;
    private final ScalingInfo scalingInfo;

    // TODO: This constructor is a stop-gap until all the tables are implemented.  Remove it when it is no longer needed.
    Table()
    {
        this.tableFlags = new TableFlagsBuilder().build();
        this.nullBasisPoints = 0;
        this.notNullBitMap = 0;
        this.rowGeneratorThreadLocal = null;
        columns = new Column[0];
        scalingInfo = new ScalingInfo(0, LINEAR, new int[9], 0);
    }

    Table(TableFlags tableFlags, int nullBasisPoints, long notNullBitMap, Class<? extends RowGenerator> rowGeneratorClass, Column[] columns, ScalingInfo scalingInfo)
    {
        this.tableFlags = tableFlags;
        this.nullBasisPoints = nullBasisPoints;
        this.notNullBitMap = notNullBitMap;
        this.rowGeneratorThreadLocal = new ThreadLocal<RowGenerator>()
        {
            @Override
            protected RowGenerator initialValue()
            {
                try {
                    return rowGeneratorClass.getDeclaredConstructor().newInstance();
                }
                catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new TpcdsException(e.toString());
                }
            }
        };
        this.columns = columns;
        this.scalingInfo = scalingInfo;
    }

    @Override
    public String toString()
    {
        return name().toLowerCase();
    }

    public boolean keepsHistory()
    {
        return tableFlags.keepsHistory();
    }

    public boolean isSmall()
    {
        return tableFlags.isSmall();
    }

    public int getNullBasisPoints()
    {
        return nullBasisPoints;
    }

    public long getNotNullBitMap()
    {
        return notNullBitMap;
    }

    public RowGenerator getRowGenerator()
    {
        return rowGeneratorThreadLocal.get();
    }

    public Column[] getColumns()
    {
        return columns;
    }

    public ScalingInfo getScalingInfo()
    {
        return scalingInfo;
    }
}
