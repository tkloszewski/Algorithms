package com.smart.tkl.lib.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Divisors {

    public static void main(String[] args) {
        System.out.println("Proper divisors: " + listProperDivisors(1491493311000L));

        long time1 = System.currentTimeMillis();
        List<Long> squareDivisors = listSquareDivisors(1491493311000L);
        long time2 = System.currentTimeMillis();
        System.out.println("Square divisors: " + squareDivisors);
        System.out.println("Time for square divisors: " + (time2 - time1));
    }

    public static long sumProperDivisors(long n) {
        List<Long> divisors = listProperDivisors(n);
        return divisors.stream().reduce(0L, Long::sum);
    }

    public static List<Long> listSquareDivisors(long n) {
        List<Long> divisors = listProperDivisors(n);
        List<Long> squareDivisors = new ArrayList<>(divisors.size());
        for(long divisor : divisors) {
            double sqrt = Math.sqrt(divisor);
            if((long)sqrt == sqrt) {
               squareDivisors.add(divisor);
            }
        }
        return squareDivisors;
    }

    public static List<Long> listProperDivisors(long n) {
        if(n == 1) {
            return List.of(1L);
        }

        long number = n;

        List<List<Long>> primeFactorsList = new ArrayList<>();
        for (int num : new int[]{2, 3, 5}) {
            long powValue = 1;
            List<Long> primeFactors = new ArrayList<>();
            while (n % num == 0) {
                n = n / num;
                powValue *= num;
                if(primeFactors.isEmpty()) {
                    primeFactors.add(1L);
                }
                primeFactors.add(powValue);
            }
            if(!primeFactors.isEmpty()) {
                primeFactorsList.add(primeFactors);
            }
        }

        int[] increments = new int[]{4, 2, 4, 2, 4, 6, 2, 6};
        int i = 0;

        for(long p = 7; p * p <= n; p += increments[i], i = (i + 1) % 8) {
            long powValue = 1;
            List<Long> primeFactors = new ArrayList<>();
            while (n % p == 0) {
                n = n / p ;
                powValue *= p;
                if(primeFactors.isEmpty()) {
                    primeFactors.add(1L);
                }
                primeFactors.add(powValue);
            }
            if(!primeFactors.isEmpty()) {
                primeFactorsList.add(primeFactors);
            }
        }

        if(n > 1) {
            primeFactorsList.add(List.of(1L, n));
        }

        return getDivisors(primeFactorsList, number);
    }

    public static List<Long> getDivisors(List<List<Long>> primeFactorsList, long number) {
        List<Long> divisors = new ArrayList<>();
        combineDivisors(primeFactorsList, divisors, 0, 1, Math.sqrt(number), number);
        Collections.sort(divisors);
        return divisors;
    }

    private static void combineDivisors(List<List<Long>> primeFactorsList, List<Long> divisors, int pos, long value, double limit, long number) {
        List<Long> primeFactors = primeFactorsList.get(pos);
        if(pos == primeFactorsList.size() - 1) {
            for(long factor : primeFactors) {
                long divisor = factor * value;
                if(divisor == limit) {
                    divisors.add(divisor);
                }
                else if(divisor < limit){
                    divisors.add(divisor);
                    divisors.add(number / divisor);
                }
                else {
                    break;
                }
            }
        }
        else {
            for(long factor : primeFactors) {
                long newValue = factor * value;
                if(newValue > limit) {
                    return;
                }
                combineDivisors(primeFactorsList, divisors, pos + 1, newValue, limit, number);
            }
        }
    }
}
