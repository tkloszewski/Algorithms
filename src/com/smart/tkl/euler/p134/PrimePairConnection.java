package com.smart.tkl.euler.p134;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.primes.PrimesRangeSieve;
import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimePairConnection {

    private final int leftLimit;
    private final int rightLimit;

    public PrimePairConnection(int leftLimit, int rightLimit) {
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        BigInteger sum1 = new PrimePairConnection(999000000,1000000000).sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum1 = " + sum1);
        System.out.println("Solution took: " + (time2 - time1));
    }

    public BigInteger sum() {
        BigInteger sum = BigInteger.ZERO;
        List<Long> primes = getPrimes();
        for(int i = 0; i < primes.size() - 1; i++) {
            long p1 = primes.get(i);
            long p2 = primes.get(i + 1);
            BigInteger n = resolveNumber(p1, p2);
            sum = sum.add(n);
        }
        return sum;
    }

    private List<Long> getPrimes() {
        List<Long> primes = new ArrayList<>();
        PrimesRangeSieve primesRangeSieve = new PrimesRangeSieve(leftLimit, rightLimit);

        for(int i = leftLimit; i <= rightLimit; i++) {
            if(primesRangeSieve.isPrime(i)) {
               primes.add((long)i);
            }
        }

        //add last prime
        long lastPrime = rightLimit + 1;
        while (!Primes.isPrime(lastPrime)) {
           lastPrime++;
        }
        primes.add(lastPrime);

        return primes;
    }

    private BigInteger resolveNumber(long p1, long p2) {
        int k = (int)Math.log10(p1);
        long base = (long)Math.pow(10, k + 1);
        long inverseModuloBase = inverseModulo(base % p2, p2);
        long x = moduloMultiply(p2 - p1, inverseModuloBase, p2);
        return BigInteger.valueOf(x).multiply(BigInteger.valueOf(base)).add(BigInteger.valueOf(p1));
    }

    private static long moduloMultiply(long a, long b, long p) {
        return MathUtils.moduloMultiplyLong(a, b, p);
    }

    private static long inverseModulo(long a, long p) {
        return MathUtils.moduloPowerLong(a, p - 2, p);
    }
}
