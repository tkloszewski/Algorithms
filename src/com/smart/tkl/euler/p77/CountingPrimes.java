package com.smart.tkl.euler.p77;

import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountingPrimes {

    private final int limit;

    public CountingPrimes(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        int numOfWays = 1000;
        long time1 = System.currentTimeMillis();
        CountingPrimes countingPrimes = new CountingPrimes(numOfWays);
        int value = countingPrimes.getValue();
        long time2 = System.currentTimeMillis();
        System.out.printf("The first value that can be written in over %d ways is: %d.", numOfWays, value);
        System.out.println("Time in ms: " + (time2 - time1));

        CountingPrimes countingPrimes2 = new CountingPrimes(71);
        long[] allWays = countingPrimes2.countAllWays();
        System.out.println(Arrays.toString(allWays));
    }

    public int getValue() {
        final int primeLimit = 1000;
        boolean[] primesSieve = MathUtils.primesSieve(primeLimit);

        int target = 2;

        while (target <= primeLimit) {
            long[] counter = new long[primeLimit + 1];
            counter[0] = 1;
            for(int i = 2; i < primesSieve.length && i <= target ; i++) {
                if(primesSieve[i]) {
                    for(int j = i; j <= target; j++) {
                        counter[j] += counter[j - i];
                    }
                }
            }
            if(counter[target] > limit) {
               break;
            }
            target++;
        }

        return target;
    }

    public long[] countAllWays() {
        long[] allWays = new long[limit + 1];

        PrimesSieve sieve = new PrimesSieve(limit);
        List<Integer> primes = new ArrayList<>();
        for(int i = 2; i <= limit; i++) {
            if(sieve.isPrime(i)) {
               primes.add(i);
            }
        }

        allWays[0] = 1;
        for(int prime : primes) {
            for(int i = prime; i <= limit; i++) {
                allWays[i] = allWays[i] + allWays[i - prime];
            }
        }

        return allWays;
    }
}
