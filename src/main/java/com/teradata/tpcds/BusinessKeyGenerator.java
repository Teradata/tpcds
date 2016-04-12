package com.teradata.tpcds;

public final class BusinessKeyGenerator
{
    private BusinessKeyGenerator() {}

    private static final String BUSINESS_KEY_CHARS = "ABCDEFGHIJKLMNOP"; // 16 possible characters for the key

    public static String makeBusinessKey(long primary)
    {
        String keyPart1 = longTo8CharString(primary >> 32); // create string based on the upper 32 bits
        String keyPart2 = longTo8CharString(primary); // create string based on the lower 32 bits
        return keyPart1 + keyPart2;
    }

    public static String longTo8CharString(long value)
    {
        StringBuilder builder = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int charIndex = (int) (value & 0xF); // The lower 4 bits of value, a number 0-15
            builder.append(BUSINESS_KEY_CHARS.charAt(charIndex));
            value >>= 4;
        }
        return builder.toString();
    }
}
