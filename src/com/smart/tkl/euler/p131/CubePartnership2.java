package com.smart.tkl.euler.p131;

import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CubePartnership2 {

    private final long primeLimit;
    private final List<Long> primes;

    public CubePartnership2(long primeLimit) {
        this.primeLimit = primeLimit;
        this.primes = generatePrimes();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long primeLimit = 25 * (long)Math.pow(10, 12);
        CubePartnership2 cubePartnership = new CubePartnership2(primeLimit);

        int T = 100000;
        Random random = new Random();
        for(int i = 0; i < T; i++) {
            long L = Math.abs(random.nextLong()) % primeLimit;
            cubePartnership.countPrimes(L);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));

    }

    public int countPrimes(long limit) {
        return search(primes, limit);
    }

    private List<Long> generatePrimes() {
        int maxI = (int)(Math.sqrt(primeLimit));

        List<Long> cubanPrimes = new ArrayList<>(maxI);
        List<Long> primes = PrimesSieve.generatePrimesUpTo(maxI);

        boolean[] sieve = new boolean[(int)maxI + 1];
        Arrays.fill(sieve, true);

        for(int k = 2; k < primes.size(); k++) {
            long prime = primes.get(k);
            List<Long> solutions = QuadraticCongruenceSolver.solveForOddPrime(3, 3, 1, prime);
            for(long x : solutions) {
                int j = (int)x;
                while (j <= maxI) {
                    long value = 3L * j * (j + 1) + 1;
                    if(value != prime) {
                        sieve[j] = false;
                    }
                    j += (int) prime;
                }
            }
        }

        for(int j = 1; j < sieve.length; j++) {
            long value = 3L * j * (j + 1) + 1;
            if(value >= primeLimit) {
               break;
            }
            if(sieve[j]) {
                cubanPrimes.add(value);
            }
        }

        return cubanPrimes;
    }

    private static int search(List<Long> primes, long primeLimit) {
        if(primes.isEmpty()) {
            return 0;
        }
        return search(primes, primeLimit, 0, primes.size() - 1);
    }

    private static int search(List<Long> primes, long primeLimit, int begin, int end) {
        long beginValue = primes.get(begin);
        long endValue = primes.get(end);

        if(primeLimit <= beginValue || begin == end) {
            return begin;
        }
        else if(primeLimit > endValue) {
            return end + 1;
        }
        else if(primeLimit == endValue) {
            return end;
        }

        int middle = (begin + end) / 2;
        long middleValue = primes.get(middle);
        if(primeLimit <= middleValue) {
            return search(primes, primeLimit, begin, middle);
        }
        else {
            return search(primes, primeLimit, middle + 1, end);
        }

    }
}
