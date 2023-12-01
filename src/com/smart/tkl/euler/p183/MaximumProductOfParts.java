package com.smart.tkl.euler.p183;

import com.smart.tkl.lib.utils.MathUtils;

public class MaximumProductOfParts {

    private final int N;

    public MaximumProductOfParts(int n) {
        N = n;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int N = 10000;
        MaximumProductOfParts maximumProductOfParts = new MaximumProductOfParts(N);
        long sum = maximumProductOfParts.sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sum() {
        long result = 0;

        for(int n = 5; n <= N; n++) {
            double x = n / Math.E;
            int k = (int)x;

            double value1 = (k + 1) * Math.log10(k + 1);
            double value2 = k * Math.log10(k) + Math.log10(n);

            k = value1 > value2 ? k : k + 1;
            if(!isTerminatedDecimal(n, k)) {
                result += n;
            }
            else {
                result -= n;
            }
        }

        return result;
    }

    private static boolean isTerminatedDecimal(long n, long k) {
        k = k / MathUtils.GCD(n, k);
        while (k % 2 == 0) {
            k = k / 2;
        }
        while (k % 5 == 0) {
            k = k / 5;
        }
        return k == 1;
    }
}