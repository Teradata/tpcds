package com.teradata.tpcds.random;

import static com.google.common.base.Preconditions.checkArgument;

public class RandomNumberStream
{
    private static final int MULTIPLIER = 16807;
    private static final int QUOTIENT = 127773;   // the quotient MAX_INT / MULTIPLIER
    private static final int REMAINDER = 2836;    // the remainder MAX_INT % MULTIPLIER

    private long seed = 0;
    private final long initialSeed = 0;
    private int seedsUsed = 0;
    private final int seedsPerRow;
    private long totalSeedsUsed = 0;

    public RandomNumberStream(int seedsPerRow)
    {
        checkArgument(seedsPerRow >= 0, "seedsPerRow must be >=0");
        this.seedsPerRow = seedsPerRow;
    }

    // https://en.wikipedia.org/wiki/Lehmer_random_number_generator
    public long nextRandom()
    {
        long nextSeed = seed;
        long divisionResult = nextSeed / QUOTIENT;
        long modResult = nextSeed % QUOTIENT;
        nextSeed = MULTIPLIER * modResult - divisionResult * REMAINDER;
        if (nextSeed < 0) {
            nextSeed += Integer.MAX_VALUE;
        }

        seed = nextSeed;
        seedsUsed += 1;
        totalSeedsUsed += 1;
        return seed;
    }

    public double nextRandomDouble()
    {
        return (double) this.nextRandom() / (double) Integer.MAX_VALUE;
    }

    public void skipRandom(long numberOfValuesToSkip)
    {
        totalSeedsUsed = numberOfValuesToSkip;
        long nextSeed = initialSeed;
        long multiplier = MULTIPLIER;
        while (numberOfValuesToSkip > 0) {
            if (numberOfValuesToSkip % 2 != 0) { // n is odd
                nextSeed = multiplier * nextSeed - Integer.MAX_VALUE;
            }
            numberOfValuesToSkip = numberOfValuesToSkip / 2;
            multiplier = (multiplier * multiplier) % Integer.MAX_VALUE;
        }
        seed = nextSeed;
    }

    public void resetSeed()
    {
        seed = initialSeed;
    }
}
