package com.smart.tkl.euler.p63;

import java.util.ArrayList;
import java.util.List;

public class PowerfulDigitCounts {

    public static void main(String[] args) {
        List<Long> numbers = getNumbers(19);
        System.out.println(numbers);
    }

    private static List<Long> getNumbers(int n) {
        double pow = (n - 1) / (double)n;
        long min = (long)Math.ceil(Math.pow(10, pow));
        List<Long> result = new ArrayList<>();
        for(long i = min; i < 10; i++) {
            long val = pow(i, n);
            result.add(val);
        }
        return result;
    }

    private static long pow(long val, int pow) {
        long result = 1;
        for(int i = 1; i <= pow; i++) {
            result *= val;
        }
        return result;
    }

}
