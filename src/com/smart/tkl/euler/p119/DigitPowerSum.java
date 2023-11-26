package com.smart.tkl.euler.p119;

import com.smart.tkl.lib.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class DigitPowerSum {

    private final int limit;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DigitPowerSum digitPowerSum = new DigitPowerSum(30);
        List<Long> powerSequence = digitPowerSum.resolveSequence();
        long time2 = System.currentTimeMillis();
        System.out.println("Sequence: " + powerSequence);
        System.out.println("Result: " + powerSequence.get(powerSequence.size() - 1));
        System.out.println("Solution took ms: " + (time2 - time1));
    }

    public DigitPowerSum(int limit) {
        this.limit = limit;
    }

    public List<Long> resolveSequence() {
        List<Long> sequence = new ArrayList<>();
        int digitsCount = 2;
        long lowerBound = 1;
        while (true) {
            lowerBound *= 10;
            long upperBound = 10 * lowerBound - 1;
            int maxSum = 9 * digitsCount;
            for(int digitSum = 7; digitSum <= maxSum; digitSum++) {
                int exp = (int)Math.ceil(Math.log10(lowerBound) / (Math.log10(digitSum)));
                long pow = (long) Math.pow(digitSum, exp);
                while (pow <= upperBound) {
                   int sumOfDigits = MathUtils.sumOfDigits(String.valueOf(pow));
                   if(sumOfDigits == digitSum) {
                      sequence.add(pow);
                      if(sequence.size() >= limit) {
                         return sequence;
                      }
                   }
                   pow *= digitSum;
                }
            }
            digitsCount++;
        }
    }
}
