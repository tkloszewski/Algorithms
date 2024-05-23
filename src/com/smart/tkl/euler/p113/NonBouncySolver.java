package com.smart.tkl.euler.p113;

import java.math.BigInteger;
import java.util.Arrays;

public class NonBouncySolver {

    private final int digitLimit;
    private final BigInteger[][] increaseTab;
    private final BigInteger[][] decreaseTab;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        NonBouncySolver nonBouncySolver = new NonBouncySolver(100);
        BigInteger count = nonBouncySolver.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public NonBouncySolver(int digitLimit) {
        this.digitLimit = digitLimit;
        this.increaseTab = new BigInteger[digitLimit + 1][10];
        this.decreaseTab = new BigInteger[digitLimit + 1][10];

        for(int i = 0; i <= 9 ; i++) {
            increaseTab[0][i] = BigInteger.ZERO;
            increaseTab[1][i] = BigInteger.ONE;
            decreaseTab[0][i] = BigInteger.ZERO;
            decreaseTab[1][i] = BigInteger.ONE;
        }

    }

    public BigInteger count() {
        BigInteger result = BigInteger.valueOf(9);
        for(int digitsCount = 2; digitsCount <= digitLimit; digitsCount++) {
            BigInteger digitSum = BigInteger.ZERO;
            for(int digit = 1; digit <= 9; digit++) {

                BigInteger incrSum = BigInteger.ZERO;
                for(int i = digit; i <= 9; i++) {
                    incrSum = incrSum.add(increaseTab[digitsCount - 1][i]);
                }
                increaseTab[digitsCount][digit] = incrSum;


                BigInteger decrSum = BigInteger.ZERO;
                for(int j = 1; j <= digit; j++) {
                    decrSum = decrSum.add(decreaseTab[digitsCount - 1][j]);
                }

                //Take into account 0. For example 3000000
                decrSum = decrSum.add(BigInteger.ONE);

                decreaseTab[digitsCount][digit] = decrSum;
            }

            for(int digit = 1; digit <= 9; digit++) {
                digitSum = digitSum.add(increaseTab[digitsCount][digit]);
            }
            for(int digit = 1; digit <= 9; digit++) {
                digitSum = digitSum.add(decreaseTab[digitsCount][digit]);
            }

            digitSum = digitSum.subtract(BigInteger.valueOf(9));
            result = result.add(digitSum);
            System.out.println("Result for k: " + digitsCount + " => " + result);
        }

        return result;
    }
}
