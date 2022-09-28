package com.smart.tkl.euler.p108;

import com.smart.tkl.utils.MathUtils;
import com.smart.tkl.utils.PrimeFactor;
import java.util.List;

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
        return number;
    }

    private int countDistinctProperDivisorsPairs(int n) {
        List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(n);
        int count = 1;
        for(PrimeFactor primeFactor : primeFactors) {
            count *= (1 + 2 * primeFactor.getPow());
        }
        int d = count/ 2;
        if(count % 2 == 1) {
           d++;
        }
        return d;
    }
}
