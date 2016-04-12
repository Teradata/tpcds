package com.teradata.tpcds;

public interface RowGenerator
{
    TableRow generateRow(long rowNumber, Scaling scaling);
}
