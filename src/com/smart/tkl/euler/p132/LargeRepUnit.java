package com.smart.tkl.euler.p132;

import com.smart.tkl.lib.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class LargeRepUnit {

    private final int k;
    private final int primesCount;

    public LargeRepUnit(int k, int primesCount) {
        this.k = k;
        this.primesCount = primesCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        LargeRepUnit largeRepUnit = new LargeRepUnit(1000000000, 40);
        long sum = largeRepUnit.getSumOfPrimes();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum = " + sum);
        System.out.println("Solution took: " + (time2 - time1));
    }

    public long getSumOfPrimes() {
        //generate sufficient number of primes
        int primesSize = primesCount * 10;
        int lastIndex = 0;
        List<Long> repUnitPrimeFactors = new ArrayList<>();

        while (repUnitPrimeFactors.size() < primesCount) {
            List<Long> primes = MathUtils.generatePrimeList(primesSize);

            for(int i = lastIndex; i < primes.size(); i++) {
                Long prime = primes.get(i);
                long divisor = prime * 9;
                long remainder = MathUtils.moduloPowerLong(10, k, divisor);
                if(remainder == 1) {
                    repUnitPrimeFactors.add(prime);
                    if(repUnitPrimeFactors.size() == primesCount) {
                       break;
                    }
                }
            }

            lastIndex = primesSize;
            primesSize *= 2;
        }
        return repUnitPrimeFactors.stream().reduce(0L, Long::sum);
    }
}
