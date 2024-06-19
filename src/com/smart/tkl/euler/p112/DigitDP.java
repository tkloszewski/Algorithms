package com.smart.tkl.euler.p112;

import com.smart.tkl.lib.utils.MathUtils;

public class DigitDP {

    public static void main(String[] args) {
        long[] digitSums = createDigitsSums(10);
        long sumInRange = sumInRange(123, 1024, digitSums);
        System.out.println("Sum in range: " + sumInRange);
    }

    private static long[] createDigitsSums(int length) {
        long[] result = new long[length + 1];
        result[1] = 45;
        long pow = 10;
        for(int digitsLength = 2; digitsLength <= length; digitsLength++, pow *= 10) {
            result[digitsLength] = 10 * result[digitsLength - 1] + pow * 45;
        }
        return result;
    }

    private static long sumInRange(long a, long b, long[] digitsSum) {
        long sum1 = sumOfDigits(a, digitsSum);
        long sum2 = sumOfDigits(b, digitsSum);
        return sum2 - sum1 + toDigitSum(a);
    }

    private static long sumOfDigits(long n, long[] digitsSum) {
        long sum = 0;
        int idx = 1;
        long value = 0, pow = 1;
        while (n > 0) {
            int digit = (int)(n % 10);

            long term1 = digit * digitsSum[idx - 1];
            long term2 = arithmeticSum(idx > 1 ? digit - 1 : digit) * pow;
            long term3 = idx > 1 ? digit * (value + 1) : 0;

            //System.out.println("(" + term1 + " + " + term2 + " + " + term3 + ")");

            sum += (term1 + term2 + term3);

            value += pow * digit;
            pow *= 10;
            idx++;
            n = n / 10;
        }
        return sum;
    }

    private static long arithmeticSum(int n) {
        return n >= 0 ? (n * (n + 1L)) / 2 : 0;
    }

    private static long toDigitSum(long n) {
        long sum = 0;
        while (n > 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    private static long bruteForceSum(long limit) {
        long sum = 0;
        for(long n = 1; n <= limit; n++) {
            sum += MathUtils.sumOfDigits(String.valueOf(n));
        }
        return sum;
    }

}
