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

// This class was derived from the TpchColumnTypes class in the following repo
// https://github.com/airlift/tpch. The license for that class can be found here
// https://www.apache.org/licenses/LICENSE-2.0.
public class ColumnTypes
{
    private ColumnTypes() {}

    public static final ColumnType INTEGER = new ColumnType(ColumnType.Base.INTEGER);
    public static final ColumnType IDENTIFIER = new ColumnType(ColumnType.Base.IDENTIFIER);
    public static final ColumnType DATE = new ColumnType(ColumnType.Base.DATE);
    public static final ColumnType TIME = new ColumnType(ColumnType.Base.TIME);

    public static ColumnType varchar(int precision)
    {
        return new ColumnType(ColumnType.Base.VARCHAR, precision);
    }

    public static ColumnType character(int precision)
    {
        return new ColumnType(ColumnType.Base.CHAR, precision);
    }

    public static ColumnType decimal(int precision, int scale)
    {
        return new ColumnType(ColumnType.Base.DECIMAL, precision, scale);
    }
}
