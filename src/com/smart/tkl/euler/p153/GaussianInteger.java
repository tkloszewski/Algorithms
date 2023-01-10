package com.smart.tkl.euler.p153;

import com.smart.tkl.utils.MathUtils;

public class GaussianInteger {

    private final int limit;

    public GaussianInteger(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ComplexNumber sum = new GaussianInteger(100000000).calculateSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum= " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public ComplexNumber calculateSum() {
        long imaginarySum = 0L;
        for(int a = 1; a < Math.sqrt(this.limit); a++) {
            for(int b = a + 1; b * b + a * a <= this.limit; b++) {
                if (MathUtils.GCD(a, b) == 1) {
                    int d = b * b +  a * a;
                    long divisorSum = 2L * (a + b);
                    long multipliersSum = sumOfMultipliers(d, this.limit);
                    imaginarySum += divisorSum * multipliersSum;
                }
            }
        }
        if(this.limit >= 2) {
           imaginarySum += 2 * sumOfMultipliers(2, this.limit);
        }
        long realDivisorsSum = sumOfDivisorsUpTo(this.limit);

        return new ComplexNumber(realDivisorsSum, imaginarySum);
    }

    private static long sumOfMultipliers(int d, int limit) {
       int n = limit / d;
       return sumOfDivisorsUpTo(n);
    }

    private static long sumOfDivisorsUpTo(int n) {
        long sum = 0;
        int left = 1, right = n;
        while (left <= right) {
            int d1 = n / left;
            int d2 = n / (left + 1);

            sum += (long)d1 * left;
            if (left != right) {
                sum += diffSum(d1, d2) * left;
            }

            right = d2;
            left++;
        }
        return sum;
    }

    private static long diffSum(int m, int n) {
        return ((long)(m - n) * (m + n + 1)) / 2;
    }

    private static class ComplexNumber {
        final long real;
        final long imaginary;
        final long sum;

        public ComplexNumber(long real, long imaginary) {
            this.real = real;
            this.imaginary = imaginary;
            this.sum = real + imaginary;
        }

        @Override
        public String toString() {
            return "ComplexNumber{" +
                    "real=" + real +
                    ", imaginary=" + imaginary +
                    ", sum=" + sum +
                    '}';
        }
    }

}
