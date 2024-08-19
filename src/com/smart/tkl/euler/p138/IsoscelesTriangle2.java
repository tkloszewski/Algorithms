package com.smart.tkl.euler.p138;

import com.smart.tkl.lib.linear.MatrixUtils;

public class IsoscelesTriangle2 {

    private static final long[][] MATRIX = new long[][] {
            {18, -1},
            {1,   0}
    };

    private static final long[][] INITIAL = new long[][] {
            {1},
            {1}
    };

    private static final long MOD = (long) Math.pow(10, 9) + 7;
    private static final long INV_MOD = 562500004;

    /*17, 322, 5795, 104004, 1866293, 33489286, 600940871, 10783446408, 193501094489,
      3472236254410, 62306751484907, 1118049290473932, 20062580477045885, 360008399296352014
    */
    public static void main(String[] args) {
        System.out.println(findNthSum(1));
        System.out.println(findNthSum(2));
        System.out.println(findNthSum(3));
        System.out.println(findNthSum(4));
        System.out.println(findNthSum(5));
        System.out.println(findNthSum(6));
        System.out.println(findNthSum(7));

        long time1 = System.currentTimeMillis();
        long T = 100000;
        for(long i = 1, k = 0; i < Long.MAX_VALUE - 100 && k < T; i += 10000000000000L, k++) {
            findNthSum(i);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long findNthSum(long n) {
        long[][] poweredMatrix = MatrixUtils.pow(MATRIX, n + 1, MOD);
        long[][] solution = MatrixUtils.multiply(poweredMatrix, INITIAL, MOD);

        long an1 = solution[0][0];
        long an = solution[1][0];

        long t1 = an1 - an - 16;
        if(t1 < 0) {
           t1 += MOD;
        }

        return (t1 * INV_MOD) % MOD ;
    }
}
