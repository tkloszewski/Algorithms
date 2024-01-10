package com.smart.tkl.euler.p145;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ReversibleNumber {

    private final int[] sums = new int[19];
    private final int[] sumsWithZero = new int[19];
    private final int powerLimit;

    public ReversibleNumber(int powerLimit) {
        this.powerLimit = powerLimit;
        initSums();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ReversibleNumber reversibleNumber = new ReversibleNumber(15);
        BigInteger totalCount = reversibleNumber.countReversibleNumbers();
        long time2 = System.currentTimeMillis();
        System.out.println("Total count: " + totalCount);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private void initSums() {
        for(int i = 1; i <= 9; i++) {
            for(int j = 1; j <= 9; j++) {
                sums[i + j]++;
            }
        }
        for(int i = 0; i <= 9; i++) {
            for(int j = 0; j <= 9; j++) {
                sumsWithZero[i + j]++;
            }
        }
    }

    public BigInteger countReversibleNumbers() {
        BigInteger totalCount = BigInteger.ZERO;

        for(int power = 2; power <= this.powerLimit; power++) {
            int pairs = power / 2;
            List<Long> coefficients = new ArrayList<>();
            long coefficient = (int)Math.pow(10, power - 1) + 1;
            coefficients.add(coefficient);
            long tenPow = 10;
            for(int i = 1; i < pairs; i++) {
                coefficient = (coefficient - (tenPow/ 10)) / 10 + tenPow;
                tenPow *= 10;
                coefficients.add(coefficient);
            }
            if(power % 2 == 1) {
                coefficients.add(2 * tenPow);
            }
            long reversibleCount = countReversible(power, coefficients, 0, 1, 0);
            totalCount = totalCount.add(BigInteger.valueOf(reversibleCount));
        }
        return totalCount;
    }

    private long countReversible(int power, List<Long> coefficients, int pos, long accumulator, long value) {
        long coefficient = coefficients.get(pos);
        if(pos == coefficients.size() - 1) {
            if(power % 2 == 1) {
                long count = 0;
                for(int i = 0; i <= 9; i++) {
                    long number = value + i * coefficient;
                    if(allDigitsOdd(number)) {
                        count++;
                    }
                }
                return count * accumulator;
            }
            else {
                long count = 0;
                if (coefficients.size() > 1) {
                    for(int i = 0; i <= 18; i++) {
                        long number = value + i * coefficient;
                        if(allDigitsOdd(number)) {
                            count += this.sumsWithZero[i];
                        }
                    }
                }
                else {
                    for(int i = 2; i <= 18; i++) {
                        long number = value + i * coefficient;
                        if(allDigitsOdd(number)) {
                            count += this.sums[i];
                        }
                    }
                }
                return count * accumulator;
            }
        }
        else {
            if(pos == 0) {
                long result = 0;
                for(int i = 2; i <= 18; i++) {
                    long initialValue = i * coefficient;
                    int initialAccumulator = this.sums[i];
                    long count = countReversible(power, coefficients, pos + 1, initialAccumulator, initialValue);
                    result += count;
                }
                return result;
            }
            else {
                long result = 0;
                for(int i = 0; i <= 18; i++) {
                    long newValue = i * coefficient + value;
                    long newAccumulator = this.sumsWithZero[i] * accumulator;
                    long count = countReversible(power, coefficients, pos + 1, newAccumulator, newValue);
                    result += count;
                }
                return result;
            }
        }
    }

    private static boolean allDigitsOdd(long number) {
        long n = number;
        while (n > 0) {
            int digit = (int)n % 10;
            if(digit % 2 == 0) {
                return false;
            }
            n = n / 10;
        }
        return true;
    }

}
