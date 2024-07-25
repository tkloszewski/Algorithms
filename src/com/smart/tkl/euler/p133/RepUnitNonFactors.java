package com.smart.tkl.euler.p133;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;

import java.util.ArrayList;
import java.util.List;

public class RepUnitNonFactors {

    private final int primeLimit;

    public RepUnitNonFactors(int primeLimit) {
        this.primeLimit = primeLimit;
    }

    public static void main(String[] args) {
        int primeLimit = 100000;
        long time1 = System.currentTimeMillis();
        RepUnitNonFactors repUnitNonFactors = new RepUnitNonFactors(primeLimit);
        long sum = repUnitNonFactors.getSumOfNonFactors();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum of non factors: " + sum);
        System.out.println("Solution took in ms: " + (time2 - time1));
        time1 = System.currentTimeMillis();
        repUnitNonFactors = new RepUnitNonFactors(primeLimit);
        sum = repUnitNonFactors.getSumOfNonFactors2();
        time2 = System.currentTimeMillis();
        System.out.println("Sum of non factors 2: " + sum);
        System.out.println("Solution 2 took in ms: " + (time2 - time1));
    }

    public long getSumOfNonFactors() {
        long sum = 10;
        List<Long> primes = MathUtils.generatePrimesUpTo(this.primeLimit);
        for(long prime : primes) {
            if(prime > 5) {
                if(!isPrimeFactor(prime)) {
                   sum += prime;
                }
            }
        }
        return sum;
    }

    public long getSumOfNonFactors2() {
        long sum  = 0;
        List<Long> primes = MathUtils.generatePrimesUpTo(this.primeLimit);
        int n = (int) (Math.log10(this.primeLimit) / Math.log10(2));

        for(long prime : primes) {
            long divisor = 9 * prime;
            long remainder = moduloPow(n, divisor);
            if(remainder != 1) {
               sum += prime;
            }
        }

        return sum;
    }

    private boolean isPrimeFactor(long prime) {
        long n = prime - 1;
        while (n % 2 == 0) {
            n = n / 2;
        }
        while (n % 5 == 0) {
            n = n / 5;
        }
        long pow = (prime - 1) / n;
        long modPow = tenPow(pow, prime);
        return modPow == 1;
    }

    private long A(long number) {
        long k = 1, n = 1;
        while (n != 0) {
            n = (10 * n + 1) % number;
            k++;
        }
        return k;
    }

    private long moduloPow(int n, long divisor) {
        long k = 10;
        long result = 10;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 9; j++) {
                result = (result * k) % divisor;
            }
            k = result;
        }
        return result;
    }

    private static long tenPow(long pow, long prime) {
        long result = 1;

        long a = 10;
        while (pow > 0) {
            if((pow & 1) == 1){
                result = (a * result) % prime;
            }
            a = (a * a) % prime;
            pow = pow >> 1;
        }

        return result;
    }
}
