package com.teradata.tpcds;

public class TableFlags
{
    private final boolean keepsHistory; // FL_TYPE_2 in the c code.   this dimension keeps history -- rowcount shows unique entities (not including revisions).
    private final boolean isSmall;      // this table has low rowcount; used by Address.java
    private final boolean isPseudoTable;

    public TableFlags(boolean keepsHistory, boolean isSmall, boolean isPseudoTable)
    {
        this.keepsHistory = keepsHistory;
        this.isSmall = isSmall;
        this.isPseudoTable = isPseudoTable;
    }

    public boolean keepsHistory() {
        return keepsHistory;
    }

    public boolean isSmall() {
        return isSmall;
    }

    public boolean isPseudoTable()
    {
        return isPseudoTable;
    }

    public static class TableFlagsBuilder
    {
        private boolean keepsHistory = false;
        private boolean isSmall = false;
        private boolean isPseudoTable;

        public TableFlagsBuilder() {}

        public TableFlagsBuilder setKeepsHistory() {
            this.keepsHistory = true;
            return this;
        }

        public TableFlagsBuilder setIsSmall() {
            this.isSmall = true;
            return this;
        }

        public TableFlagsBuilder setIsPseudoTable() {
            this.isPseudoTable = true;
            return this;
        }

        public TableFlags build() {
            return new TableFlags(keepsHistory, isSmall, false);
        }
    }

}
