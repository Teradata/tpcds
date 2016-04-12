package com.teradata.tpcds;

import com.teradata.tpcds.TableFlags.TableFlagsBuilder;

public enum Table
{
    CALL_CENTER(new TableFlagsBuilder().setIsSmall().setKeepsHistory().build()),
    CATALOG_PAGE,
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
    S_ZIPG,

    //PSEUDO TABLES from here on; used in hierarchies
    ITEM_BRAND(new TableFlagsBuilder().setIsPseudoTable().build()),
    ITEM_CLASS(new TableFlagsBuilder().setIsPseudoTable().build()),
    ITEM_CATEGORY(new TableFlagsBuilder().setIsPseudoTable().build()),
    DIVISIONS(new TableFlagsBuilder().setIsPseudoTable().build()),
    COMPANY(new TableFlagsBuilder().setIsPseudoTable().build()),
    CONCURRENT_WEB_SITES(new TableFlagsBuilder().setIsPseudoTable().build()),
    ACTIVE_CITIES(new TableFlagsBuilder().setIsPseudoTable().build()),
    ACTIVE_COUNTIES(new TableFlagsBuilder().setIsPseudoTable().build()),
    ACTIVE_STATES(new TableFlagsBuilder().setIsPseudoTable().build());

    private TableFlags tableFlags;

    Table(TableFlags tableFlags)
    {
        this.tableFlags = tableFlags;
    }

    Table()
    {
        this.tableFlags = new TableFlagsBuilder().build();
    }

    public boolean keepsHistory()
    {
        return tableFlags.keepsHistory();
    }

    public boolean isSmall()
    {
        return tableFlags.isSmall();
    }

    public boolean isPseudoTable() {
        return tableFlags.isPseudoTable();
    }
}
