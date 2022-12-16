package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuSquare;

public class ExampleSudokuSolver {

    private static final int[][] exampleSudoku = {
            {0, 0, 7, 0, 2, 0, 0, 0, 6},
            {5, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 9, 5, 0, 6, 1, 0, 0},
            {0, 0, 0, 0, 5, 0, 0, 0, 0},
            {9, 0, 0, 4, 0, 7, 0, 0, 3},
            {0, 0, 1, 0, 0, 0, 0, 8, 0},
            {4, 0, 0, 6, 0, 9, 0, 0, 7},
            {0, 0, 0, 0, 0, 2, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 3, 0, 0},
    };

    public static void main(String[] args) {
        SudokuSquare sudokuSquare = new SudokuSquare(exampleSudoku);
        DelegateSudokuSolver sudokuSolver = new DelegateSudokuSolver();
        SudokuSolverResult result = sudokuSolver.solve(sudokuSquare);
        System.out.println("Solved sudoku: \n" + result.getSquare().toString());
    }
}
