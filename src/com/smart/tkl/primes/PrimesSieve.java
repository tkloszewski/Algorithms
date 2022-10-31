package com.smart.tkl.primes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimesSieve {

    private final int length;
    private boolean[] sieve;

    public PrimesSieve(int length) {
        this.length = length;
        this.sieve = createSieve();
    }

    public static void main(String[] args) {
        PrimesSieve primesSieve = new PrimesSieve(1000);
        List<Integer> primes = new ArrayList<>();
        for(int i = 2; i <= 1000; i++) {
            if(primesSieve.isPrime(i)) {
               primes.add(i);
            }
        }
        System.out.println("Primes: " + primes);
    }

    public boolean isPrime(int n) {
        if(n % 2 == 0) {
           return n == 2;
        }
        return sieve[n / 2];
    }

    private boolean[] createSieve() {
        boolean[] sieve = new boolean[this.length / 2 + 1];
        Arrays.fill(sieve, true);
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
