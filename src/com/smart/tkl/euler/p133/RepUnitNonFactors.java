package com.smart.tkl.euler.p133;

import com.smart.tkl.utils.MathUtils;
import com.smart.tkl.utils.PrimeFactor;

import java.util.List;

public class RepUnitNonFactors {

    private final int primeLimit;

    public RepUnitNonFactors(int primeLimit) {
        this.primeLimit = primeLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        RepUnitNonFactors repUnitNonFactors = new RepUnitNonFactors(100000);
        long sum = repUnitNonFactors.getSumOfNonFactors();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum of non factors: " + sum);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public long getSumOfNonFactors() {
        long sum = 7;
        List<Long> primes = MathUtils.generatePrimesUpTo(this.primeLimit);
        for(long prime : primes) {
            if(prime != 2 && prime != 5) {
                if(!isPrimeFactor(prime)) {
                   sum += prime;
                }
            }
        }
        return sum;
    }

    private boolean isPrimeFactor(long prime) {
        long k = A(prime);
        List<PrimeFactor> factors = MathUtils.listPrimeFactors(k);

        if(factors.size() == 1 || factors.size() == 2) {
           int factor1 = factors.get(0).getFactor();
           if(factors.size() == 1) {
               return factor1 == 2 || factor1 == 5;
           }
           int factor2 = factors.get(1).getFactor();
           return factor1 == 2 && factor2 == 5;
        }

        return false;
    }

    private long A(long number) {
        long k = 1, n = 1;
        while (n != 0) {
            n = (10 * n + 1) % number;
            k++;
        }
        return k;
    }
}
