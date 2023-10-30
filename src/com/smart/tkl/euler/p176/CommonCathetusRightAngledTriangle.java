package com.smart.tkl.euler.p176;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonCathetusRightAngledTriangle {

    public static void main(String[] args) {
        long n = 88573;
        long time1 = System.currentTimeMillis();
        long cathetus = findSmallestCathetus(n);
        long time2 = System.currentTimeMillis();
        System.out.println("Smallest cathetus: " + cathetus);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long findSmallestCathetus(long n) {
        long m = 2 * n + 1;
        List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(m);
        Map<Long, Integer> factorsExpMap = new HashMap<>();

        List<Long> factors = new ArrayList<>();
        for (PrimeFactor primeFactor : primeFactors) {
            for (long i = 1; i <= primeFactor.getPow(); i++) {
                factors.add(primeFactor.getFactor());
            }
            factorsExpMap.put(primeFactor.getFactor(), primeFactor.getPow());
        }

        factors.sort((o1, o2) -> -1 * o1.compareTo(o2));
        List<Long> primes = MathUtils.generatePrimeList(factors.size() + 1);
        long evenResult = 1;

        long biggestPrimeForEven = primes.get(factors.size() - 1);
        int lastBigInd = factors.size() - 1;

        for (int i = 0, l = 0; i < factors.size(); i++, l++) {
            long factor = factors.get(i);
            long prime = primes.get(l);
            long e = prime == 2 ? (factor + 1) / 2 : (factor - 1) / 2;
            for (int k = 0; k < e; k++) {
                evenResult *= prime;
            }
        }

        List<PrimeFactor> finalFactors = MathUtils.listPrimeFactors(evenResult);
        Map<Long, Integer> finalFactorsMap = new HashMap<>();
        for(PrimeFactor finalFactor : finalFactors) {
            finalFactorsMap.put(finalFactor.getFactor(), finalFactor.getPow());
        }

        /*Adjust result by grouping terms.
        In case of 3^11 we can group as 3^2 * 3^2 * 3 * 3 * 3 * 3 * 3 * 3 * 3 =>
         2^5 * 3^4 * 5 * 7 * 11 * 13 * 17 * 19 * 23
         */
        for (int i = 0, l = 0; i < factors.size(); i++, l++) {
            long factor = factors.get(i);
            long prime = primes.get(l);
            long e = prime == 2 ? (factor + 1) / 2 : (factor - 1) / 2;

            if (factorsExpMap.get(factor) > 1) {
                long exp = factorsExpMap.get(factor);
                long newFactor = factor;
                long adv = 0;
                for (int j = 2; j <= exp; j++) {
                    newFactor *= factor;
                    long pow = prime == 2 ? (newFactor + 1) / 2 : (newFactor - 1) / 2;
                    long incPow = pow - finalFactorsMap.get(prime);
                    if ((long) Math.pow(prime, incPow) >= biggestPrimeForEven) {
                        break;
                    }
                    adv++;
                    e = incPow;
                }
                if (adv > 0) {
                    i += adv;
                    evenResult = evenResult / biggestPrimeForEven;
                    evenResult = evenResult * (long)Math.pow(prime, e);
                    biggestPrimeForEven = primes.get(--lastBigInd);
                }
            }
        }

        return evenResult;
    }
}

