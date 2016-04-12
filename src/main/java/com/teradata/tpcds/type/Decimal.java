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
