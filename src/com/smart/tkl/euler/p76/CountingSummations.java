package com.smart.tkl.euler.p76;

import java.util.Arrays;

public class CountingSummations {

    private final int amount;
    private final long[][] memo;

    public CountingSummations(int amount) {
        this.amount = amount;
        this.memo = new long[amount + 1][amount + 1];
    }

    public static void main(String[] args) {
        int amount = 6;
        CountingSummations countingSummations = new CountingSummations(amount);
        long time1 = System.currentTimeMillis();
        long numberOfSums = countingSummations.countSums();
        long time2 = System.currentTimeMillis();
        System.out.printf("The number %d can be written in %d ways as a sum of at least two positive integers.\n", amount, numberOfSums);
        System.out.println("Iterative approach took in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        long recursiveCount = countingSummations.recursiveCount(amount, amount - 1);
        time2 = System.currentTimeMillis();
        System.out.println("Recursive count: " + recursiveCount);
        System.out.println("Recursive approach took in ms: " + (time2 - time1));
    }

    public long countSums() {
        long[] sums = new long[amount + 1];
        sums[0] = 1;

        for(int number = 1; number < amount; number++) {
            for(int j = number; j <= amount; j++) {
                sums[j] = sums[j] + sums[j - number];
            }
        }

        for(int number = 1; number < amount; number++) {
            sums[number]--;
        }

        return sums[amount];
    }

    public long recursiveCount(int amount, int max) {
        if(memo[amount][max] != 0) {
           return memo[amount][max];
        }
        if(amount == 0 || amount == 1 || max == 1) {
           return 1;
        }
        long result = 0;
        for(int num = 1; num <= max && amount - num >= 0; num++) {
            result += recursiveCount(amount - num, num);
        }
        memo[amount][max] = result;
        return result;
    }

}
