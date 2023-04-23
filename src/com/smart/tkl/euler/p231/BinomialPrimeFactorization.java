package com.smart.tkl.euler.p231;

import java.util.Arrays;

public class BinomialPrimeFactorization {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int a = 20000000;
        int b = 15000000;
        long sum = sumOfTerms(a, b);
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long sumOfTerms(int a, int b) {
        long sum = 0;
        int c = a - b;

        boolean[] sieve = new boolean[a / 2 + 1];
        Arrays.fill(sieve, true);
        sieve[0] = false;
        int primeLimit = (int)Math.sqrt(a);
        for(int i = 1; i <= primeLimit; i++) {
            int k = 2 * i + 1;
            if(sieve[k / 2]) {
                for (int j = 3; k * j <= a; j += 2) {
                    int p = k * j;
                    sieve[p / 2] = false;
                }
            }
        }

        for(int i = 0; i < sieve.length; i++) {
            if(sieve[i] || i == 0) {
                long prime = i == 0 ? 2L : 2L * i + 1;
                if(prime <= a) {
                    long pow1 = getPow(a, prime);
                    long pow2 = getPow(b, prime);
                    long pow3 = getPow(c, prime);
                    sum += prime * (pow1 - pow2 - pow3);
                }
            }
        }

        return sum;
    }

    private static long getPow(int n, long prime) {
        long k = 0;
        long pow = prime;
        long div = n / pow;
        while (div > 0) {
            k += div;
            pow *= prime;
            div = n / pow;
        }
        return k;
    }
}
