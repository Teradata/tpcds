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
    // of digits.  We leave out the scale field because it's never used, and the c implementation
    // was buggy.

    private final int precision;
    private final long number;

    public Decimal(int precision, long number)
    {
        checkArgument(precision >= 0, "precision must be greater than or equal to zero");
        this.precision = precision;
        this.number = number;
    }

    public int getPrecision()
    {
        return precision;
    }

    public long getNumber()
    {
        return number;
    }

    @Override
    public String toString()
    {
        // this loses all of the benefit of having exact numeric types
        // but it's what the C code does, so we have to follow it.
        // In particular this copies the behavior of print_decimal in print.c
        // the C code has a different function called dectostr in decimal.c that
        // does a proper string representatio but it never gets called.
        double temp = number;
        for (int i = 0; i < precision; i++) {
            temp /= 10.0;
        }

        return String.format("%." + precision + "f", temp);
    }
}
