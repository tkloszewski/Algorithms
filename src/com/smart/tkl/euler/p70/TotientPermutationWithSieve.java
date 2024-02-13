package com.smart.tkl.euler.p70;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.LinkedHashSet;
import java.util.Set;

public class TotientPermutationWithSieve {

    private final int limit;

    public TotientPermutationWithSieve(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        TotientPermutationWithSieve totientPermutation = new TotientPermutationWithSieve(100);
        int foundValue = totientPermutation.findValue();
        System.out.println("Found value: " + foundValue);
    }

    public int findValue() {
        long[] totients = MathUtils.generateTotientsUpTo(this.limit);
        double minRatio = Double.MAX_VALUE;

        int foundValue = -1;

        for(int n = 3; n < limit; n += 2) {
            long phi = totients[n];
            if(phi == n - 1) {
               continue;
            }
            Digits digits1 = toDigits(n);
            Digits digits2 = toDigits((int)phi);
            if(isPermutation(digits1, digits2)) {
               double ratio = (double) n / (double) phi;
               if(ratio < minRatio) {
                  minRatio = ratio;
                  foundValue = n;
               }
            }
        }

        return foundValue;
    }

    private boolean isPermutation(Digits digits1, Digits digits2) {
        if(digits1.length != digits2.length) {
           return false;
        }
        for(int digit : digits1.digits) {
            if(digits1.freq[digit] != digits2.freq[digit]) {
               return false;
            }
        }
        return true;
    }

    private static Digits toDigits(int n) {
        Set<Integer> digits = new LinkedHashSet<>();
        int[] freq = new int[10];
        int length = 0;
        while (n > 0) {
            int digit = n % 10;
            digits.add(digit);
            freq[digit]++;
            n = n / 10;
        }
        return new Digits(digits, freq, length);
    }

    private static class Digits {
        Set<Integer> digits;
        int[] freq;
        int length;

        public Digits(Set<Integer> digits, int[] freq, int length) {
            this.digits = digits;
            this.freq = freq;
            this.length = length;
        }
    }
}
