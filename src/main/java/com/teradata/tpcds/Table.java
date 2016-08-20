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

import com.google.common.collect.ImmutableList;
import com.teradata.tpcds.TableFlags.TableFlagsBuilder;
import com.teradata.tpcds.column.CallCenterColumn;
import com.teradata.tpcds.column.CatalogPageColumn;
import com.teradata.tpcds.column.CatalogReturnsColumn;
import com.teradata.tpcds.column.CatalogSalesColumn;
import com.teradata.tpcds.column.Column;
import com.teradata.tpcds.column.CustomerAddressColumn;
import com.teradata.tpcds.column.CustomerColumn;
import com.teradata.tpcds.column.CustomerDemographicsColumn;
import com.teradata.tpcds.column.DateDimColumn;
import com.teradata.tpcds.column.DbgenVersionColumn;
import com.teradata.tpcds.column.HouseholdDemographicsColumn;
import com.teradata.tpcds.column.IncomeBandColumn;
import com.teradata.tpcds.column.InventoryColumn;
import com.teradata.tpcds.column.ItemColumn;
import com.teradata.tpcds.column.PromotionColumn;
import com.teradata.tpcds.column.ReasonColumn;
import com.teradata.tpcds.column.ShipModeColumn;
import com.teradata.tpcds.column.StoreColumn;
import com.teradata.tpcds.column.StoreReturnsColumn;
import com.teradata.tpcds.column.StoreSalesColumn;
import com.teradata.tpcds.column.TimeDimColumn;
import com.teradata.tpcds.column.WarehouseColumn;
import com.teradata.tpcds.column.WebPageColumn;
import com.teradata.tpcds.column.WebReturnsColumn;
import com.teradata.tpcds.column.WebSalesColumn;
import com.teradata.tpcds.column.WebSiteColumn;
import com.teradata.tpcds.column.generator.CallCenterGeneratorColumn;
import com.teradata.tpcds.column.generator.CatalogPageGeneratorColumn;
import com.teradata.tpcds.column.generator.CatalogReturnsGeneratorColumn;
import com.teradata.tpcds.column.generator.CatalogSalesGeneratorColumn;
import com.teradata.tpcds.column.generator.CustomerAddressGeneratorColumn;
import com.teradata.tpcds.column.generator.CustomerDemographicsGeneratorColumn;
import com.teradata.tpcds.column.generator.CustomerGeneratorColumn;
import com.teradata.tpcds.column.generator.DateDimGeneratorColumn;
import com.teradata.tpcds.column.generator.DbgenVersionGeneratorColumn;
import com.teradata.tpcds.column.generator.GeneratorColumn;
import com.teradata.tpcds.column.generator.HouseholdDemographicsGeneratorColumn;
import com.teradata.tpcds.column.generator.IncomeBandGeneratorColumn;
import com.teradata.tpcds.column.generator.InventoryGeneratorColumn;
import com.teradata.tpcds.column.generator.ItemGeneratorColumn;
import com.teradata.tpcds.column.generator.PromotionGeneratorColumn;
import com.teradata.tpcds.column.generator.ReasonGeneratorColumn;
import com.teradata.tpcds.column.generator.ShipModeGeneratorColumn;
import com.teradata.tpcds.column.generator.StoreGeneratorColumn;
import com.teradata.tpcds.column.generator.StoreReturnsGeneratorColumn;
import com.teradata.tpcds.column.generator.StoreSalesGeneratorColumn;
import com.teradata.tpcds.column.generator.TimeDimGeneratorColumn;
import com.teradata.tpcds.column.generator.WarehouseGeneratorColumn;
import com.teradata.tpcds.column.generator.WebPageGeneratorColumn;
import com.teradata.tpcds.column.generator.WebReturnsGeneratorColumn;
import com.teradata.tpcds.column.generator.WebSalesGeneratorColumn;
import com.teradata.tpcds.column.generator.WebSiteGeneratorColumn;
import com.teradata.tpcds.row.generator.CallCenterRowGenerator;
import com.teradata.tpcds.row.generator.CatalogPageRowGenerator;
import com.teradata.tpcds.row.generator.CatalogReturnsRowGenerator;
import com.teradata.tpcds.row.generator.CatalogSalesRowGenerator;
import com.teradata.tpcds.row.generator.CustomerAddressRowGenerator;
import com.teradata.tpcds.row.generator.CustomerDemographicsRowGenerator;
import com.teradata.tpcds.row.generator.CustomerRowGenerator;
import com.teradata.tpcds.row.generator.DateDimRowGenerator;
import com.teradata.tpcds.row.generator.DbgenVersionRowGenerator;
import com.teradata.tpcds.row.generator.HouseholdDemographicsRowGenerator;
import com.teradata.tpcds.row.generator.IncomeBandRowGenerator;
import com.teradata.tpcds.row.generator.InventoryRowGenerator;
import com.teradata.tpcds.row.generator.ItemRowGenerator;
import com.teradata.tpcds.row.generator.PromotionRowGenerator;
import com.teradata.tpcds.row.generator.ReasonRowGenerator;
import com.teradata.tpcds.row.generator.RowGenerator;
import com.teradata.tpcds.row.generator.ShipModeRowGenerator;
import com.teradata.tpcds.row.generator.StoreReturnsRowGenerator;
import com.teradata.tpcds.row.generator.StoreRowGenerator;
import com.teradata.tpcds.row.generator.StoreSalesRowGenerator;
import com.teradata.tpcds.row.generator.TimeDimRowGenerator;
import com.teradata.tpcds.row.generator.WarehouseRowGenerator;
import com.teradata.tpcds.row.generator.WebPageRowGenerator;
import com.teradata.tpcds.row.generator.WebReturnsRowGenerator;
import com.teradata.tpcds.row.generator.WebSalesRowGenerator;
import com.teradata.tpcds.row.generator.WebSiteRowGenerator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.teradata.tpcds.ScalingInfo.ScalingModel.LINEAR;
import static com.teradata.tpcds.ScalingInfo.ScalingModel.LOGARITHMIC;
import static com.teradata.tpcds.ScalingInfo.ScalingModel.STATIC;

