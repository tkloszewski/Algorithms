package com.smart.tkl.euler.p114;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import com.smart.tkl.lib.linear.MatrixUtils;

public class BlockCombinations {

    private final int length;

    public BlockCombinations(int length) {
        assert length >= 3;
        this.length = length;
    }

    public static void main(String[] args) {
        int length = 50;
        long time1 = System.currentTimeMillis();
        BlockCombinations blockCombinations = new BlockCombinations(50);
        long count = blockCombinations.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Number of combinations: " + count);
        System.out.println("Solution took in ms: " + (time2 - time1));

        count = countUsingMatrixExp(length, 3);
        System.out.println("Number of combinations by matrix exponentiation: " + count);
    }

    public long count() {
        long[] solutions = new long[this.length + 1];
        for(int i = 0; i <= 2; i++) {
            solutions[i] = 1;
        }
        solutions[3] = 2;
        for(int pos = 4; pos <= this.length; pos++) {
            solutions[pos] += solutions[pos - 1];
            for(int tileLength = 3; tileLength <= pos - 1; tileLength++) {
                solutions[pos] += solutions[pos - tileLength - 1];
            }
            solutions[pos]++;
        }

        return solutions[this.length];
    }

    public static long countUsingMatrixExp(long length, int m) {
          if(length < m) {
             return 1;
          }

          long[][] initial = resolveInitialVector(m);
          long[][] matrix = resolveMatrix(m);

          long mod = Long.MAX_VALUE;
          matrix = MatrixUtils.pow(matrix, length - m, mod);
          long[][] result = MatrixUtils.multiply(matrix, initial, mod);

          long sum = 0;
          for(int i = 0; i < m + 1; i++) {
              sum += result[i][0];
              sum = sum % mod;
          }
          return sum;
    }

    private static long[][] resolveInitialVector(int m) {
        long[][] initial = new long[m + 1][1];
        initial[0][0] = 1;
        initial[m][0] = 1;
        return initial;
    }

    private static long[][] resolveMatrix(int m) {
        long[][] matrix = new long[m + 1][m + 1];
        matrix[0][0] = 1;
        matrix[0][m] = 1;
        matrix[m][m - 1] = 1;
        matrix[m][m] = 1;
        for(int i = 1, j = 0; i <= m - 1; i++, j++) {
            matrix[i][j] = 1;
        }
        return matrix;
    }


}
