package com.smart.tkl.euler.p87;

import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PrimePowerTriples {

    private final int upperBound;

    public PrimePowerTriples(int upperBound) {
        this.upperBound = upperBound;
    }

    public static void main(String[] args) {
        int upperBound = 10000000;
        PrimePowerTriples primePowerTriples = new PrimePowerTriples(upperBound);
        long time1 = System.currentTimeMillis();
        int[] counts = primePowerTriples.getCountTab();
        int count = counts[upperBound];
        long time2 = System.currentTimeMillis();
        System.out.println("Prime power count: " + count + ". Time in ms: " + (time2 - time1));
    }

    public int count() {
        return getPowerSums().size();
    }

    public int[] getCountTab() {
        int[] result = new int[upperBound + 1];
        Set<Long> powerSums = getPowerSums();
        int count = 0;
        int lastValue = 1;

        for(long powerSum : powerSums) {
            for(int i = lastValue; i < powerSum; i++) {
                result[i] = count;
            }
            count++;
            lastValue = (int)powerSum;
        }

        for(int i = lastValue; i <= upperBound; i++) {
            result[i] = count;
        }

        return result;
    }

    public Set<Long> getPowerSums() {
        int primeLimit = (int)Math.sqrt(upperBound);
        List<Long> primes = PrimesSieve.generatePrimesUpTo(primeLimit);
        Set<Long> powerSums = new TreeSet<>();

        for(long prime1 : primes) {
            long powerFour = prime1 * prime1 * prime1 * prime1;
            if(powerFour >= upperBound) {
               break;
            }
            for(long prime2 : primes) {
                long powerThree = prime2 * prime2 * prime2;
                if(powerFour + powerThree >= upperBound) {
                   break;
                }
                for (long prime3 : primes) {
                    long powerTwo = prime3 * prime3;
                    if(powerFour + powerThree + powerTwo >= upperBound) {
                       break;
                    }
                    powerSums.add(powerFour + powerThree + powerTwo);
                }
            }
        }

        return powerSums;
    }
}
