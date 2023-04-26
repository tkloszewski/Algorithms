package com.smart.tkl.lib.linear;

import com.smart.tkl.lib.combinatorics.permutation.SwapPermutationGenerator;
import com.smart.tkl.lib.combinatorics.permutation.PermutationListener;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;



public class Matrix {

    public enum DetMethod {LAPLACE, GAUSS, PERMUTATION, CHIO}

    private double[][] table;
    private DetMethod detMethod = DetMethod.LAPLACE;

    public Matrix(double[][] table) {
        this.table = table;
    }

    public static void main(String[] args) {

        double[][] table1 = {
                { 1,  1, -1,  0},
                { 2, -1,  0,  1},
                { 9,  2,  1, -1},
                { 0, -1,  2,  1},

        };
        Matrix matrix1 = new Matrix(table1);
        double determinant1 = matrix1.determinant();

        double[][] table3_3 = {
                { 3,  4, -5},
                { 2, -3,  3},
                { 7, -2,  1}
        };

        Matrix matrix3_3 = new Matrix(table3_3);
        Matrix complement = matrix3_3.complement();

        System.out.println("Matrix 1 complement");
        matrix1.complement().printGrid();

        System.out.println("Matrix_3_3 determinant: " + matrix3_3.determinant());

        System.out.println("Complement");
        complement.printGrid();

        System.out.println("Adjunct");
        Matrix adjunct = complement.transpose();
        adjunct.printGrid();

        Matrix multiplied = matrix3_3.multiplyBy(adjunct);
        System.out.println("Multiplied");
        multiplied.printGrid();

        Matrix adjunct2 = matrix1.adjunct();
        System.out.println("Adjunct of matrix1:");
        adjunct2.printGrid();
        Matrix multiplied2 = matrix1.multiplyBy(adjunct2);
        System.out.println("Multiplied of adjunct");
        multiplied2.printGrid();


        System.out.println("Calculating determinants...");
        double[][] table2 = {
                {1,  3,  2,  1,  4},
                {2,  1,  5,  1,  2},
                {3,  4,  1,  0,  1},
                {2,  1,  1,  5,  2},
                {3, -1,  1, -1,  1}
        };
        Matrix matrix2 = new Matrix(table2);
        double determinant2 = matrix2.determinant();

        double detGauss = matrix2.detGauss();
        double detPerm = matrix2.detPermutation();
        double detChio = matrix2.detChio();


        System.out.println("Determinant1: " + determinant1);
        System.out.println("Det Laplace: " + determinant2);
        System.out.println("Det Gauss: " + detGauss);
        System.out.println("Det Perm: " + detPerm);
        System.out.println("Det Chio: " + detChio);
    }

    public int rowsCount() {
        return this.table.length;
    }

    public int colsCount() {
        return this.table[0].length;
    }

    public double valueAt(int i, int j) {
        return this.table[i][j];
    }

    //this * other
    public Matrix multiplyBy(Matrix other) {
       if(this.colsCount() != other.rowsCount()) {
           throw new RuntimeException("Cannot multiply. Matrix sizes are incompatible.");
       }
       double[][] result = new double[this.rowsCount()][other.colsCount()];

       for(int m = 0; m < result.length; m ++) {
           for(int n = 0; n < result[0].length; n++) {
               double c = 0;
               for(int i = 0; i < this.colsCount(); i++) {
                   c += this.table[m][i] * other.valueAt(i, n);
               }
               result[m][n] = c;
           }
       }

       return new Matrix(result);
    }

