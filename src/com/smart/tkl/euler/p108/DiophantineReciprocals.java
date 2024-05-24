package com.smart.tkl.euler.p108;

import com.smart.tkl.lib.BrentFactorization;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiophantineReciprocals {

    private final int threshold;

    public DiophantineReciprocals(int threshold) {
        this.threshold = threshold;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DiophantineReciprocals diophantineReciprocals = new DiophantineReciprocals(1000);
        int foundNumber = diophantineReciprocals.findNumber();
        long time2 = System.currentTimeMillis();
        System.out.println("Number for distinct solution exceeding 1000: " + foundNumber);
        System.out.println("The solution took ms: " + (time2 - time1));
    }

    public int findNumber() {
        int number = 1;
        while (countDistinctProperDivisorsPairs(number) <= threshold) {
            number++;
        }
        System.out.println(countDistinctProperDivisorsPairs(number));
        return number;
    }

    private long countDistinctProperDivisorsPairs(long n) {
        List<PrimeFactor> primeFactors = n < 100000000 ? MathUtils.listPrimeFactors(n) :
                BrentFactorization.listPrimeFactors(n);
        long count = 1;
        for(PrimeFactor primeFactor : primeFactors) {
            count *= (1 + 2L * primeFactor.getPow());
        }
        long d = count/ 2;
        if(count % 2 == 1) {
           d++;
        }
        return d;
    }
}
