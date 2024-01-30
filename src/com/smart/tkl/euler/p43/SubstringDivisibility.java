package com.smart.tkl.euler.p43;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubstringDivisibility {

    private static int[] DIVS = new int[]{0, 0, 0, 2, 3, 5, 7, 11, 13, 17};

    public static void main(String[] args) {
        List<Long> numbers = getNumbers(3);
        System.out.println(numbers);
        System.out.println(getSum(3));
    }

    public static long getSum(int N) {
        List<Long> numbers = getNumbers(N);
        return numbers.stream().reduce(0L, Long::sum);
    }

    public static List<Long> getNumbers(int N) {
        List<Long> numbers = new ArrayList<>();
        List<Long> suffixNumbers = getNumbersDivisibleBy(N, DIVS[N]);

        Set<Integer> digits = new HashSet<>();
        for(int i = 0; i <= N; i++) {
            digits.add(i);
        }

        for(Long suffixNumber : suffixNumbers) {
            Set<Integer> digitsLeft = new HashSet<>(digits);
            getDigits(suffixNumber).forEach(digitsLeft::remove);
            if(suffixNumber < 100) {
                digitsLeft.remove(0);
            }
            fillNumbers(numbers, suffixNumber, suffixNumber, digitsLeft, 1000, N - 1);
        }

        return numbers;
    }

    private static void fillNumbers(List<Long> numbers, long value, long substringValue, Set<Integer> digitsLeft, long pow, int divPos) {
        if(digitsLeft.size() == 1) {
            int remainingDigit = digitsLeft.iterator().next();
            long number = remainingDigit * pow + value;
            numbers.add(number);
        }
        else {
            long suffixValue = substringValue / 10;
            for(int remainingDigit : digitsLeft) {
                long newSubstringValue = 100L * remainingDigit + suffixValue;
                if(newSubstringValue % DIVS[divPos] == 0) {
                    long newValue = pow * remainingDigit + value;
                    Set<Integer> newDigitsLeft = new HashSet<>(digitsLeft);
                    newDigitsLeft.remove(remainingDigit);
                    fillNumbers(numbers, newValue, newSubstringValue, newDigitsLeft, 10 * pow, divPos - 1);
                }
            }
        }
    }

    private static List<Long> getNumbersDivisibleBy(int maxDigit, int div) {
        List<Long> numbers = new ArrayList<>();
        boolean[] used = new boolean[maxDigit + 1];
        fillNumbers(numbers, 3, maxDigit, 0, used, div);
        return numbers;
    }

    private static void fillNumbers(List<Long> numbers, int digitsLeft, int maxDigit, long value, boolean[] used, int div) {
        if(digitsLeft == 1) {
            for(int digit = 0; digit <= maxDigit; digit++) {
                if(!used[digit]) {
                    long number = value * 10 + digit;
                    if((number % 1000) %  div == 0) {
                        numbers.add(number);
                    }
                }
            }
        }
        else {
            for(int digit = 0; digit <= maxDigit; digit++) {
                if(!used[digit]) {
                    used[digit] = true;
                    fillNumbers(numbers, digitsLeft - 1, maxDigit, 10 * value + digit, used, div);
                    used[digit] = false;
                }
            }
        }
    }

    private static List<Integer> getDigits(long n) {
        List<Integer> digits = new ArrayList<>();
        while (n > 0) {
            digits.add((int)(n % 10));
            n = n / 10;
        }
        return digits;
    }
}
