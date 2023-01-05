package com.smart.tkl.euler.p493;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

public class UnderTheRainbow {

    private static final long[][] memo = new long[100][100];

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        double expectedValue = calculateExpectedValue();
        long time2 = System.currentTimeMillis();
        System.out.println("Expected value: " + expectedValue);
        System.out.println("Solution found in ms: " + (time2 - time1));
    }

    public static double calculateExpectedValue() {
        long denominator = CombinatoricsUtils.countCombinations(70, 20, memo);
        long numerator = 0;
        for(int colors = 1; colors <= 7; colors++) {
            long multiplier = CombinatoricsUtils.countCombinations(7, colors, memo);
            long colorSetWays = countColorSetWays(0, 20, new int[colors]);
            numerator += colors * multiplier * colorSetWays;
        }
        return (double)numerator / denominator;
    }

    private static long countColorSetWays(int pos, int remainingSum, int[] selectedBalls) {
        if(pos == selectedBalls.length - 1) {
            if (remainingSum <= 10) {
                selectedBalls[pos] = remainingSum;
                long combinations = 1;
                for(int ball : selectedBalls) {
                    combinations *= CombinatoricsUtils.countCombinations(10, ball, memo);
                }
                return combinations;
            }
            return 0;
        }
        long result = 0;
        for(int i = 1; i <= 10; i++) {
            if (i < remainingSum) {
                selectedBalls[pos] = i;
                result += countColorSetWays(pos + 1, remainingSum - i, selectedBalls);
            }
        }
        return result;
    }

}
