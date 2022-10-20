package com.smart.tkl.euler.p145;

import java.util.ArrayList;
import java.util.List;

public class ReversibleNumber {

    private static final int[] sums = new int[19];
    private static final int[] sumsWithZero = new int[19];

    public static void main(String[] args) {
        fillSums(9);
    }

    private static void fillSums(int powerLimit) {
        int totalCount = 0;
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

        for(int power = 2; power <= powerLimit; power++) {
            int pairs = power / 2;
            List<Integer> coefficients = new ArrayList<>();
            int coefficient = (int)Math.pow(10, power - 1) + 1;
            coefficients.add(coefficient);
            int tenPow = 10;
            for(int i = 1; i < pairs; i++) {
                coefficient = (coefficient - (tenPow/ 10)) / 10 + tenPow;
                tenPow *= 10;
                coefficients.add(coefficient);
            }
            if(power % 2 == 1) {
                coefficients.add(2 * tenPow);
            }
            System.out.println("Coefficient for power: " + power + " => " + coefficients);
            int reversibleCount = countReversible(power, coefficients, 0, 1, 0);
            System.out.println("Reversible count: " + reversibleCount);
            totalCount += reversibleCount;
        }
        System.out.println("Total count: " + totalCount);
    }

    private static int countReversible(int power, List<Integer> coefficients, int pos, int accumulator, long value) {
        int coefficient = coefficients.get(pos);
        if(pos == coefficients.size() - 1) {
            if(power % 2 == 1) {
                int count = 0;
                for(int i = 0; i <= 9; i++) {
                    long number = value + i * coefficient;
                    if(allDigitsOdd(number)) {
                        count++;
                    }
                }
                return count * accumulator;
            }
            else {
                int count = 0;
                if (coefficients.size() > 1) {
                    for(int i = 0; i <= 18; i++) {
                        long number = value + i * coefficient;
                        if(allDigitsOdd(number)) {
                            count += sumsWithZero[i];
                        }
                    }
                }
                else {
                    for(int i = 2; i <= 18; i++) {
                        long number = value + i * coefficient;
                        if(allDigitsOdd(number)) {
                            count += sums[i];
                        }
                    }
                }
                return count * accumulator;
            }
        }
        else {
            if(pos == 0) {
                int result = 0;
                for(int i = 2; i <= 18; i++) {
                    long initialValue = i * coefficient;
                    int initialAccumulator = sums[i];
                    int count = countReversible(power, coefficients, pos + 1, initialAccumulator, initialValue);
                    result += count;
                }
                return result;
            }
            else {
                int result = 0;
                for(int i = 0; i <= 18; i++) {
                    long newValue = i * coefficient + value;
                    int newAccumulator = sumsWithZero[i] * accumulator;
                    int count = countReversible(power, coefficients, pos + 1, newAccumulator, newValue);
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

    private static boolean allDigitsOdd(List<Integer> digits) {
        for(int digit : digits) {
            if(digit % 2 == 0) {
                return false;
            }
        }
        return true;
    }

}
