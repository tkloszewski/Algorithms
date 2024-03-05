package com.smart.tkl.euler.p103.hk;

import java.util.Arrays;

public class SpecialSubOptimumSet {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int n = 11;
        long mod = 715827881;
        long[] subOptimalSet = getSubOptimalSet(n, mod);
        long time2 = System.currentTimeMillis();
        System.out.println("Suboptimal set: " + Arrays.toString(subOptimalSet));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long[] getSubOptimalSet(int n, long mod) {
        long[] result = new long[n];
        long[] midValues = getMidValues(n, mod);
        long value = 0;
        for(int i = n - 1, k = 0; i >= 0; i--, k++) {
            value += midValues[i];
            result[k] = value;
        }
        return result;
    }

    private static long[] getMidValues(int n, long mod) {
        long[] midValues = new long[n + 1];
        midValues[0] = 1;
        midValues[1] = 1;
        long midValue = 1;
        for(int i = 2; i <= n; i++) {
            if(i % 2 == 0) {
                midValue += midValues[i - 1];
                midValue = midValue % mod;
                midValues[i] = midValue;
            }
            else {
                int idx = i / 2 - 1;
                midValue -= midValues[idx];
                if(midValue < 0) {
                   midValue = mod + midValue;
                }
                midValue += midValues[i - 1];
                midValue = midValue % mod;
                midValues[i] = midValue;
            }
        }
        return midValues;
    }
}