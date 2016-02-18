package com.teradata.tpcds.random;

import com.teradata.tpcds.Date;
import com.teradata.tpcds.Decimal;

import static com.teradata.tpcds.Date.fromJulianDays;
import static com.teradata.tpcds.Date.toJulianDays;

public final class ExponentialRandomValueGenerator
{
    private ExponentialRandomValueGenerator()
    {
    }

    public static int generateRandomInt(int min, int max, RandomNumberStream randomNumberStream)
    {
        int range = max - min + 1;
        double doubleResult = 0;
        for (int i = 0; i < 12; i++) {
            doubleResult += randomNumberStream.nextRandomDouble() - 0.5;
        }
        return min + (int) (range * doubleResult);
    }

    public static long generateRandomKey(long min, long max, RandomNumberStream randomNumberStream)
    {
        double doubleResult = 0;
        for (int i = 0; i < 12; i++)
            doubleResult += (double) (randomNumberStream.nextRandom() / Integer.MAX_VALUE) - 0.5;
        return (int) min + (int) ((max - min + 1) * doubleResult); // truncating long to int copies behavior of c code
    }

    public static Decimal generateRandomDecimal(Decimal min, Decimal max, Decimal mean, RandomNumberStream randomNumberStream)
    {
        // compute precision
        int precision = min.getPrecision() < max.getPrecision() ? min.getPrecision() : max.getPrecision();

        // compute number
        double doubleResult = 0;
        for (int i = 0; i < 12; i++) {
            doubleResult /= 2.0;
            doubleResult += (double) randomNumberStream.nextRandom() / Integer.MAX_VALUE - 0.5;
        }

        long number = mean.getNumber() + (int) ((max.getNumber() - min.getNumber() + 1) * doubleResult);

        // compute scale
        int scale = 0;
        long n = number;
        while (n > 10) {
            n /= 10;
            scale += 1;
        }
        return new Decimal(precision, scale, number);
    }

    public static Date generateRandomDate(Date min, Date max, Date mean, RandomNumberStream randomNumberStream)
    {
        int range = toJulianDays(max) - toJulianDays(min);
        int days = toJulianDays(min) + generateRandomInt(0, range, randomNumberStream);
        return fromJulianDays(days);
    }

    public static String generateRandomCharset(String set, int min, int max, RandomNumberStream randomNumberStream)
    {
        throw new NotApplicableException("Method not needed for ExponentialRandomValueGenerator");
    }

    public static String generateRandomEmail(String first, String last, RandomNumberStream randomNumberStream)
    {
        throw new NotApplicableException("Method not needed for ExponentialRandomValueGenerator");
    }

    public static String generateRandomIpAddress(RandomNumberStream randomNumberStream)
    {
        throw new NotApplicableException("Method not needed for ExponentialRandomValueGenerator");
    }

    public static String generateRandomUrl(RandomNumberStream randomNumberStream)
    {
        throw new NotApplicableException("Method not needed for ExponentialRandomValueGenerator");
    }
}
