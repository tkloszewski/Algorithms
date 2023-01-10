package com.smart.tkl.euler.p153;

import com.smart.tkl.Stack;

public class GaussianInteger {

    private final int limit;

    public GaussianInteger(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int limit = 100000000;
        GaussianInteger gaussianInteger = new GaussianInteger(limit);
        ComplexNumber sum = gaussianInteger.calculateSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum= " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public ComplexNumber calculateSum() {
        long imaginaryDivisorsSum = calculateImaginarySum();
        long realDivisorsSum = sumOfDivisorsUpTo(this.limit);
        return new ComplexNumber(realDivisorsSum, imaginaryDivisorsSum);
    }

    private long calculateImaginarySum() {
        if (this.limit >= 2) {
            int sqrtLimit = (int)Math.sqrt(this.limit);
            long imaginarySum = 2 * sumOfMultipliers(2, this.limit);
            imaginarySum += generateSumFromCoPrimes(2, 1, this.limit, sqrtLimit);
            imaginarySum += generateSumFromCoPrimes(3, 1, this.limit, sqrtLimit);
            return imaginarySum;
        }
        return 0;
    }

    private long generateSumFromCoPrimes(int a0, int b0, int limit, int sqrtLimit) {
        long sum = 0;
        Stack<CoPrime> stack = new Stack<>();
        if (isValidPair(a0, b0, limit, sqrtLimit)) {
            stack.push(new CoPrime(a0, b0));
        }

        while (!stack.isEmpty()) {
            CoPrime pair = stack.pop();
            int a = pair.a;
            int b = pair.b;

            int d = a * a + b * b;
            long divisorSum = 2L * (a + b);
            long multipliersSum = sumOfMultipliers(d, limit);
            sum += divisorSum * multipliersSum;

            if(isValidPair(2 * a - b, a, limit, sqrtLimit)) {
               stack.push(new CoPrime(2 * a - b, a));
            }
            if(isValidPair(2 * a + b, a, limit, sqrtLimit)) {
                stack.push(new CoPrime(2 * a + b, a));
            }
            if(isValidPair(a + 2 * b, b, limit, sqrtLimit)) {
                stack.push(new CoPrime(a + 2 * b, b));
            }
        }

        return sum;
    }

    private boolean isValidPair(int a, int b, int limit, int sqrtLimit) {
        return a <= sqrtLimit && a * a + b * b <= limit;
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

    private static class CoPrime {
        final int a;
        final int b;

        public CoPrime(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }
}
