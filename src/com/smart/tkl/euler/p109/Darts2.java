package com.smart.tkl.euler.p109;

import com.smart.tkl.lib.linear.MatrixUtils;

public class Darts2 {

    private final long mod, limit;

    public Darts2(long mod, long limit) {
        this.mod = mod;
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long mod = (long)Math.pow(10, 9) + 9;
        long limit = 4L;
        Darts2 darts = new Darts2(mod, limit);
        long count = darts.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long[][] initialVector = new long[61][1];
        initialVector[0][0] = 1;

        long[][] matrix = resolveMatrix();

        long pow = limit;

        long[][] a = MatrixUtils.pow(matrix, pow, this.mod);
        long[][] result = MatrixUtils.multiply(a, initialVector, this.mod);

        return result[60][0];
    }

    private static long[][] resolveMatrix() {
        long[][] result = new long[61][61];

        long[] scoreFreq = new long[61];
        scoreFreq[25] = 1;
        scoreFreq[50] = 1;
        for(int score = 1; score <= 20; score++) {
           scoreFreq[score]++;
           scoreFreq[2 * score]++;
           scoreFreq[3 * score]++;
        }

        for(int column = 0; column <= 59; column++) {
            result[0][column] = scoreFreq[column + 1];
        }

        for(int row = 1; row <= 59; row++) {
            result[row][row - 1] = 1;
        }

        for(int doubleScore = 2; doubleScore <= 40; doubleScore += 2) {
            result[60][doubleScore - 1] = 1;
        }

        result[60][49] = 1;
        result[60][60] = 1;

        return result;
    }
}
