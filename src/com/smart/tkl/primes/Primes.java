package com.smart.tkl.primes;

import com.smart.tkl.utils.Coefficients;
import com.smart.tkl.utils.MathUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class Primes {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        System.out.println("Is prime: " + isPrime(546591868459071613L));
        long time2 = System.currentTimeMillis();
        System.out.println("Prime test took ms: " + (time2 - time1));

        Coefficients coefficients = MathUtils.GCDExtended(12, 7);
        System.out.println("Coefficients: " + coefficients);
    }

    public static boolean isPrime(long n) {
        if(n % 2 == 0) {
           return n == 2;
        }

        if(n < 1000) {
           return MathUtils.isPrime(n);
        }

        List<BigInteger> bases = getBases(n).stream().map(a -> BigInteger.valueOf(a)).collect(Collectors.toList());

        long d = n - 1;
        int s = 0;
        while (d % 2 == 0) {
            d = d / 2;
            s++;
        }

        BigInteger nBg = BigInteger.valueOf(n);
        BigInteger minusOneBgN = BigInteger.valueOf(n - 1);
        BigInteger dBg = BigInteger.valueOf(d);

        for(BigInteger a : bases) {
            BigInteger x = a.modPow(dBg, nBg);
            BigInteger y = BigInteger.ZERO;
            for(int i = 0; i < s; i++) {
                y = x.modPow(BigInteger.TWO, nBg);
                if(y.equals(BigInteger.ONE) && !x.equals(BigInteger.ONE) && !x.equals(minusOneBgN)) {
                   return false;
                }
                x = y;
            }
            if(!y.equals(BigInteger.ONE)) {
               return false;
            }
        }

        return true;
    }

    private static List<Long> getBases(long n) {
        if(n < 2047) {
           return List.of(2L);
        }
        if(n < 1373653) {
           return List.of(2L, 3L);
        }
        if(n < 9080191L) {
           return List.of(31L, 73L);
        }
        if(n < 25326001) {
            return List.of(2L, 3L, 5L);
        }
        if(n < 3215031751L) {
            return List.of(2L, 3L, 5L, 7L);
        }
        if(n < 4759123141L) {
            return List.of(2L, 7L, 61L);
        }
        if(n < 1122004669633L) {
            return List.of(2L, 13L, 23L, 1662803L);
        }
        if(n < 2152302898747L) {
            return List.of(2L, 3L, 5L, 7L, 11L);
        }
        if(n < 3474749660383L) {
            return List.of(2L, 3L, 5L, 7L, 11L, 13L);
        }
        if(n < 341550071728321L) {
            return List.of(2L, 3L, 5L, 7L, 11L, 13L, 17L);
        }
        if(n < 3825123056546413051L) {
            return List.of(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L);
        }
        return List.of(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L);
    }
}
