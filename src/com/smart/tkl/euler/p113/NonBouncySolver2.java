package com.smart.tkl.euler.p113;

import java.util.Arrays;

public class NonBouncySolver2 {

    private final int digitLimit;
    private final long mod;

    public NonBouncySolver2(int digitLimit, long mod) {
        this.digitLimit = digitLimit;
        this.mod = mod;
    }

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 16) + 7;
        NonBouncySolver2 nonBouncySolver = new NonBouncySolver2(100, mod);
        long[] result = nonBouncySolver.count();
        System.out.println(Arrays.toString(result));
    }

    public long[] count() {
        long[] result = new long[digitLimit + 1];
        long[][] increaseTab = new long[digitLimit + 1][10];
        long[][] decreaseTab = new long[digitLimit + 1][10];

        for(int i = 0; i <= 9 ; i++) {
            increaseTab[0][i] = 0;
            increaseTab[1][i] = 1;
            decreaseTab[0][i] = 0;
            decreaseTab[1][i] = 1;
        }

        result[1] = 9 % mod;

        //increaseTab[k][digit] stores number of increasing numbers with length k that start with digit
        //increaseTab[k][digit] stores number of decreasing numbers with length k that start with digit

        for(int digitsCount = 2; digitsCount <= digitLimit; digitsCount++) {
            long digitsSum = 0;
            for(int startDigit = 1; startDigit <= 9; startDigit++) {
                long incrSum = 0;
                for(int i = startDigit; i <= 9; i++) {
                    incrSum = (incrSum + increaseTab[digitsCount - 1][i]) % mod;
                }
                increaseTab[digitsCount][startDigit] = incrSum;

                long decrSum = 1; // take into account trailing zeroes e.g 700000
                for(int j = 1; j <= startDigit; j++) {
                    decrSum = (decrSum + decreaseTab[digitsCount - 1][j]) % mod;
                }
                decreaseTab[digitsCount][startDigit] = decrSum;

                digitsSum = (digitsSum + incrSum) % mod;
                digitsSum = (digitsSum + decrSum) % mod;
            }

            //remove 9 duplicate for given length e.q 111, 222, ..., 999
            digitsSum = digitsSum - 9;
            result[digitsCount] = (result[digitsCount - 1] + digitsSum) % mod;
        }

        return result;
    }
}
