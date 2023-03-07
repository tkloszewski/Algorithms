package com.smart.tkl.euler.p171;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

public class PerfectDigitSumSquare {

    private final int limit;
    private final boolean[] squares;
    private final long mod;
    private final long[] factorials;
    private final long[] repUnits;

    public PerfectDigitSumSquare(int limit, int lastDigitsLength) {
        this.limit = limit;
        this.factorials = new long[limit + 1];
        this.squares = new boolean[1 + limit * 81];
        this.repUnits = new long[limit + 1];
        this.mod = (long) Math.pow(10, lastDigitsLength);
        initFactorials();
        initSquares();
        initRepUnits();
    }

    public static void main(String[] args) {
        System.out.println(CombinatoricsUtils.countCombinations(28, 20));
        long time1 = System.currentTimeMillis();
        PerfectDigitSumSquare perfectDigitSumSquare = new PerfectDigitSumSquare(20, 9);
        long sumOfNumbers = perfectDigitSumSquare.sumOfNumbers(20);
        long time2 = System.currentTimeMillis();
        System.out.println("Sum of numbers: " + sumOfNumbers);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private void initFactorials() {
        factorials[0] = 1;
        for(int i = 1; i <= limit; i++) {
            factorials[i] = (i * factorials[i -1]);
        }
    }

    private void initSquares() {
        int bound = this.limit * 81;
        for(int i = 1; i * i <= bound; i++) {
            int square = i * i;
            squares[square] = true;
        }
    }

    private void initRepUnits() {
        for(int i = 1; i <= this.limit; i++) {
            this.repUnits[i] = (10 * this.repUnits[i - 1] + 1) % this.mod;
        }
    }

    public long sumOfNumbers(int digitsCount) {
        return sumOfNumbers(new int[10], digitsCount, 0, digitsCount, 0);
    }

    private long sumOfNumbers(int[] digits, int digitsLeft, int startDigit, int length, int currentSquare) {
        long result = 0;
        if(digitsLeft == 0) {
            if(this.squares[currentSquare]) {
                result = sumOfPermutations(digits, length);
            }
        }
        else {
            for(int digit = startDigit; digit <= 9; digit++) {
                digits[digit]++;
                result += sumOfNumbers(digits, digitsLeft - 1, digit, length, currentSquare + digit * digit);
                result = result % this.mod;
                digits[digit]--;
            }
        }
        return result % this.mod;
    }

    private long sumOfPermutations(int[] digits, int len) {
        long repUnit = this.repUnits[len];
        long sum = this.factorials[len];

        int term = 0;
        for(int digit = 0; digit <= 9; digit++) {
            int freq = digits[digit];
            term += digit * freq;
            sum = sum / this.factorials[freq];
        }

        sum = sum * term;
        sum = sum / len;
        sum = (sum % this.mod);

        return (sum * repUnit)  % this.mod;
    }
}
