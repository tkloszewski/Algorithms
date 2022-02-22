package com.smart.tkl.euler.p92;

import com.smart.tkl.utils.MathUtils;

public class SquareDigitChains {

    private final int limit;
    private final int digitLength;

    private boolean[] arrivalsAt89;
    private int[] factorials;

    public SquareDigitChains(int limit) {
        this.limit = limit;
        this.digitLength = (int)Math.log10(limit);
    }

    public SquareDigitChains(int limit, int digitLength) {
        this.limit = limit;
        this.digitLength = digitLength;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SquareDigitChains squareDigitChains = new SquareDigitChains(10000000);
        int fastCountResult = squareDigitChains.fastCount();
        long time2 = System.currentTimeMillis();
        System.out.printf("Arrives at 89 fast count result %d. Time in ms: %d%n",
                fastCountResult, (time2 - time1));
    }

    public int slowCount() {
        int count = 0;
        for(int i = 1; i < limit; i++) {
            int arrivesAt = i;
            while (arrivesAt != 89 && arrivesAt != 1) {
                arrivesAt = getSquareDigits(arrivesAt);
            }
            if(arrivesAt == 89) {
                count++;
            }

        }
        return count;
    }

    public int fastCount() {
        factorials = new int[digitLength + 1];
        factorials[0] = 1;
        for(int i = 1; i <= digitLength; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        arrivalsAt89 = new boolean[digitLength * 89 + 1];
        for(int i = 1; i < arrivalsAt89.length; i++) {
            arrivalsAt89[i] = isArrival89(i);
        }

        int[] combination = new int[10];
        return countCombinations(0, digitLength, combination);
    }

    private int countCombinations(int idx, int left, int[] combination) {
        if(idx + 1 == combination.length) {
            combination[idx] = left;
            return countPermutations(combination);
        }
        int result = 0;
        for(int i = 0; i <= left; i++) {
            combination[idx] = i;
            result += countCombinations(idx + 1, left - i, combination);
        }
        return result;
    }

    private int countPermutations(int[] combination) {
        int arrivalAt = 0;
        for(int i = 0; i < combination.length; i++) {
            arrivalAt += combination[i] * i * i;
        }
        if(arrivalsAt89[arrivalAt]) {
           int denominator = 1;
           for(int freq : combination) {
               denominator *= factorials[freq];
           }
           return factorials[digitLength] / denominator;
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
        for(int digit : MathUtils.getDigits(n)) {
            result += digit * digit;
        }
        return result;
    }

}
