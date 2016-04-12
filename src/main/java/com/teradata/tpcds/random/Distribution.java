package com.teradata.tpcds.random;


//TODO: ultimately these will all be instance methods, or they'll have to get distributions passed in.
// until all that's worked out though, this makes for an easier stand in.
public abstract class Distribution
{
    public static String pickRandomValue(int valueSet, int weightSet, RandomNumberStream randomNumberStream)
    {
        //TODO: implement
        return "foo";
    }

    public static int getWeight(int index, int weightSet)
    {
        //TODO: implement
        return 0;
    }

    public static int getSize()
    {
        //TODO: implement
        return 0;
    }

    public static String getMemberString(int valueSet, int weightSet)
    {
        //TODO: implement.  Also rename to something clearer, e.g. getValueForWeight
        return "foo";
    }

    public static int getMemberInt(int valueSet, int weightSet)
    {
        //TODO: implement.  Also rename to something clearer, e.g. getValueForWeight
        return 0;
    }
}
