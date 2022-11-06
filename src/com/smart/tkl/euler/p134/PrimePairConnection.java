package com.smart.tkl.euler.p134;

import com.smart.tkl.primes.PrimesSieve;
import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class PrimePairConnection {

    private final int primeLimit;

    public PrimePairConnection(int primeLimit) {
        this.primeLimit = primeLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum1 = new PrimePairConnection(1000000).sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum1 = " + sum1);
        System.out.println("Solution took: " + (time2 - time1));
    }

    public long sum() {
        long sum = 0;
        List<Long> primes = getPrimes();
        for(int i = 0; i < primes.size() - 1; i++) {
            long p1 = primes.get(i);
            long p2 = primes.get(i + 1);
            long n = resolveNumber(p1, p2);
            sum += n;
        }
        return sum;
    }

    public long sum2() {
        long sum = 0;
        List<Long> primes = getPrimes();
        for(int i = 0; i < primes.size() - 1; i++) {
            long p1 = primes.get(i);
            long p2 = primes.get(i + 1);
            long n = resolveNumber2(p1, p2);
            sum += n;
        }
        return sum;
    }

    private List<Long> getPrimes() {
        List<Long> primes = new ArrayList<>();
        PrimesSieve primesSieve = new PrimesSieve(this.primeLimit);
        for(int i = 5; i <= primeLimit; i += 2) {
            if(primesSieve.isPrime(i)) {
               primes.add((long)i);
            }
        }
        //add last prime
        long lastPrime = primeLimit + 1;
        while (!MathUtils.isPrime(lastPrime)) {
           lastPrime++;
        }
        primes.add(lastPrime);
        return primes;
    }

    private long resolveNumber(long p1, long p2) {
        int k = (int)Math.log10(p1);
        long base = (long)Math.pow(10, k + 1);
        long inverseModuloBase = inverseModulo(base % p2, p2);
        long x = moduloMultiply(p2 - p1, inverseModuloBase, p2);
        return x * base + p1;
    }

    private static long resolveNumber2(long p1, long p2) {
        int k = (int)Math.log10(p1);
        long base = (long)Math.pow(10, k + 1);
        long a = p1;
        int x = 0;
        while (a % p2 != 0) {
            a = (a + base) % p2;
            x++;
        }
        return x * base + p1;
    }

    private static long moduloMultiply(long a, long b, long p) {
        return MathUtils.moduloMultiplyLong(a, b, p);
    }

    private static long inverseModulo(long a, long p) {
        return MathUtils.moduloPowerLong(a, p - 2, p);
    }
}
