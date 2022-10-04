package com.smart.tkl.euler.p113;

import java.math.BigDecimal;

public class NonBouncySolver {

    private final int digitLimit;
    private final BigDecimal[][] increaseTab;
    private final BigDecimal[][] decreaseTab;

    public static void main(String[] args) {
        NonBouncySolver nonBouncySolver = new NonBouncySolver(100);
        BigDecimal count = nonBouncySolver.count();
        System.out.println("Count: " + count);
    }

    public NonBouncySolver(int digitLimit) {
        this.digitLimit = digitLimit;
        this.increaseTab = new BigDecimal[digitLimit + 1][10];
        this.decreaseTab = new BigDecimal[digitLimit + 1][10];

        for(int i = 0; i <= 9 ; i++) {
            increaseTab[0][i] = BigDecimal.ZERO;
            increaseTab[1][i] = BigDecimal.ONE;
            decreaseTab[0][i] = BigDecimal.ZERO;
            decreaseTab[1][i] = BigDecimal.ONE;
        }

    }

    public BigDecimal count() {
        BigDecimal result = BigDecimal.valueOf(9);
        for(int digitsCount = 2; digitsCount <= digitLimit; digitsCount++) {
            BigDecimal digitSum = BigDecimal.ZERO;
            for(int digit = 1; digit <= 9; digit++) {

                BigDecimal incrSum = BigDecimal.ZERO;
                for(int i = digit; i <= 9; i++) {
                    incrSum = incrSum.add(increaseTab[digitsCount - 1][i]);
                }
                increaseTab[digitsCount][digit] = incrSum;


                BigDecimal decrSum = BigDecimal.ZERO;
                for(int j = 1; j <= digit; j++) {
                    decrSum = decrSum.add(decreaseTab[digitsCount - 1][j]);
                }

                //Take into account 0. For example 3000000
                decrSum = decrSum.add(BigDecimal.ONE);

                decreaseTab[digitsCount][digit] = decrSum;
            }

            for(int digit = 1; digit <= 9; digit++) {
                digitSum = digitSum.add(increaseTab[digitsCount][digit]);
            }
            for(int digit = 1; digit <= 9; digit++) {
                digitSum = digitSum.add(decreaseTab[digitsCount][digit]);
            }

            digitSum = digitSum.subtract(BigDecimal.valueOf(9));
            result = result.add(digitSum);
        }

        return result;
    }
}
