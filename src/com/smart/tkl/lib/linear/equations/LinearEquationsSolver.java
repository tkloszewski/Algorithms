package com.smart.tkl.lib.linear.equations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearEquationsSolver {

    private static final double EPS = Math.pow(10, -14);

    private final double[][] equations;
    private final Size size;

    public LinearEquationsSolver(double[][] originalEquations) {
        this.equations = copy(originalEquations);
        this.size = getSize(originalEquations);
        if(size.m + 1 != size.n) {
            throw new IllegalArgumentException("Number of rows must be 1 less than number of columns");
        }
    }

    public static void main(String[] args) {
        double[][] equations1 = new double[][] {
                {1, 1, 1, 1},
                {-0.8, 0.3, 0.5, 0},
                {0.6, -1, 0, 0}
        };

        LinearEquationsSolver solver = new LinearEquationsSolver(equations1);
        EquationsSolution solution = solver.solve();
        System.out.println("Solution 1: " + solution);

        double[][] equations2 = new double[][] {
                {1, 1, 1, 1},
                {0.2, 0.7, -0.5, 0},
                {0.6, -1, 0, 0}
        };

        solver = new LinearEquationsSolver(equations2);
        solution = solver.solve();
        System.out.println("Solution 2: " + solution);

        for(double val : solution.getSolutions()) {
            System.out.println(val);
        }

        double[][] equations3 = new double[][] {
                {1, 1, 1, 1},
                {8, 4, 2, 5},
                {27, 9 , 3, 14}
        };
        solver = new LinearEquationsSolver(equations3);
        solution = solver.solve();

        System.out.println("Solutions for sum: " + solution);

        double[][] equations4 = new double[][] {
                {1, 1, 1, 1, 1, 1},
                {32, 16, 8, 4, 2, 17},
                {243, 81, 27, 9, 3, 98},
                {1024, 256, 64, 16, 4, 354},
                {3125, 625, 125, 25, 5, 979},
        };
        solver = new LinearEquationsSolver(equations4);
        solution = solver.solve();

        System.out.println("Solutions for sum of 4-th powers: " + solution);
    }

    public EquationsSolution solve() {
        List<Double> solutions = new ArrayList<>(this.size.m);
        for(int i = 0; i < this.size.m; i++) {
            int selected = i;
            double maxAbs = Math.abs(this.equations[i][i]);
            for(int k = i + 1; k < this.size.m; k++) {
               if(Math.abs(this.equations[k][i]) > maxAbs) {
                  selected = k;
                  maxAbs = Math.abs(this.equations[k][i]);
               }
            }
            if(selected != i) {
               swapRows(i, selected);
            }
            double selectedCoefficient = equations[i][i];
            if(isZero(selectedCoefficient)) {
                return EquationsSolution.infinitelyMany();
            }
            if(!isZero(selectedCoefficient - 1)) {
                multiplyRowBy(equations[i], 1 / selectedCoefficient);
            }
            for(int j = 0; j < this.size.m; j++) {
                double coefficient = this.equations[j][i];
                if(j != i && !isZero(coefficient)) {
                   double multiplyBy = -coefficient;
                   multiplyAndAddToRow(equations[i], equations[j], multiplyBy);
                   checkRow(equations[j]);
                }
            }
        }
        for(int i = 0; i< this.size.m; i++) {
            solutions.add(equations[i][this.size.n - 1]);
        }
        return new EquationsSolution(solutions);
    }

    private Size getSize(double[][] equations) {
        if(equations == null || equations.length == 0) {
            throw new IllegalArgumentException("Null or empty equations matrix");
        }
        int m = equations.length;
        int n = equations[0].length;
        for(int j = 1; j < equations.length; j++) {
            if(equations[j].length != n) {
                throw new IllegalArgumentException("Row " + j + " has different number of columns");
            }
        }
        return new Size(m, n);
    }

    private double[] multiplyAndAddToRow(double[] row1, double[] row2, double value) {
        for(int i = 0; i < row1.length; i++) {
            row2[i] += row1[i] * value;
        }
        return row2;
    }

    private double[] multiplyRowBy(double[] row, double value) {
        for(int i = 0; i < row.length; i++) {
            row[i] *= value;
        }
        return row;
    }

    private void swapRows(int row1, int row2) {
        double[] temp = this.equations[row1];
        this.equations[row1] = this.equations[row2];
        this.equations[row2] = temp;
    }

    private Optional<EquationsSolution> checkRow(double[] row) {
        for(int i = 0; i < row.length - 1; i++) {
            if(!isZero(row[i])) {
                return Optional.empty();
            }
        }
        return isZero(row[row.length - 1]) ?
                Optional.of(EquationsSolution.infinitelyMany()) : Optional.of(EquationsSolution.inconsistent());
    }

    private boolean isZero(double value) {
        return Math.abs(value) < EPS;
    }

    private double[][] copy(double[][] original) {
        double[][] result = new double[original.length][original[0].length];
        for(int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, result[i], 0, original[0].length);
        }
        return result;
    }

    private static class Size {
        final int m,n;

        public Size(int m, int n) {
            this.m = m;
            this.n = n;
        }
    }

}
