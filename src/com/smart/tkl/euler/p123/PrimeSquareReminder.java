package com.smart.tkl.euler.p123;

import com.smart.tkl.utils.MathUtils;

import java.util.List;

public class PrimeSquareReminder {

    private final long limit;
    private final int primeLimit;

    public PrimeSquareReminder(long limit) {
        this.limit = limit;
        this.primeLimit = (int)Math.sqrt(limit) * 10;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PrimeSquareReminder primeSquareReminder = new PrimeSquareReminder(10000000000L);
        int n = primeSquareReminder.getPrimeIndex();
        long time2 = System.currentTimeMillis();
        System.out.println("n: " + n);
        System.out.println("The solution took ms: " + (time2 - time1));
    }

    public int getPrimeIndex() {
        boolean[] sieve = MathUtils.primesSieve(primeLimit);
        List<Long> primes = MathUtils.generatePrimesUpTo(primeLimit, sieve);
        int number = (int)Math.sqrt(limit);
        while (!sieve[number]){
            number++;
        }
        int index = 0;
        while (primes.get(index) != number) {
            index++;
        }
        if((index + 1) % 2 == 0) {
            index++;
        }
        index = index - 2;
        long modulo = 0;
        while (modulo <= limit && index < primes.size()) {
            index = index + 2;
            modulo = toModuloSquarePrime(index, primes);
        }
        return modulo > limit ? index + 1: -1;
    }

    private long toModuloSquarePrime(int index, List<Long> primes) {
        long prime = primes.get(index);
        long n = index + 1;
        return MathUtils.moduloMultiply(2 * n, prime, prime * prime);
    }
}
