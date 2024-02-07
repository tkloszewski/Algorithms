package com.smart.tkl.euler.p58;

import com.smart.tkl.lib.primes.Primes;

public class SpiralPrimes {

    public static void main(String[] args) {
        long length = spiralPrime(8);
        System.out.println("Length: " + length);
    }

    public static long spiralPrime(int N) {
        return spiralPrime(N / 100.0);
    }

    public static long spiralPrime(double th) {
        double ratio = 1.0;
        long pC = 0, k = 1;

        long lastTerm = 1, length = 1;

        while(ratio >= th) {
            for(long i = 1; i <= 4; i++) {
                long term = lastTerm + i * (length + 1);
                if(Primes.isPrime(term)) {
                    pC++;
                }
            }

            lastTerm += 4 * (length + 1);
            length += 2;
            k++;
            long total = 4 * k - 3;
            ratio = ((double)pC)/total;
        }

        return length;
    }
}