    public Matrix multiplyBy(double a) {
        double[][] result = new double[this.table.length][this.table[0].length];
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] =  a * this.table[i][j];
            }
        }
        return new Matrix(result);
    }

    public Matrix inverse() {
        double determinant = this.determinant();
        if(determinant == 0) {
            throw new RuntimeException("There is no inverse of this matrix. Determinant is 0");
        }
        Matrix adjunct = this.adjunct();
        return adjunct.multiplyBy(1/determinant);
    }

    public Matrix adjunct() {
        Matrix complement = this.complement();
        return complement.transpose();
    }

    public Matrix complement() {
        double[][] result = new double[this.table.length][this.table[0].length];
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                Matrix minor = minor(i,j);
                result[i][j] =  Math.pow(-1, i + j) * minor.determinant();
            }
        }
        return new Matrix(result);
    }

    public Matrix minor(int i, int j) {
        return new Matrix(getMinorTable(this.table, i , j));
    }

    public Matrix transpose() {
        double[][] result = new double[this.table[0].length][this.table.length];
        for(int i = 0; i < result[0].length; i++) {
            for(int j = 0; j < result.length; j++) {
                result[i][j] =  this.table[j][i];
            }
        }
        return new Matrix(result);
    }

    public Matrix swapRows(int m, int n) {
        if(m >= this.rowsCount() || n >= this.rowsCount()) {
            return this;
        }
        swapRows(this.table, m, n);
        return this;
    }

    public double determinant() {
        return determinant(this.detMethod);
    }

    public double determinant(DetMethod detMethod) {
        if(!isSquare(this.table)) {
            return 0;
        }
        if(detMethod.equals(DetMethod.LAPLACE)) {
            return detLaplace();
        }
        if(detMethod.equals(DetMethod.GAUSS)) {
            return detGauss();
        }
        if(detMethod.equals(DetMethod.PERMUTATION)) {
            return detPermutation();
        }
        if(detMethod.equals(DetMethod.CHIO)) {
            return detChio();
        }
        return 0;
    }

    public double detGauss() {
        int swapCount = 0;
        double scaleFactor = 1.0;
        double[][] workTable = new double[this.rowsCount()][this.colsCount()];
        for(int i = 0; i < this.rowsCount(); i++) {
            System.arraycopy(this.table[i], 0, workTable[i], 0, this.colsCount());
        }

        for(int i = 0, columnIndex = 0; i < this.rowsCount() - 1; i++, columnIndex++) {
            int nonZeroIdx = findFirstRowWithNonZero(workTable, i, columnIndex);
            if(nonZeroIdx == -1) {
               continue;
            }
            else if(nonZeroIdx != i) {
                swapRows(workTable, i, nonZeroIdx);
                swapCount++;
            }

            int oneIdx = findRowWithOne(workTable, i+1, columnIndex);
            if(oneIdx != -1) {
                swapRows(workTable, i, oneIdx);
                swapCount++;
            }
            else {
                double firstValue = workTable[i][columnIndex];
                if (firstValue != 1) {
                    for(int k = columnIndex; k < this.colsCount(); k++) {
                        workTable[i][k] = workTable[i][k]/firstValue;
                    }
                    scaleFactor = scaleFactor * firstValue;
                }
            }

            for(int j = i+1; j < this.rowsCount(); j++) {
                double rowValue2 = workTable[j][columnIndex];
                if(rowValue2 == 0) {
                   continue;
                }

                for(int k = columnIndex; k < this.colsCount(); k++) {
                    workTable[j][k] = workTable[j][k] - rowValue2 * workTable[i][k];
                }
            }
        }

        double result = 1 * Math.pow(-1, swapCount) * scaleFactor;
        for(int i = 0; i < this.rowsCount(); i++) {
            result = result * workTable[i][i];

        }
        return new BigDecimal(result).round(MathContext.DECIMAL32).doubleValue();
    }

    public double detLaplace() {
        return detLaplace(this.table, indexTable(table.length),  0);
    }

    public double detPermutation() {
        MatrixPermutationListener permutationListener = new MatrixPermutationListener(this.table);
        SwapPermutationGenerator permutationGenerator = new SwapPermutationGenerator(permutationListener);
        permutationGenerator.generate(this.rowsCount());
        return permutationListener.getDeterminant();
    }

    public void printGrid() {
        doPrintGrid(this.table);
    }

    private double detLaplace(double[][] t, int[] columns, int rowIdx) {
        double result = 0;
        int[] validColumns = getValidIndexes(columns);

        if(validColumns.length == 1) {
            result = t[rowIdx][validColumns[0]];
        }
        else if(validColumns.length == 2) {
            result = t[rowIdx][validColumns[0]] * t[rowIdx + 1][validColumns[1]] -
                    t[rowIdx][validColumns[1]] * t[rowIdx + 1][validColumns[0]];
        }
        else {
            for (int i = 0; i < validColumns.length; i++) {
                int colIdx = validColumns[i];
                columns[colIdx] = 0;
                if (t[rowIdx][colIdx] != 0) {
                    result += (int) Math.pow(-1, i) * t[rowIdx][colIdx] * detLaplace(t, columns, rowIdx + 1);
                }
                columns[colIdx] = 1;
            }
        }
        return result;
    }

    private double detChio() {
        if(this.table[0].length == 1) {
            return this.table[0][0];
        }
        return detChio(this.table);
    }

    private double detChio(double[][] matrix) {
        int n = matrix[0].length;
        if(n == 2) {
           return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int sign = 1;
        if(matrix[0][0] == 0) {
           int firstNonZeroIdx = findFirstRowWithNonZero(matrix, 0, 0);
           if(firstNonZeroIdx == -1) {
              return 0;
           }
           swapRows(matrix, 0, firstNonZeroIdx);
           sign = -1;
        }
        double[][] chioMatrix = createChioMatrix(matrix);
        double firstTerm = sign /(Math.pow(matrix[0][0], n - 2));
        return firstTerm * detChio(chioMatrix);
    }

    private double[][] createChioMatrix(double[][] matrix) {
        if(matrix[0].length ==2 ) {
           return matrix;
        }
        int n = matrix[0].length - 1;
        double[][] chioMatrix = new double[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                chioMatrix[i][j] = matrix[0][0] * matrix[i+1][j+1] - matrix[0][j+1] * matrix[i+1][0];
            }
        }
        return chioMatrix;
    }

    private int[] getValidIndexes(int[] t) {
        int[] result = new int[getValidCount(t)];
        for(int i =0,j=0; i< t.length; i++) {
            if(t[i] == 1) {
                result[j++] = i;
            }
        }
        return result;
    }

    private int[] indexTable(int n) {
        int[] t = new int[n];
        for(int i = 0; i< n; i++) {
            t[i] = 1;
        }
        return t;
    }

    private int getValidCount(int[] t) {
        int validCount = 0;
        for (int j : t) {
            validCount += j;
        }
        return validCount;
    }

    private void doPrintGrid(double[][] tab) {
        for (double[] ints : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.printf("%5.2f ", ints[j]);
            }
            System.out.println();
        }
    }

    private static void swapRows(double[][] tab, int m, int n) {
        for(int i = 0; i < tab[0].length; i++) {
            double temp = tab[m][i];
            tab[m][i] = tab[n][i];
            tab[n][i] = temp;
        }
    }

    private int findRowWithOne(double[][] tab, int rowIndex, int columnIndex) {
        for(int i = rowIndex; i < tab.length; i++) {
            if(tab[i][columnIndex] == 1) {
                return i;
            }
        }
        return -1;
    }

    private int findFirstRowWithNonZero(double[][] tab, int rowIndex, int columnIndex) {
        for(int i = rowIndex; i < tab.length; i++) {
            if(tab[i][columnIndex] != 0) {
                return i;
            }
        }
        return -1;
    }

    private boolean isSquare(double[][] t) {
        if(t == null || t.length == 0) {
           return false;
        }
        for (double[] ints : t) {
            if (ints.length != t.length) {
                return false;
            }
        }
        return true;
    }

    private double[][] getMinorTable(double[][] t, int i , int j) {
        int rows = t.length - 1;
        int columns = t[0].length - 1;

        double[][] result = new double[rows][columns];

        for(int rowIdx = 0, m = 0; rowIdx < rows; rowIdx++, m++) {
            if(m == i) {
               m = m + 1;
            }
            for(int colIdx = 0, n = 0; colIdx < columns; colIdx++, n++) {
                if(n == j) {
                    n = n + 1;
                }
                result[rowIdx][colIdx] = t[m][n];
            }
        }
        return result;
    }

    private void printTable(int[] t) {
        for (int j : t) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    private List<Integer> produceRange(int length) {
       List<Integer> result = new ArrayList<>(length);
       for(int i = 0; i < length; i++) {
           result.add(i, i);
       }
       return result;
    }

    private static class MatrixPermutationListener implements PermutationListener {

        private final double[][] table;
        private double determinant = 0;

        public MatrixPermutationListener(double[][] table) {
            this.table = table;
        }

        @Override
        public void permutation(int[] permutation) {
            double factor = 1;
            int inverseCount = inverseCount(permutation);
            double sign = Math.pow(-1, inverseCount);
            for(int i = 0; i < permutation.length; i++) {
                if(table[i][permutation[i]] == 0) {
                   return;
                }
                factor = factor * table[i][permutation[i]];
            }
            determinant = determinant +  sign * factor;
        }

        private int inverseCount(int[] permutation) {
            int result = 0;
            for(int i = 0; i < permutation.length; i++) {
                for(int j = i+1; j< permutation.length; j++) {
                    if(permutation[i] < permutation[j]) {
                        result++;
                    }
                }
            }
            return result;
        }

        public double getDeterminant() {
           return new BigDecimal(determinant).round(MathContext.DECIMAL32).doubleValue();
        }
    }


}