public enum Table
{
    CALL_CENTER(new TableFlagsBuilder().setIsSmall().setKeepsHistory().build(),
            100,
            0xB,
            CallCenterRowGenerator.class,
            CallCenterGeneratorColumn.values(),
            CallCenterColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {3, 12, 15, 18, 21, 24, 27, 30, 30}, 0)),
    CATALOG_PAGE(new TableFlagsBuilder().build(),
            200,
            0x3,
            CatalogPageRowGenerator.class,
            CatalogPageGeneratorColumn.values(),
            CatalogPageColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {11718, 12000, 20400, 26000, 30000, 36000, 40000, 46000, 50000}, 0)),
    CATALOG_RETURNS(new TableFlagsBuilder().build(),
            400,
            0x10007,
            CatalogReturnsRowGenerator.class,
            CatalogReturnsGeneratorColumn.values(),
            CatalogReturnsColumn.values(),
            new ScalingInfo(4, LINEAR, new int[] {16, 160, 1600, 4800, 16000, 48000, 160000, 480000, 1600000}, 0)),
    CATALOG_SALES(new TableFlagsBuilder().setIsDateBased().build(),
            100,
            0x28000,
            CatalogSalesRowGenerator.class,
            CatalogSalesGeneratorColumn.values(),
            CatalogSalesColumn.values(),
            new ScalingInfo(4, LINEAR, new int[] {16, 160, 1600, 4800, 16000, 48000, 160000, 480000, 1600000}, 0)),
    CUSTOMER(new TableFlagsBuilder().build(),
            700,
            0x13,
            CustomerRowGenerator.class,
            CustomerGeneratorColumn.values(),
            CustomerColumn.values(),
            new ScalingInfo(3, LOGARITHMIC, new int[] {100, 500, 2000, 5000, 12000, 30000, 65000, 80000, 100000}, 0)),
    CUSTOMER_ADDRESS(new TableFlagsBuilder().build(),
            600,
            0x3,
            CustomerAddressRowGenerator.class,
            CustomerAddressGeneratorColumn.values(),
            CustomerAddressColumn.values(),
            new ScalingInfo(3, LOGARITHMIC, new int[] {50, 250, 1000, 2500, 6000, 15000, 32500, 40000, 50000}, 0)),
    CUSTOMER_DEMOGRAPHICS(new TableFlagsBuilder().build(),
            0,
            0x1,
            CustomerDemographicsRowGenerator.class,
            CustomerDemographicsGeneratorColumn.values(),
            CustomerDemographicsColumn.values(),
            new ScalingInfo(2, STATIC, new int[] {19208, 19208, 19208, 19208, 19208, 19208, 19208, 19208, 19208}, 0)),
    DATE_DIM(new TableFlagsBuilder().build(),
            0,
            0x03,
            DateDimRowGenerator.class,
            DateDimGeneratorColumn.values(),
            DateDimColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {73049, 73049, 73049, 73049, 73049, 73049, 73049, 73049, 73049}, 0)),
    HOUSEHOLD_DEMOGRAPHICS(new TableFlagsBuilder().build(),
            0,
            0x01,
            HouseholdDemographicsRowGenerator.class,
            HouseholdDemographicsGeneratorColumn.values(),
            HouseholdDemographicsColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {7200, 7200, 7200, 7200, 7200, 7200, 7200, 7200, 7200}, 0)),
    INCOME_BAND(new TableFlagsBuilder().build(),
            0,
            0x1,
            IncomeBandRowGenerator.class,
            IncomeBandGeneratorColumn.values(),
            IncomeBandColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {20, 20, 20, 20, 20, 20, 20, 20, 20}, 0)),
    INVENTORY(new TableFlagsBuilder().setIsDateBased().build(),
            1000,
            0x07,
            InventoryRowGenerator.class,
            InventoryGeneratorColumn.values(),
            InventoryColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0}, 0)), // the inventory table is scaled based on item and warehouse
    ITEM(new TableFlagsBuilder().setKeepsHistory().build(),
            50,
            0x0B,
            ItemRowGenerator.class,
            ItemGeneratorColumn.values(),
            ItemColumn.values(),
            new ScalingInfo(3, LOGARITHMIC, new int[] {9, 51, 102, 132, 150, 180, 201, 231, 251}, 0)),
    PROMOTION(new TableFlagsBuilder().build(),
            200,
            0x3,
            PromotionRowGenerator.class,
            PromotionGeneratorColumn.values(),
            PromotionColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {300, 500, 1000, 1300, 1500, 1800, 2000, 2300, 2500}, 0)),
    REASON(new TableFlagsBuilder().build(),
            0,
            0x03,
            ReasonRowGenerator.class,
            ReasonGeneratorColumn.values(),
            ReasonColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {35, 45, 55, 60, 65, 67, 70, 72, 75}, 0)),
    SHIP_MODE(new TableFlagsBuilder().build(),
            0,
            0x03,
            ShipModeRowGenerator.class,
            ShipModeGeneratorColumn.values(),
            ShipModeColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {20, 20, 20, 20, 20, 20, 20, 20, 20}, 0)),
    STORE(new TableFlagsBuilder().setKeepsHistory().setIsSmall().build(),
            100,
            0xB,
            StoreRowGenerator.class,
            StoreGeneratorColumn.values(),
            StoreColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {6, 51, 201, 402, 501, 675, 750, 852, 951}, 0)),
    STORE_RETURNS(new TableFlagsBuilder().build(),
            700,
            0x204,
            StoreReturnsRowGenerator.class,
            StoreReturnsGeneratorColumn.values(),
            StoreReturnsColumn.values(),
            new ScalingInfo(4, LINEAR, new int[] {24, 240, 2400, 7200, 24000, 72000, 240000, 720000, 2400000}, 0)),
    STORE_SALES(new TableFlagsBuilder().setIsDateBased().build(),
            900,
            0x204,
            StoreSalesRowGenerator.class,
            StoreSalesGeneratorColumn.values(),
            StoreSalesColumn.values(),
            new ScalingInfo(4, LINEAR, new int[] {24, 240, 2400, 7200, 24000, 72000, 240000, 720000, 2400000}, 0)),
    TIME_DIM(new TableFlagsBuilder().build(),
            0,
            0x03,
            TimeDimRowGenerator.class,
            TimeDimGeneratorColumn.values(),
            TimeDimColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {86400, 86400, 86400, 86400, 86400, 86400, 86400, 86400, 86400}, 0)),
    WAREHOUSE(new TableFlagsBuilder().setIsSmall().build(),
            200,
            0x03,
            WarehouseRowGenerator.class,
            WarehouseGeneratorColumn.values(),
            WarehouseColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {5, 10, 15, 17, 20, 22, 25, 27, 30}, 0)),
    WEB_PAGE(new TableFlagsBuilder().setKeepsHistory().build(),
            250,
            0x0B,
            WebPageRowGenerator.class,
            WebPageGeneratorColumn.values(),
            WebPageColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {30, 100, 1020, 1302, 1500, 1800, 2001, 2301, 2502}, 0)),
    WEB_RETURNS(new TableFlagsBuilder().build(),
            900,
            0x2004,
            WebReturnsRowGenerator.class,
            WebReturnsGeneratorColumn.values(),
            WebReturnsColumn.values(),
            new ScalingInfo(3, LINEAR, new int[] {60, 600, 6000, 18000, 60000, 180000, 600000, 1800000, 6000000}, 0)),
    WEB_SALES(new TableFlagsBuilder().setIsDateBased().build(),
            5,
            0x20008,
            WebSalesRowGenerator.class,
            WebSalesGeneratorColumn.values(),
            WebSalesColumn.values(),
            new ScalingInfo(3, LINEAR, new int[] {60, 600, 6000, 18000, 60000, 180000, 600000, 1800000, 6000000}, 0)),
    WEB_SITE(new TableFlagsBuilder().setKeepsHistory().setIsSmall().build(),
            100,
            0x0B,
            WebSiteRowGenerator.class,
            WebSiteGeneratorColumn.values(),
            WebSiteColumn.values(),
            new ScalingInfo(0, LOGARITHMIC, new int[] {15, 21, 12, 21, 27, 33, 39, 42, 48}, 0)),
    DBGEN_VERSION(new TableFlagsBuilder().build(),
            0,
            0x0,
            DbgenVersionRowGenerator.class,
            DbgenVersionGeneratorColumn.values(),
            DbgenVersionColumn.values(),
            new ScalingInfo(0, STATIC, new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1}, 0)),

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
    private final GeneratorColumn[] generatorColumns;
    private final Column[] columns;
    private final ScalingInfo scalingInfo;
    private Optional<Table> parent = Optional.empty();
    private Optional<Table> child = Optional.empty();

    static {
        // initialize parent and child relationships here because in
        // table constructors can't refer to tables that have not yet been
        // defined
        CATALOG_RETURNS.parent = Optional.of(CATALOG_SALES);
        CATALOG_SALES.child = Optional.of(CATALOG_RETURNS);
        STORE_RETURNS.parent = Optional.of(STORE_SALES);
        STORE_SALES.child = Optional.of(STORE_RETURNS);
        WEB_RETURNS.parent = Optional.of(WEB_SALES);
        WEB_SALES.child = Optional.of(WEB_RETURNS);
    }

    // TODO: This constructor is a stop-gap until all the tables are implemented.  Remove it when it is no longer needed.
    Table()
    {
        this.tableFlags = new TableFlagsBuilder().build();
        this.nullBasisPoints = 0;
        this.notNullBitMap = 0;
        this.rowGeneratorThreadLocal = null;
        this.generatorColumns = new GeneratorColumn[0];
        this.columns = new Column[0];
        this.scalingInfo = new ScalingInfo(0, LINEAR, new int[9], 0);
    }

    Table(TableFlags tableFlags, int nullBasisPoints, long notNullBitMap, Class<? extends RowGenerator> rowGeneratorClass, GeneratorColumn[] generatorColumns, Column[] columns, ScalingInfo scalingInfo)
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
        this.generatorColumns = generatorColumns;
        this.columns = columns;
        this.scalingInfo = scalingInfo;
    }

    public String getName()
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

    public boolean hasChild()
    {
        return child.isPresent();
    }

    public Table getChild()
    {
        return child.get();
    }

    public boolean isChild()
    {
        return parent.isPresent();
    }

    public Table getParent()
    {
        return parent.get();
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

    public GeneratorColumn[] getGeneratorColumns()
    {
        return generatorColumns;
    }

    public ScalingInfo getScalingInfo()
    {
        return scalingInfo;
    }

    public Column[] getColumns()
    {
        return columns;
    }

    public static List<Table> getBaseTables()
    {
        List<Table> allTables = ImmutableList.copyOf(Table.values());
        return allTables.stream()
                .filter(table -> !table.getName().startsWith("s_"))
                .collect(Collectors.toList());
    }

    public static List<Table> getSourceTables()
    {
        List<Table> allTables = ImmutableList.copyOf(Table.values());
        return allTables.stream()
                .filter(table -> table.getName().startsWith("s_"))
                .collect(Collectors.toList());
    }
}
