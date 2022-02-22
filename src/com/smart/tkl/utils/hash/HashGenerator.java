package com.smart.tkl.utils.hash;

public class HashGenerator {

    private static final long R = 1779033703;

    public static long toDigitHash(long n) {
        long result = 1;
        while (n > 0) {
            long digit = n % 10;
            result *= (R + 2 * digit);
            n = n / 10;
        }
        return result;
    }

}
