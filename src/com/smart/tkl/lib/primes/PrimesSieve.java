package com.smart.tkl.lib.primes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimesSieve {

    private final int length;
    private final boolean[] sieve;

    public PrimesSieve(int length) {
        this.length = length;
        this.sieve = createSieve();
    }

    public int getLength() {
        return length;
    }

    public boolean isPrime(int n) {
        if((n & 1) == 0) {
           return n == 2;
        }
        return sieve[n / 2];
    }

    public static List<Long> generatePrimesUpTo(int n) {
        int notPrimesCount = n / 2;

        boolean[] sieve = new boolean[n / 2 + 1];
        Arrays.fill(sieve, true);
        sieve[0] = false;
        int primeLimit = (int)Math.sqrt(n);
        for(int i = 1; i <= primeLimit; i++) {
            int k = 2 * i + 1;
            if(sieve[k / 2]) {
                for (int j = 3; k * j <= n; j += 2) {
                    int p = k * j;
                    if (sieve[p / 2]) {
                        sieve[p / 2] = false;
                        notPrimesCount++;
                    }
                }
            }
        }

        int primesCount = n - notPrimesCount;
        List<Long> primes = new ArrayList<>(primesCount);

        primes.add(2L);

        for(int i = 1; i < sieve.length; i++) {
            if(sieve[i]) {
               long p = 2L * i + 1;
               if(p <= n) {
                   primes.add(p);
               }
            }
        }

        return primes;
    }

    private boolean[] createSieve() {
        boolean[] sieve = new boolean[this.length / 2 + 1];
        Arrays.fill(sieve, true);
        sieve[0] = false;
        int primeLimit = (int)Math.sqrt(this.length);
        for(int i = 1; i <= primeLimit; i++) {
            int k = 2 * i + 1;
            if(sieve[k / 2]) {
                for (int j = 3; k * j <= this.length; j += 2) {
                     int p = k * j;
                     sieve[p / 2] = false;
                }
            }
        }
        return sieve;
    }
}
