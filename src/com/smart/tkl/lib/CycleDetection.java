package com.smart.tkl.lib;

import com.smart.tkl.lib.utils.MathUtils;

public class CycleDetection {

    public static void main(String[] args) {
        System.out.println("Period Floyd: " + floyd(3316));
        System.out.println("Period Brent: " + brent(3316));
    }

    public static Period floyd(long n) {
        return floyd(n, 1);
    }

    public static Period floyd(long n, long c) {
        long x0 = 2;
        long tortoise = f(x0, c, n);
        long hare = f(f(x0, c, n), n);
        while (tortoise != hare) {
            tortoise = f(tortoise, c, n);
            hare = f(f(hare, c, n), c, n);
        }

        int m = 0;
        tortoise = x0;
        while (tortoise != hare) {
            tortoise = f(tortoise, c, n);
            hare = f(hare, c, n);
            m++;
        }

        int period = 1;
        tortoise = f(tortoise, c, n);
        while (tortoise != hare) {
            period++;
            tortoise = f(tortoise, c, n);
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
        int m, length;

        public Period(int m, int period) {
            this.m = m;
            this.length = period;
        }

        @Override
        public String toString() {
            return "Period{" +
                    "m=" + m +
                    ", length=" + length +
                    '}';
        }
    }

}
