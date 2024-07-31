package com.smart.tkl.euler.p137;

import com.smart.tkl.lib.linear.MatrixUtils;

public class FibGoldenNuggets2 {

    private static final long[][] MATRIX = new long[][] {
            {0, 1},
            {1, 1}
    };

    private static final long[][] INITIAL = new long[][] {
            {1},
            {1}
    };

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 9) + 7;
        System.out.println(findNthGoldenNugget(1, mod));
        System.out.println(findNthGoldenNugget(10, mod));
    }

    public static long findNthGoldenNugget(long n, long mod) {
        long[][] poweredMatrix = MatrixUtils.pow(MATRIX, 2 * n - 2, mod);
        long[][] solution = MatrixUtils.multiply(poweredMatrix, INITIAL, mod);

        long f2n = solution[1][0];
        long f2n1 = (solution[0][0] + solution[1][0]) % mod;
        return (f2n * f2n1) % mod;
    }
}
