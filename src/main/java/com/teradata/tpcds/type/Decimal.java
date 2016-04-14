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

package com.teradata.tpcds.type;

import static com.google.common.base.Preconditions.checkArgument;

public class Decimal
{
    // XXX: Definitions of precision and scale are reversed. This was done to
    // make it easier to follow the C code, which reverses the definitions.  Here,
    // precision means the number of decimal places and scale means the total number
    // of digits.
    private final int precision;
    private final int scale;
    private final long number;

    public Decimal(int precision, int scale, long number)
    {
        checkArgument(scale > 0, "scale must be greater than zero");
        checkArgument(precision >= 0, "precision must be greater than or equal to zero");
        checkArgument(precision <= scale, "precision must be less than or equal to scale");
        this.precision = precision;
        this.scale = scale;
        this.number = number;
    }

    public int getPrecision()
    {
        return precision;
    }

    public int getScale()
    {
        return scale;
    }

    public long getNumber()
    {
        return number;
    }
}
