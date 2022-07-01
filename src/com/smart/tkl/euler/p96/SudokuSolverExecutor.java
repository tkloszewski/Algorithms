package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuSquare;

public class SudokuSolverExecutor {

    private static final int[][] empty = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] sudoku1 = {
            {0, 0, 3, 0, 2, 0, 6, 0, 0},
            {9, 0, 0, 3, 0, 5, 0, 0, 1},
            {0, 0, 1, 8, 0, 6, 4, 0, 0},
            {0, 0, 8, 1, 0, 2, 9, 0, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 8},
            {0, 0, 6, 7, 0, 8, 2, 0, 0},
            {0, 0, 2, 6, 0, 9, 5, 0, 0},
            {8, 0, 0, 2, 0, 3, 0, 0, 9},
            {0, 0, 5, 0, 1, 0, 3, 0, 0},
    };

    private static final int[][] easy1 = {
            {0, 0, 0, 9, 0, 0, 0, 0, 3},
            {3, 4, 0, 0, 1, 0, 6, 0, 0},
            {0, 5, 6, 4, 0, 0, 0, 0, 8},
            {1, 3, 2, 6, 5, 8, 0, 0, 0},
            {0, 9, 0, 7, 4, 3, 0, 6, 0},
            {0, 6, 4, 2, 9, 1, 8, 0, 0},
            {0, 2, 0, 0, 8, 0, 3, 1, 9},
            {0, 0, 0, 0, 2, 0, 0, 8, 0},
            {0, 8, 0, 0, 0, 9, 4, 5, 0},
    };

    private static final int[][] medium1 = {
            {0, 0, 0, 7, 6, 3, 4, 1, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 6},
            {3, 0, 6, 0, 0, 0, 0, 0, 7},
            {0, 2, 0, 0, 0, 0, 7, 5, 0},
            {0, 5, 4, 2, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 5, 8, 2, 0, 1},
            {0, 6, 0, 0, 8, 0, 0, 0, 0},
            {0, 3, 0, 9, 0, 6, 0, 2, 5},
            {0, 0, 7, 0, 2, 4, 6, 0, 0},
    };

    private static final int[][] hard1 = {
            {6, 0, 7, 0, 3, 1, 0, 0, 4},
            {0, 0, 3, 0, 4, 0, 2, 0, 0},
            {0, 9, 0, 8, 0, 0, 0, 0, 0},
            {4, 0, 0, 9, 0, 0, 0, 7, 8},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 0, 4, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 4, 0},
            {0, 4, 8, 7, 0, 0, 1, 0, 6},
    };

    private static final int[][] expert1 = {
            {0, 0, 0, 0, 0, 3, 0, 0, 0},
            {2, 3, 0, 6, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 2, 0, 1, 0},
            {0, 0, 5, 7, 9, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 7, 0},
            {0, 0, 1, 0, 0, 0, 8, 0, 0},
            {0, 0, 0, 0, 6, 5, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 3, 8, 2},
            {0, 0, 0, 0, 4, 0, 0, 0, 6},
    };

    public static void main(String[] args) {
        SudokuSquare sudokuSquare = new SudokuSquare(expert1);
        System.out.println("Built square: \n" + sudokuSquare);
        SudokuSolver sudokuSolver = new SudokuSolver(sudokuSquare);
        SudokuSolverResult solverResult = sudokuSolver.solve();
        System.out.println("Solved sudoku: \n" + solverResult.getSquare());
    }
}
