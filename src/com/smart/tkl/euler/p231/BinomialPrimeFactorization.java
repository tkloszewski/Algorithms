package com.smart.tkl.euler.p231;

import com.smart.tkl.primes.PrimesSieve;
import java.util.List;

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
        List<Long> primes = PrimesSieve.generatePrimesUpTo(a);
        for(long prime : primes) {
            long pow1 = getPow(a, prime);
            long pow2 = getPow(b, prime);
            long pow3 = getPow(c, prime);
            sum += prime * (pow1 - pow2 - pow3);
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
