package com.smart.tkl.lib;

import com.smart.tkl.lib.utils.MathUtils;

public class CycleDetection {

    public static void main(String[] args) {
        System.out.println("Period Floyd: " + floyd(3316));
        System.out.println("Period Brent: " + brent(3316));
    }

    static Period floyd(long n) {
        long x0 = 2;
        long tortoise = f(x0, n);
        long hare = f(f(x0, n), n);
        while (tortoise != hare) {
            tortoise = f(tortoise, n);
            hare = f(f(hare, n), n);
        }

        int m = 0;
        tortoise = x0;
        while (tortoise != hare) {
            tortoise = f(tortoise, n);
            hare = f(hare, n);
            m++;
        }

        int period = 1;
        tortoise = f(tortoise, n);
        while (tortoise != hare) {
            period++;
            tortoise = f(tortoise, n);
        }

        return new Period(m, period);
    }

    static Period brent(long n) {
        long x0 = 2;
        int power = 1, period = 1;
        long tortoise = x0;
        long hare = f(x0, n);
        while (tortoise != hare) {
            if(power == period) {
                tortoise = hare;
                power *= 2;
                period = 0;
            }
            hare = f(hare, n);
            period++;
        }

        tortoise = hare = x0;
        for(int i = 0; i < period; i++) {
            hare = f(hare, n);
        }

        int m = 0;
        while (tortoise != hare) {
              tortoise = f(tortoise, n);
              hare = f(hare, n);
              m++;
        }
        return new Period(m, period);
    }

    static long f(long x,long  n) {
        return f(x, 1, n);
    }

    static long f(long x, long c, long n) {
        return (MathUtils.moduloMultiply(x, x, n) + c) % n;
    }

    static class Period {
        int m, period;

        public Period(int m, int period) {
            this.m = m;
            this.period = period;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "m=" + m +
                    ", period=" + period +
                    '}';
        }
    }

}
