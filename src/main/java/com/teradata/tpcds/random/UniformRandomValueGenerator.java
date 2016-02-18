package com.teradata.tpcds.random;

import com.teradata.tpcds.Date;
import com.teradata.tpcds.Decimal;

import static com.teradata.tpcds.Date.fromJulianDays;
import static com.teradata.tpcds.Date.getDaysInYear;
import static com.teradata.tpcds.Date.toJulianDays;
import static com.teradata.tpcds.random.DistributionType.RETURNS;
import static com.teradata.tpcds.random.DistributionType.SALES;
import static com.teradata.tpcds.random.DistributionType.UNIFORM;
import static java.util.Objects.requireNonNull;

public final class UniformRandomValueGenerator
{
    private static final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ0123456789";

    private UniformRandomValueGenerator()
    {
    }

    public static int generateRandomInt(int min, int max, RandomNumberStream randomNumberStream)
    {
        int result = (int) randomNumberStream.nextRandom(); // truncating long to int copies behavior of c code.
        result %= max - min + 1;
        result += min;
        return result;
    }

    public static long generateRandomKey(long min, long max, RandomNumberStream randomNumberStream)
    {
        int result = (int) randomNumberStream.nextRandom(); // truncating long to int copies behavior of c code
        result %= (int) (max - min + 1);
        result += (int) min;
        return result;
    }

    public static Decimal generateRandomDecimal(Decimal min, Decimal max, Decimal mean, RandomNumberStream randomNumberStream)
    {
        int precision = min.getPrecision() < max.getPrecision() ? min.getPrecision() : max.getPrecision();

        // compute number
        long number = randomNumberStream.nextRandom();
        number %= max.getNumber() - min.getNumber() + 1;
        number += min.getNumber();

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
        int julianDays = toJulianDays(min) + generateRandomInt(0, range, randomNumberStream);

        // get random date based on distribution.
        DistributionType distributionType = UNIFORM; //TODO: pass in or make its own Generator impl
        if (distributionType == SALES || distributionType == RETURNS) {
            // Copying behavior of dsdgen, but unclear what's going on there
            // Date.day represents the day of the month, but for some reason
            // it is interpreted in genrand_date as the day of the year.
            // That means we always start somewhere in January, I guess.
            int dayCount = min.getDay();
            int year = min.getYear();
            int totalWeight = 0;
            int distIndex = 0; //TODO: change depending on sales/returns and leap year vs. not.
            //TODO: Also probably want to pass in the distribution itself, not an index.

            // calculate the sum of all the weights in the range
            for (int i = 0; i < range; i++) {
                totalWeight += CalendarDistribution.getWeight(dayCount, distIndex);
                if (dayCount == getDaysInYear(year)) {
                    year += 1;
                    dayCount = 1;
                }
                else {
                    dayCount += 1;
                }
            }

            // Choose a random int up to totalWeight.
            // Then work backwards to get the first date where the chosen number is
            // greater than or equal to the sum of all weights from min to date.
            int tempWeightSum = generateRandomInt(1, totalWeight, randomNumberStream);
            dayCount = min.getDay();
            julianDays = Date.toJulianDays(min);
            year = min.getYear();
            while (tempWeightSum > 0) {
                tempWeightSum -= CalendarDistribution.getWeight(dayCount, distIndex);
                dayCount += 1;
                julianDays += 1;
                if (dayCount > getDaysInYear(year)) {
                    dayCount = 1;
                    year += 1;
                }
            }
        }

        return fromJulianDays(julianDays);
    }

    public static String generateRandomCharset(String set, int min, int max, RandomNumberStream randomNumberStream)
    {
        requireNonNull(set, "set is null");

        int length = generateRandomInt(min, max, randomNumberStream);
        StringBuilder builder = new StringBuilder();

        // It seems like it would make more sense to make length the loop condition.
        // For some reason dsdgen doesn't do that, and we want the RNG seeds to be the same
        // so we copy the behavior.
        for (int i = 0; i < max; i++) {
            int index = generateRandomInt(0, set.length() - 1, randomNumberStream);
            if (i < length) {
                builder.append(set.charAt(index));
            }
        }
        return builder.toString();
    }

    public static String generateRandomEmail(String first, String last, RandomNumberStream randomNumberStream)
    {
        String domain = TopDomainsDistribution.pickValue(1, 1, randomNumberStream);
        int companyLength = generateRandomInt(10, 20, randomNumberStream);
        String company = generateRandomCharset(ALPHA_NUMERIC, 1, 20, randomNumberStream);
        company = company.substring(0, companyLength);

        return String.format("%s.%s@%s.%s", first, last, company, domain);
    }

    public static String generateRandomIpAddress(RandomNumberStream randomNumberStream)
    {
        int ipSegments[] = new int[4];

        for (int i = 0; i < 4; i++)
            ipSegments[i] = generateRandomInt(1, 255, randomNumberStream);

        return String.format("%d.%d.%d.%d", ipSegments[0], ipSegments[1], ipSegments[2], ipSegments[3]);
    }

    public static String generateRandomUrl(RandomNumberStream randomNumberStream)
    {
        return "http://www.foo.com";  // This is what the c code does. No joke.
    }
}
