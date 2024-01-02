package com.smart.tkl.euler.p82;

import com.smart.tkl.euler.MatrixFileReader;

import java.io.IOException;
import java.util.Arrays;

public class MatrixPathSum3Ways {

    private static final String MATRIX_80_80_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p82\\p082_matrix.txt";

    private static final long[][] TEST_MATRIX = {
            {131, 673, 234, 103, 18},
            {201, 96,  342, 965, 150},
            {630, 803, 746, 422, 111},
            {537, 699, 497, 121, 956},
            {805, 732, 524, 37,  331}
    };

    private final long[][] matrix;

    public static void main(String[] args) {
        MatrixPathSum3Ways matrix = new MatrixPathSum3Ways(TEST_MATRIX);
        long minPath = matrix.getMinPath();
        System.out.println("Min path for test matrix: " + minPath);

        try {
            matrix = new MatrixPathSum3Ways(MATRIX_80_80_FILE_PATH);
            long time1 = System.currentTimeMillis();
            minPath = matrix.getMinPath();
            long time2 = System.currentTimeMillis();
            System.out.println("Min path for 80x80 matrix: " + minPath + "Time in ms: " + (time2 - time1));
        } catch (IOException e) {
            System.out.println("Error reading matrix file: " + e);
        }
    }

    public MatrixPathSum3Ways(String matrixFilePath) throws IOException {
        this.matrix = new MatrixFileReader(matrixFilePath).readMatrix();
    }

    public MatrixPathSum3Ways(long[][] matrix) {
        this.matrix = matrix;
    }

    public long getMinPath() {
        long[][] dp = getPathMatrix(this.matrix);
        long minElem = Long.MAX_VALUE;
        for (long[] row : dp) {
            minElem = Math.min(minElem, row[0]);
        }
        return minElem;
    }

    private long[][] getPathMatrix(long[][] a) {
        long[][] dp = new long[a.length][a[0].length];
        int lastColumn = a[0].length - 1;
        for(int i = 0; i < dp.length; i++) {
            dp[i][lastColumn] = a[i][lastColumn];
        }

        for(int j = lastColumn - 1; j >= 0; j--) {
            for(int i = 0; i < dp.length; i++) {
                long min = getMinPathToPrevColumnForElem(i, j, dp, a);
                dp[i][j] = min;
            }
        }

        return dp;
    }

    private long getMinPathToPrevColumnForElem(int rowIdx, int columnIdx, long[][] dp, long[][] a) {
        long min = a[rowIdx][columnIdx] + dp[rowIdx][columnIdx + 1];
        long s = a[rowIdx][columnIdx];

        for(int i = rowIdx; i <= a.length - 2; i++) {
            if(s + a[i + 1][columnIdx] + dp[i + 1][columnIdx + 1] < min) {
               min = s + a[i + 1][columnIdx] + dp[i + 1][columnIdx + 1];
            }
            else if(s + a[i + 1][columnIdx] > min) {
                break;
            }
            s += a[i + 1][columnIdx];
        }

        s = a[rowIdx][columnIdx];

        for(int i = rowIdx; i >= 1; i--) {
            if(s + a[i - 1][columnIdx] + dp[i - 1][columnIdx + 1] < min) {
                min = s + a[i - 1][columnIdx] + dp[i - 1][columnIdx + 1];
            }
            else if(s + a[i - 1][columnIdx] > min) {
                break;
            }
            s += a[i - 1][columnIdx];
        }

        return min;
    }

    private static void printMatrix(int[][] t) {
        for (int[] row : t) {
            System.out.println(Arrays.toString(row));
        }
    }
}
