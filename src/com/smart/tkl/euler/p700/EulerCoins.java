package com.smart.tkl.euler.p700;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EulerCoins {

    public static void main(String[] args) {
        long a = 1504170715041707L, mod = 4503599627370517L;
        long time1 = System.currentTimeMillis();
        BigInteger sum = getEulerCoinsSum(a, mod);
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static BigInteger getEulerCoinsSum(long a, long mod) {
        BigInteger result = BigInteger.ZERO;
        List<Long> eulerCoins = getEulerCoins(a, mod);
        for(long eulerCoin : eulerCoins) {
            result = result.add(BigInteger.valueOf(eulerCoin));
        }
        return result;
    }

    private static List<Long> getEulerCoins(long a, long mod) {
        List<Long> result = new ArrayList<>();
        result.add(a);

        long last = a, first = a, min = a;
        while (min != 1) {
            long k = (mod - last) / first;
            long prevLast = last;
            last = last + k * first;
            first = (prevLast + (k + 1) * first) % mod;
            if(first < min) {
               result.add(first);
               min = first;
            }
        }
        return result;
    }
}
