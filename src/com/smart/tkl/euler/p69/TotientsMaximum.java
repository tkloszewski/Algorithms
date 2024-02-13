package com.smart.tkl.euler.p69;

import java.util.List;

public class TotientsMaximum {

    private static List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17, 19,
            23, 29, 31, 37, 41, 43, 47, 53, 57);

    public static void main(String[] args) {
        long limit = 1000000000000000000L;
        System.out.println(findValue(limit));
    }

    private static long findValue(long limit) {
        long value = 1;

        for(int prime : primes) {
            long newValue = value * (long)prime;
            if(newValue >= limit || newValue < 0) {
                return value;
            }
            value = newValue;
        }

        return value;
    }
}
