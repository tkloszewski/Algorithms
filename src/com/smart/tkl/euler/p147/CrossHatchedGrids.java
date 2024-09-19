package com.smart.tkl.euler.p147;

import java.util.ArrayList;
import java.util.List;

public class CrossHatchedGrids {

    private final int maxWidth;
    private final int maxHeight;

    private final long[][] diagonalCache;

    public CrossHatchedGrids(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        int max = Math.max(this.maxWidth, this.maxHeight);
        this.diagonalCache = new long[max + 1][max + 1];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long count = new CrossHatchedGrids(300, 300).countAll();
        long time2 = System.currentTimeMillis();
        System.out.println("Count regular and diagonal: " + count);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public long countAll() {
        long result = 0;
        for(int m = 1; m <= maxWidth; m++) {
            for(int n = 1; n <= maxHeight; n++) {
                long regularAndDiagonalCount = countRegularAndDiagonal(m, n);
                result += regularAndDiagonalCount;
            }
        }
        return result;
    }

    private long countRegularAndDiagonal(int m, int n) {
        return countRegular(m, n) + countDiagonal(m, n);
    }

    private long countDiagonal(int m, int n) {
        if(diagonalCache[m][n] != 0) {
            return diagonalCache[m][n];
        }
        long result = CrossHatchedGridUtils.doCountDiagonal(m, n);
        diagonalCache[m][n] = result;
        diagonalCache[n][m] = result;
        return result;
    }

    private static long countRegular(int m, int n) {
        return  (m * n * ((long) m * n  + m + n + 1)) / 4;
    }
}
