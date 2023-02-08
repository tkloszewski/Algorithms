package com.smart.tkl.euler.p159;

public class FactorizationDigitalRootSum {

    private final int limit;

    public FactorizationDigitalRootSum(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FactorizationDigitalRootSum factorizationDigitalRootSum = new FactorizationDigitalRootSum(1000000);
        long sum = factorizationDigitalRootSum.findSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long findSum() {
        long sum = 0;
        int[] maxDigitalSumRoots = maxDigitalSumRoot(limit);
        for(int i = 0; i < maxDigitalSumRoots.length - 1; i++) {
            sum += maxDigitalSumRoots[i];
        }
        return sum;
    }

    private static int[] maxDigitalSumRoot(int n) {
        int[] result = new int[n + 1];
        for(int i = 2; i <= n; i++) {
            result[i] = digitalRoot(i);
        }

        int limit = (int)Math.sqrt(n);

        for(int i = 2; i <= limit; i++) {
            for(int p = i * i; p <= n; p = p + i) {
                int div = p / i;
                int sum = result[i] + result[div];
                if(sum > result[p]) {
                   result[p] = sum;
                }
            }
        }

        return result;
    }

    private static int digitalRoot(int n) {
        int sum = 0;
        while (n >= 10) {
            while (n > 0) {
                sum += n % 10;
                n = n / 10;
            }
            n = sum;
            sum = 0;
        }
        return n;
    }
}

