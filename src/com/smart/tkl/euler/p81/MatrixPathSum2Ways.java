package com.smart.tkl.euler.p81;


import com.smart.tkl.euler.MatrixFileReader;
import com.smart.tkl.utils.GenericUtils;

import java.io.IOException;
import java.util.Arrays;

public class MatrixPathSum2Ways {

    private static final String MATRIX_80_80_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p81\\p081_matrix.txt";

    private static final int[][] TEST_MATRIX = {
            {131, 673, 234, 103, 18},
            {201, 96,  342, 965, 150},
            {630, 803, 746, 422, 111},
            {537, 699, 497, 121, 956},
            {805, 732, 524, 37,  331}
    };

    private final int[][] matrix;

    public static void main(String[] args) {
        MatrixPathSum2Ways matrixPathSum2Ways = new MatrixPathSum2Ways(TEST_MATRIX);
        System.out.println("Min path sum of test matrix: " + matrixPathSum2Ways.getMinimumSum());

        try {
            matrixPathSum2Ways = new MatrixPathSum2Ways(MATRIX_80_80_FILE_PATH);
            long time1 = System.currentTimeMillis();
            int sum = matrixPathSum2Ways.getMinimumSum();
            long time2 = System.currentTimeMillis();
            System.out.println("Min path sum of 80x80 matrix: " + sum + ". => time ms: " + (time2 - time1));
        } catch (IOException e) {
            System.out.println("Error while reading matrix file: " + e);
        }
    }

    public MatrixPathSum2Ways(String matrixFilePath) throws IOException {
        this.matrix = new MatrixFileReader(matrixFilePath).readMatrix();
    }

    public MatrixPathSum2Ways(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getMinimumSum() {
        int[][] tab = GenericUtils.clone(this.matrix);
        return getMinimumSum(tab);
    }

    private int getMinimumSum(int[][] tab) {
        int lastRow = tab.length - 1;
        int lastColumn = tab[0].length - 1;
        for(int i = lastColumn - 1; i >= 0; i--) {
            tab[lastRow][i] += tab[lastRow][i + 1];
        }
        for(int i = lastRow - 1; i >= 0; i--) {
            tab[i][lastColumn] += tab[i + 1][lastColumn];
        }

        for(int i = lastRow - 1; i >= 0; i--) {
            for(int j = lastColumn - 1; j >= 0; j--) {
                tab[i][j] = tab[i][j] + Math.min(tab[i + 1][j], tab[i][j + 1]);
            }
        }
        return tab[0][0];
    }

    private static void printMatrix(int[][] t) {
        for (int[] row : t) {
            System.out.println(Arrays.toString(row));
        }
    }
}
