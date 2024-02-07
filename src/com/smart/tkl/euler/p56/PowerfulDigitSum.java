package com.smart.tkl.euler.p56;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.List;

public class PowerfulDigitSum {

    private final int limit;

    public PowerfulDigitSum(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PowerfulDigitSum powerfulDigitSum = new PowerfulDigitSum(200);
        int sum = powerfulDigitSum.maxDigitSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Max sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int maxDigitSum() {
        int maxSum = 0;
        for(int a = 2; a < limit; a++) {
            List<Integer> digits1 = MathUtils.getDigits(a);
            List<Integer> digits = List.of(1);
            for(int b = 1; b < limit; b++) {
                digits = MathUtils.writtenMultiplication(digits1, digits);
                int sum = digits.stream().reduce(0, Integer::sum);
                if(sum > maxSum) {
                   maxSum = sum;
                }
            }
        }
        return maxSum;
    }
}
