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

public class TableFlags
{
    private final boolean keepsHistory; // FL_TYPE_2 in the c code.   this dimension keeps history -- rowcount shows unique entities (not including revisions).
    private final boolean isSmall;      // this table has low rowcount; used by Address.java

    public TableFlags(boolean keepsHistory, boolean isSmall)
    {
        this.keepsHistory = keepsHistory;
        this.isSmall = isSmall;
    }

    public boolean keepsHistory()
    {
        return keepsHistory;
    }

    public boolean isSmall()
    {
        return isSmall;
    }

    public static class TableFlagsBuilder
    {
        private boolean keepsHistory = false;
        private boolean isSmall = false;

        public TableFlagsBuilder()
        {
        }

        public TableFlagsBuilder setKeepsHistory()
        {
            this.keepsHistory = true;
            return this;
        }

        public TableFlagsBuilder setIsSmall()
        {
            this.isSmall = true;
            return this;
        }

        public TableFlags build()
        {
            return new TableFlags(keepsHistory, isSmall);
        }
    }
}
