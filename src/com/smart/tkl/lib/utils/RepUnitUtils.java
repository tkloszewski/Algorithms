package com.smart.tkl.lib.utils;

public class RepUnitUtils {

    public static boolean isDecimalPrimeFactor(long prime) {
        long n = prime - 1;
        while (n % 2 == 0) {
            n = n / 2;
        }
        while (n % 5 == 0) {
            n = n / 5;
        }
        long pow = (prime - 1) / n;
        long modPow = tenPow(pow, prime);
        return modPow == 1;
    }

    public static long tenPow(long pow, long prime) {
        long result = 1;

        long a = 10;
        while (pow > 0) {
            if((pow & 1) == 1){
                result = (a * result) % prime;
            }
            a = (a * a) % prime;
            pow = pow >> 1;
        }

        return result;
    }
}
