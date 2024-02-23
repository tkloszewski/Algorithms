package com.smart.tkl.euler.p92;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SquareDigitChains {

    private final int digitLength;
    private final long mod;

    private boolean[] arrivalsAt89;
    private BigInteger[] factorials;

    public SquareDigitChains(int digitLength, long mod) {
        this.digitLength = digitLength;
        this.mod = mod;
    }

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 9) + 7;
        int k = 200;
        long time1 = System.currentTimeMillis();
        SquareDigitChains squareDigitChains = new SquareDigitChains(k, mod);
        long fastCountResult = squareDigitChains.fastCount2();
        long time2 = System.currentTimeMillis();
        System.out.printf("Arrives at 89 fast count result %d. Time in ms: %d%n",
                fastCountResult, (time2 - time1));
    }

    public long fastCount2() {
        long[] sumsCount = new long[digitLength * 89 + 1];
        for(int digit = 0; digit <= 9; digit++) {
            sumsCount[digit * digit]++;
        }

        for(int length = 2; length <= digitLength; length++) {
            for(int sum = 81 * length; sum > 0; sum--) {
                for(int digit = 1; digit <= 9; digit++) {
                    int square = digit * digit;
                    if(square > sum) {
                       break;
                    }
                    sumsCount[sum] += sumsCount[sum - square];
                    sumsCount[sum] = sumsCount[sum] % mod;
                }
            }
        }

        long count = 0;

        for(int sum = 1; sum <= digitLength * 89; sum++) {
            if(isArrival89(sum)) {
               count += sumsCount[sum];
               count = count % mod;
            }
        }

        return count;
    }

    public long fastCount() {
        factorials = new BigInteger[digitLength + 1];
        factorials[0] = BigInteger.ONE;
        for(int i = 1; i <= digitLength; i++) {
            factorials[i] = factorials[i - 1].multiply(BigInteger.valueOf(i));
        }
        List<Integer> arrivalsValues = new ArrayList<>();
        arrivalsAt89 = new boolean[digitLength * 89 + 1];
        for(int i = 1; i < arrivalsAt89.length; i++) {
            arrivalsAt89[i] = isArrival89(i);
            if(arrivalsAt89[i]) {
               arrivalsValues.add(i);
            }
        }
        System.out.println(arrivalsValues.size());

        int[] combination = new int[10];
        return countCombinations(0, digitLength, combination);
    }

    private long countCombinations(int idx, int left, int[] combination) {
        if(idx + 1 == combination.length) {
            combination[idx] = left;
            return countPermutations(combination);
        }
        long result = 0;
        for(int i = 0; i <= left; i++) {
            combination[idx] = i;
            result += countCombinations(idx + 1, left - i, combination);
            result = result % mod;
        }
        return result;
    }

    private long countPermutations(int[] combination) {
        int arrivalAt = 0;
        for(int i = 0; i < combination.length; i++) {
            arrivalAt += combination[i] * i * i;
        }
        if(arrivalsAt89[arrivalAt]) {
           BigInteger denominator = BigInteger.ONE;
           for(int freq : combination) {
               if (freq != 0) {
                   denominator = denominator.multiply(factorials[freq]);
               }
           }
           return factorials[digitLength].divide(denominator).mod(BigInteger.valueOf(mod)).longValue();
        }
        return 0;
    }

    private boolean isArrival89(int n) {
        int arrivesAt = n;
        while (arrivesAt != 89 && arrivesAt != 1) {
            arrivesAt = getSquareDigits(arrivesAt);
        }
        return arrivesAt == 89;
    }

    private int getSquareDigits(int n) {
        int result = 0;
        while (n > 0) {
            int digit = n % 10;
            result += digit * digit;
            n = n / 10;
        }
        return result;
    }

}
