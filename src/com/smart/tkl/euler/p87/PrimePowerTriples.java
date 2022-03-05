package com.smart.tkl.euler.p87;

import com.smart.tkl.utils.MathUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrimePowerTriples {

    private final int upperBound;

    public PrimePowerTriples(int upperBound) {
        this.upperBound = upperBound;
    }

    public static void main(String[] args) {
        PrimePowerTriples primePowerTriples = new PrimePowerTriples(50000000);
        long time1 = System.currentTimeMillis();
        int count = primePowerTriples.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Prime power count: " + count + ". Time in ms: " + (time2 - time1));
    }

    public int count() {
        int primeLimit = (int)Math.sqrt(upperBound);
        List<Long> primes = MathUtils.generatePrimesUpTo(primeLimit);
        Set<Long> powerSums = new HashSet<>();

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

        return powerSums.size();
    }
}
