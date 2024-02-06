package com.smart.tkl.euler.p52;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermutedMultiple {

    private final int limit;
    private final int maxK;

    public PermutedMultiple(int limit, int k) {
        this.limit = limit;
        maxK = k;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PermutedMultiple permutedMultiple = new PermutedMultiple(2000000, 6);
        List<Multiple> multiples = permutedMultiple.getMultiples();
        long time2 = System.currentTimeMillis();
        System.out.println(multiples);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private List<Multiple> getMultiples() {
        List<Multiple> result = new ArrayList<>();
        Map<Integer, Set<Integer>> digitMap = createDigitMap(this.maxK);
        System.out.println("Digit map: " + digitMap);

        int pow = 10;
        for(int n = pow; n <= limit; n++) {
            if(isPermutedMultiple(n, maxK, digitMap)) {
                List<Integer> values = new ArrayList<>();
                for(int k = 1; k <= maxK; k++) {
                    values.add(k * n);
                }
                result.add(new Multiple(values));
            }
            if(n * maxK >= 10 * pow) {
               pow *= 10;
               n = pow;
            }
        }

        return result;
    }


    private static Map<Integer, Set<Integer>> createDigitMap(int maxK) {
        Map<Integer, Set<Integer>> digitMap = new HashMap<>();

        for(int digit = 1; digit <= 9; digit++) {
            Set<Integer> mandatoryDigits = new LinkedHashSet<>();
            for (int k = 1; k <= maxK; k++) {
                 mandatoryDigits.add((k * digit) % 10);
            }
            digitMap.put(digit, mandatoryDigits);
        }

        return digitMap;
    }

    private static boolean isPermutedMultiple(int n, int maxK, Map<Integer, Set<Integer>> digitMap) {
        int lastDigit = n % 10, num = n, zeroes = 0;

        Set<Integer> digits = new LinkedHashSet<>();
        int[] freq = new int[10];

        boolean isLastDigit = true, allDigitsOdd = true;
        int length = 0;
        while (num > 0) {
            int digit = num % 10;
            if(digit == 0) {
                if (isLastDigit) {
                    zeroes++;
                }
            }
            else {
                if (lastDigit == 0) {
                    lastDigit = digit;
                }
                isLastDigit = false;
            }
            if(digit % 2 == 0) {
               allDigitsOdd = false;
            }
            digits.add(digit);
            freq[digit]++;
            length++;
            num = num / 10;
        }

        Digits digits1 = new Digits(digits, freq, length);

        Set<Integer> mandatoryDigits = digitMap.get(lastDigit);
        if(digits.size() - zeroes < mandatoryDigits.size()) {
            return false;
        }
        return (hasDigits(digits, mandatoryDigits) || !allDigitsOdd) && isPermutedMultiple(digits1, n, maxK);
    }

    private static boolean hasDigits(Set<Integer> digits, Set<Integer> mandatoryDigits) {
        for(Integer mandatoryDigit : mandatoryDigits) {
            if(!digits.contains(mandatoryDigit)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPermutedMultiple(Digits digits1, int n, int maxK) {
        for(int i = 2; i <= maxK; i++) {
            Digits digits2 = toDigits(i * n);
            if(!isPermutation(digits1, digits2)) {
               return false;
            }
        }
        return true;
    }

    private static boolean isPermutation(Digits digits1, Digits digits2) {
        if(digits1.length != digits2.length) {
           return false;
        }
        if(digits1.digits.size() != digits2.digits.size()) {
            return false;
        }
        for(int digit : digits1.digits) {
            if(digits1.freq[digit] != digits2.freq[digit]) {
               return false;
            }
        }
        return true;
    }

    private static Digits toDigits(int n) {
        Set<Integer> digits = new LinkedHashSet<>();
        int[] freq = new int[10];
        int length = 0;
        while (n > 0) {
            int digit = n % 10;
            digits.add(digit);
            freq[digit]++;
            length++;
            n = n / 10;
        }
        return new Digits(digits, freq, length);
    }

    private static class Digits {
        Set<Integer> digits;
        int[] freq;
        int length;

        public Digits(Set<Integer> digits, int[] freq, int length) {
            this.digits = digits;
            this.freq = freq;
            this.length = length;
        }
    }

    private static class Multiple {
        List<Integer> values;

        public Multiple(List<Integer> values) {
            this.values = values;
        }

        @Override
        public String toString() {
            return "{" +
                    "values=" + values +
                    '}';
        }
    }
}
