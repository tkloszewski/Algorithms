package com.smart.tkl.euler.p176;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.List;

public class CommonCathetusRightAngledTriangle {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long cathetus = findSmallestCathetus(47547);
        long time2 = System.currentTimeMillis();
        System.out.println("Smallest cathetus: " + cathetus);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long findSmallestCathetus(long n) {
        long m = 2 * n + 1;
        List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(m);
        List<Long> factors = new ArrayList<>();
        for(PrimeFactor primeFactor : primeFactors) {
            for(long i = 1; i <= primeFactor.getPow(); i++) {
                factors.add(primeFactor.getFactor());
            }
        }
        factors.sort((o1, o2) -> -1 * o1.compareTo(o2));
        List<Long> primes = MathUtils.generatePrimeList(factors.size() + 1);
        long evenResult = 1;
        for(int i = 0; i < factors.size(); i++) {
            long factor = factors.get(i);
            long prime = primes.get(i);
            if(prime == 2) {
               factor = (factor + 1) / 2;
            }
            else {
                factor = (factor - 1) / 2;
            }
            for(int k = 0; k < factor; k++) {
                evenResult *= prime;
            }
        }
        long oddResult = 1;
        for(int i = 0; i < factors.size(); i++) {
            long factor = (factors.get(i) - 1) / 2;
            long prime = primes.get(i + 1);
            for(int k = 0; k < factor; k++) {
                oddResult *= prime;
            }
        }
        return Math.min(evenResult, oddResult);
    }
}
