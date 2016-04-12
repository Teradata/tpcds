package com.teradata.tpcds;

import static com.google.common.base.Preconditions.checkArgument;

public class Options
{
    private final int scale;

    public Options(int scale) {
        checkArgument(scale > 0 && scale <= 100000, "scale %d is not between 0 and 100000", scale);
        this.scale = scale;
    }

    public int getScale()
    {
        return scale;
    }
}
