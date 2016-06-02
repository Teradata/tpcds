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
    public static final Decimal ZERO = new Decimal(0, 2);
    public static final Decimal ONE_HALF = new Decimal(50, 2);
    public static final Decimal NINE_PERCENT = new Decimal(9, 2);
    public static final Decimal ONE_HUNDRED = new Decimal(10000, 2);
    public static final Decimal ONE = new Decimal(100, 2);

    // XXX: Definitions of precision and scale are reversed. This was done to
    // make it easier to follow the C code, which reverses the definitions.  Here,
    // precision means the number of decimal places and scale means the total number
    // of digits.  We leave out the scale field because it's never used, and the C implementation
    // was buggy.
    private final int precision;
    private final long number;

    public Decimal(long number, int precision)
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

    public static Decimal add(Decimal decimal1, Decimal decimal2)
    {
        int precision = decimal1.precision > decimal2.precision ? decimal1.precision : decimal2.precision;
        long number = decimal1.number + decimal2.number;  // This is not mathematically correct when the precisions aren't the same, but it's what the C code does
        return new Decimal(number, precision);
    }

    public static Decimal subtract(Decimal decimal1, Decimal decimal2)
    {
        int precision = decimal1.precision > decimal2.precision ? decimal1.precision : decimal2.precision;
        long number = decimal1.number - decimal2.number;  // again following C code
        return new Decimal(number, precision);
    }

    public static Decimal multiply(Decimal decimal1, Decimal decimal2)
    {
        int precision = decimal1.precision > decimal2.precision ? decimal1.precision : decimal2.precision;
        long number = decimal1.number * decimal2.number;
        for (int i = decimal1.precision + decimal2.precision; i > precision; i--) {
            number /= 10;  // Always round down, I guess
        }
        return new Decimal(number, precision);
    }

    public static Decimal divide(Decimal decimal1, Decimal decimal2)
    {
        float f1 = (float) decimal1.number;
        int precision = decimal1.precision > decimal2.precision ? decimal1.precision : decimal2.precision;
        for (int i = decimal1.precision; i < precision; i++) {
            f1 *= 10.0;
        }

        for (int i = 0; i < precision; i++) {
            f1 *= 10.0;
        }

        float f2 = (float) decimal2.number;
        for (int i = decimal2.precision; i < precision; i++) {
            f2 *= 10.0;
        }

        int number = (int) (f1 / f2);
        return new Decimal(number, precision);
    }

    public static Decimal negate(Decimal decimal)
    {
        return new Decimal(decimal.number * -1, decimal.precision);
    }

    public static Decimal fromInteger(int from)
    {
        return new Decimal(from, 0);
    }

    @Override
    public String toString()
    {
        // This loses all of the benefit of having exact numeric types
        // but it's what the C code does, so we have to follow it.
        // In particular this copies the behavior of print_decimal in print.c.
        // The C code has a different function called dectostr in decimal.c that
        // does a proper string representation but it never gets called.
        double temp = number;
        for (int i = 0; i < precision; i++) {
            temp /= 10.0;
        }

        return String.format("%." + precision + "f", temp);
    }
}
