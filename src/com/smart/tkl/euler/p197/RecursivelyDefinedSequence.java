package com.smart.tkl.euler.p197;

public class RecursivelyDefinedSequence {

    private static final double ONE_BILLIONTH = Math.pow(10, -9);

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long k = (long)Math.pow(10, 12);
        double foundValue2 = findValue(-1,  30.403243784, k);
        long time2 = System.currentTimeMillis();
        System.out.println("Found value2: " + foundValue2);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static String toString(double value) {
        return String.format("%.9f", value);
    }

    /*Tortoise and hare algorithm*/
    public static double findValue(double v0, double b, long k) {
        if(k == 0) {
           return v0 + calc(v0, b);
        }
        double v1 = calc(v0, b);
        double v2 = calc(v1, b);

        long i = 1;
        while (i < k && v1 != v2) {
            v1 = calc(v1, b);
            v2 = calc(calc(v2, b), b);
            i++;
        }
        if(i == k) {
           return v1;
        }

        v1 = v0;
        long startPeriod = 0;
        while (v1 != v2 && startPeriod < k) {
            v1 = calc(v1, b);
            v2 = calc(v2, b);
            startPeriod++;
        }

        //searched value was before start period
        if(startPeriod == k) {
           return v1;
        }

        long period = 1;
        v2 = calc(v1, b);
        while (v1 != v2) {
           v2 = calc(v2, b);
           period++;
        }

        long remaining = (k - startPeriod) % period;
        v2 = calc(v1, b);
        for(int j = 1; j < remaining; j++) {
            v2 = calc(v2, b);
        }
        return v2 + calc(v2, b);
    }

    private static double calc(double x, double b) {
        return ONE_BILLIONTH * Math.floor(Math.pow(2, b - x * x));
    }
}
