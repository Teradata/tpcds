package com.teradata.tpcds;

import com.teradata.tpcds.random.RandomNumberStream;

public interface Column
{
    public Table getTable();
    public RandomNumberStream getRandomNumberStream();
}
