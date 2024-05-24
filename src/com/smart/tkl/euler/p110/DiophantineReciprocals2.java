package com.smart.tkl.euler.p110;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DiophantineReciprocals2 {

    private final long limit;
    private final List<Long> primes;

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        DiophantineReciprocals2 diophantineReciprocals = new DiophantineReciprocals2(130754415038L);
        BigInteger minValue = diophantineReciprocals.findMinValue();
        long t2 = System.currentTimeMillis();
        System.out.println("Diophantine reciprocals primes: " + diophantineReciprocals.primes);
        System.out.println("Min solution: " + minValue);
        System.out.println("Time in ms: " + (t2 - t1));
    }

    public DiophantineReciprocals2(long solutionThreshold) {
        this.limit = solutionThreshold;
        this.primes = MathUtils.generatePrimeList(primesCount( 2 * solutionThreshold + 1));
        System.out.println(primes);
    }

    private static int primesCount(long productLimit) {
        double log3 = Math.log10(productLimit) / Math.log10(3);
        return (log3 == (int)log3) ? (int)log3 : (int)log3 + 1;
    }

    public BigInteger findMinValue() {
        BigInteger minValue = calcPrimesValue();
        int lastPrime = primes.get(primes.size() - 1).intValue();
        List<FactorizedValue> factorizedValues = toFactorsLessThan(lastPrime);

        int[] expFactors = new int[lastPrime + 1];
        long expValue = 1;
        for(long prime : primes) {
            expFactors[(int) prime] = 3;
            expValue *= 3;
        }

        int lastPrimeIndex = primes.size() - 1;
        while (true) {
            BigInteger newMinValue = minValue.divide(BigInteger.valueOf(lastPrime));
            long newExpValue = expValue / expFactors[lastPrime];
            expFactors[lastPrime] = 1;
            boolean foundLessValue = false;
            for(FactorizedValue factorizedValue : factorizedValues) {
                if(factorizedValue.value >= lastPrime) {
                   break;
                }
                long multipliedExp = multiplyByValue(expFactors, newExpValue, factorizedValue);
                if((multipliedExp + 1) / 2 >= limit) {
                    newMinValue = newMinValue.multiply(BigInteger.valueOf(factorizedValue.value));
                    expValue = multipliedExp;
                    for(PrimeFactor primeFactor : factorizedValue.factors) {
                        int prime = (int)primeFactor.getFactor();
                        expFactors[prime] += 2 * primeFactor.getPow();
                    }
                    foundLessValue = true;
                    break;
                }
            }

            if(foundLessValue) {
                minValue = newMinValue;
                lastPrimeIndex--;
                lastPrime = primes.get(lastPrimeIndex).intValue();
            }
            else {
               break;
            }
        }

        return minValue;
    }

    private BigInteger calcPrimesValue() {
        BigInteger value = BigInteger.ONE;
        for(long prime : primes) {
            value = value.multiply(BigInteger.valueOf(prime));
        }
        return value;
    }

    private static long multiplyByValue(int[] expFactors, long expValue, FactorizedValue factorizedValue) {
        long newValue = expValue;
        for(PrimeFactor primeFactor : factorizedValue.factors) {
            int prime = (int)primeFactor.getFactor();
            int e1 = (expFactors[prime] - 1) / 2;
            int e2 = primeFactor.getPow();
            int e = 2 * (e1 + e2) + 1;
            newValue = newValue / expFactors[prime];
            newValue = newValue * e;
        }
        return newValue;
    }

    private static List<FactorizedValue> toFactorsLessThan(long maxPrime) {
        List<FactorizedValue> result = new ArrayList<>();
        for(long n = 2; n < maxPrime; n++) {
            List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(n);
            result.add(new FactorizedValue(n, primeFactors));
        }
        return result;
    }

    private static class FactorizedValue {
        long value;
        List<PrimeFactor> factors;

        FactorizedValue(long value, List<PrimeFactor> factors) {
            this.value = value;
            this.factors = factors;
        }
    }
}
