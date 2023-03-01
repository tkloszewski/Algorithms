package com.smart.tkl.euler.p158;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

public class StringExplorer {

    private final long[][] memo = new long[27][27];

    public static void main(String[] args) {
        System.out.println((long) Math.pow(16, 12));
        long time1 = System.currentTimeMillis();
        long max = new StringExplorer().getMaximumValue();
        long time2 = System.currentTimeMillis();
        System.out.println("Max: " + max);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long getMaximumValue() {
        long max = 0;
        for(int n = 2; n <= 26; n++) {
            long numOfStrings = countStrings2(n);
            max = Math.max(max, numOfStrings);
        }
        return max;
    }

    private long countStrings(int n) {
        long combinationCount = CombinatoricsUtils.countCombinations(26, n, this.memo);
        long permutationCount = 0;

        for(int i = 0; i <= n - 2; i++) {
            for(int j = i + 1; j <= n - 1; j++) {
                permutationCount += CombinatoricsUtils.countCombinations(j, i, this.memo);
            }
        }

        return combinationCount * permutationCount;
    }

    private long countStrings2(int n) {
        long combinationCount = CombinatoricsUtils.countCombinations(26, n, this.memo);
        long permutationCount = pow2(n) - 1 - n;
        return combinationCount * permutationCount;
    }

    private static long pow2(int n) {
        long result = 1, pow = 2;
        while (n > 0) {
            if((n & 1) == 1) {
               result *= pow;
            }
            pow = pow * pow;
            n = n / 2;
        }
        return result;
    }
}
